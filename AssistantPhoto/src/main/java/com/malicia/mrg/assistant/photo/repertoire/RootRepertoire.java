package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.RepertoireOfType;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import com.malicia.mrg.assistant.photo.repertoire.file.WorkWithFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class RootRepertoire {

    private final MyConfig config;

    public RootRepertoire(MyConfig config) {
        this.config = config;
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
}
