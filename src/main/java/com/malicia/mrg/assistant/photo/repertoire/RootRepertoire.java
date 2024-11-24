package com.malicia.mrg.assistant.photo.repertoire;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.RepertoireOfType;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import com.malicia.mrg.assistant.photo.repertoire.file.WorkWithFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class RootRepertoire {

    private final MyConfig config;

    public RootRepertoire(MyConfig config) {
        this.config = config;
        controlConfig(config);
    }

    private void controlConfig(MyConfig config) {
        int size = getAllSeanceRepertoire(SeanceTypeEnum.ASSISTANT_WORK).size();
        try {

            if (size > 1) {
                throw new CustomException("ASSISTANT_WORK has more than one element.");
            }
            if (size == 0) {
                throw new CustomException("ASSISTANT_WORK has no element.");
            }

        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SeanceRepertoire> getAllSeanceRepertoire(SeanceTypeEnum typeOfSceance) {

        List<SeanceRepertoire> expectedList = new ArrayList<>();

        // Using enhanced for-each loop
        for (RepertoireOfType repertoireOfType : config.getRepertoireOfType()) {
            if (repertoireOfType.getSeanceType() == typeOfSceance) {
                expectedList.addAll(repertoireOfType.getRepertoire());
            }
        }
        return expectedList;
    }

    public List<Photo> getAllPhotoFromSeanceRepertoire(SeanceRepertoire repertoire) {
        List<Photo> expectedList = new ArrayList<>();

        String pathToScan = config.getRootPath() + repertoire.getPath();
        List<String> includeTypeFile = config.getFileExtensionsToWorkWith();

        try {
            List<Path> listPath = WorkWithFile.getAllFilesFromFolderAndSubFolderWithType(pathToScan, includeTypeFile);
            expectedList = WorkWithFile.convertPathsToPhotos(listPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return expectedList;
    }

    public List<Photo> getAllPhotoFromSeanceRepertoire(List<SeanceRepertoire> repertoires) {
        List<Photo> expectedList = new ArrayList<>();
        for (SeanceRepertoire repertoire : repertoires) {
            expectedList.addAll(getAllPhotoFromSeanceRepertoire(repertoire));
        }
        return expectedList;
    }

    public List<Photo> getAllPhotoFromSeanceRepertoireToJson(List<SeanceRepertoire> repertoires, String jsonDest) {
        List<Photo> expectedList = getAllPhotoFromSeanceRepertoire(repertoires);

        WorkWithFile.putIntoJsonFile(expectedList, jsonDest);

        return expectedList;
    }

    public List<Photo> getAllPhotoFromJson(String jsonDest) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonDest);
        List<Photo> allPhotoFromSeanceRepertoireFromFile = new ArrayList<>();
        try {
            allPhotoFromSeanceRepertoireFromFile = objectMapper.readValue(file, new TypeReference<List<Photo>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allPhotoFromSeanceRepertoireFromFile;
    }

    public List<Photo> getGroupOfPhotoFrom(List<Photo> allPhotoFromSeanceRepertoireFromFile) {
        //todo
        return allPhotoFromSeanceRepertoireFromFile;
    }
}

class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
