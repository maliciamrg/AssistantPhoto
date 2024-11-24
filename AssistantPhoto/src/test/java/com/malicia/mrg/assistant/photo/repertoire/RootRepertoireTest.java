package com.malicia.mrg.assistant.photo.repertoire;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RootRepertoireTest {

    @Autowired
    private MyConfig mockConfig; // Mocking the MyConfig dependency

    private RootRepertoire rootRepertoire; // Injecting the mock into the RootRepertoire constructor

    @BeforeEach
    void setUp() {
        // Any setup before each test (if necessary)
    }

    // recuperer uniquement les Repertoires AllIn (photo non rafiné a tirer /grouper)
    @Test
    void getAllSeanceRepertoireAllin() {
        //given
        RootRepertoire rootRep = new RootRepertoire(mockConfig);

        //when
        List<SeanceRepertoire> allSeanceRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.ALL_IN);

        //then
        System.out.println(allSeanceRepertoire);
        for (SeanceRepertoire seanceRepertoire : allSeanceRepertoire) {
            System.out.println(seanceRepertoire);
        }
        assertEquals(2, allSeanceRepertoire.size());
    }

    // recuperer uniquement le Repertoires de travail de assitant
    @Test
    void getAssistantWorkRepertoire() {
        //given
        RootRepertoire rootRep = new RootRepertoire(mockConfig);

        //when
        List<SeanceRepertoire> allSeanceRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.ASSISTANT_WORK);

        //then
        System.out.println(allSeanceRepertoire);
        for (SeanceRepertoire seanceRepertoire : allSeanceRepertoire) {
            System.out.println(seanceRepertoire);
        }
        assertEquals(1, allSeanceRepertoire.size());
        assertEquals("00-CheckIn", allSeanceRepertoire.get(0).getPath());
    }

    // recupere un list de photo depuis un repertoire
    @Test
    void getAllPhotoFromAllIn() {
        //given
        Path rootTest = Paths.get("src", "test", "resources");
        mockConfig.setRootPath("./" + rootTest.toString() + "/");
        RootRepertoire rootRep = new RootRepertoire(mockConfig);

        //when
        List<SeanceRepertoire> assistantRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.ALL_IN);
        List<Photo> allPhotoFromSeanceRepertoire = rootRep.getAllPhotoFromSeanceRepertoire(assistantRepertoire);

        //then
        System.out.println(allPhotoFromSeanceRepertoire);
        for (Photo photo : allPhotoFromSeanceRepertoire) {
            System.out.println(photo);
        }
        assertEquals(10, allPhotoFromSeanceRepertoire.size());
    }

    // recupere un list de photo depuis et le sauvegarder dans un json
    @Test
    void getAllPhotoFromAllInToJson() {
        //given
        Path rootTest = Paths.get("src", "test", "resources");
        mockConfig.setRootPath("./" + rootTest.toString() + "/");
        RootRepertoire rootRep = new RootRepertoire(mockConfig);
        String jsonDest = mockConfig.getRootPath() + "/getAllPhotoFromAllInToJsonTEST.json";

        //when
        List<SeanceRepertoire> assistantRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.ALL_IN);
        List<Photo> allPhotoFromSeanceRepertoire = rootRep.getAllPhotoFromSeanceRepertoireToJson(assistantRepertoire, jsonDest);

        //then
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonDest);
        List<Photo> allPhotoFromSeanceRepertoireFromFile = new ArrayList<>();
        try {
            allPhotoFromSeanceRepertoireFromFile = objectMapper.readValue(file, new TypeReference<List<Photo>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" --> " + allPhotoFromSeanceRepertoireFromFile.size() + " == " + allPhotoFromSeanceRepertoire.size() + " <-- ");
        assertEquals(allPhotoFromSeanceRepertoireFromFile.size(), allPhotoFromSeanceRepertoire.size());
    }

    // recuperate un list de photo depuis un fichier json
    @Test
    void getAllPhotoFromAllInFromJson() {
        //given
        Path rootTest = Paths.get("src", "test", "resources");
        mockConfig.setRootPath("./" + rootTest.toString() + "/");
        RootRepertoire rootRep = new RootRepertoire(mockConfig);
        String jsonDest = mockConfig.getRootPath() + "/getAllPhotoFromAllInToJsonTEST.json";

        //when
        List<Photo> allPhotoFromJson = rootRep.getAllPhotoFromJson(jsonDest);

        //then
        System.out.println(allPhotoFromJson);
        for (Photo photo : allPhotoFromJson) {
            System.out.println(photo);
        }
        assertEquals(10, allPhotoFromJson.size());
    }

//    // recupere un list de photo depuis le repertoire reel
//    @Test
//    void getAllPhotoFromAllInRealToJson() {
//        //given
//        mockConfig.setRootPath("\\\\192.212.5.111\\80-Photo\\");
//        RootRepertoire rootRep = new RootRepertoire(mockConfig);
//        String jsonDest = mockConfig.getRootPath() + "/getAllPhotoFromAllInRealToJsonTEST.json";
//
//        //when
//        List<SeanceRepertoire> assistantRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.ALL_IN);
//        List<Photo> allPhotoFromSeanceRepertoire = rootRep.getAllPhotoFromSeanceRepertoireToJson(assistantRepertoire, jsonDest);
//
//        //then
//        ObjectMapper objectMapper = new ObjectMapper();
//        File file = new File(jsonDest);
//        List<Photo> allPhotoFromSeanceRepertoireFromFile = new ArrayList<>();
//        try {
//            allPhotoFromSeanceRepertoireFromFile = objectMapper.readValue(file, new TypeReference<List<Photo>>() {});
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(" --> " + allPhotoFromSeanceRepertoireFromFile.size() + " == " + allPhotoFromSeanceRepertoire.size() + " <-- ");
//        assertEquals(allPhotoFromSeanceRepertoireFromFile.size(), allPhotoFromSeanceRepertoire.size());
//    }

    // recupere un list de photo depuis un repertoire
    @Test
    void getAllPhotoFromSeance() {
        //given
        Path rootTest = Paths.get("src", "test", "resources");
        mockConfig.setRootPath("./" + rootTest.toString() + "/");
        RootRepertoire rootRep = new RootRepertoire(mockConfig);

        //when
        SeanceRepertoire assistantRepertoire = rootRep.getAllSeanceRepertoire(SeanceTypeEnum.EVENTS).get(0);
        List<Photo> allPhotoFromSeanceRepertoire = rootRep.getAllPhotoFromSeanceRepertoire(assistantRepertoire);

        //then
        System.out.println(allPhotoFromSeanceRepertoire);
        for (Photo photo : allPhotoFromSeanceRepertoire) {
            System.out.println(photo);
        }
        assertEquals(1, allPhotoFromSeanceRepertoire.size());
    }

    // Group photo
    @Test
    void getGroupOfPhotoFromJson() {
        //given
        Path rootTest = Paths.get("src", "test", "resources");
        mockConfig.setRootPath("./" + rootTest.toString() + "/");
        RootRepertoire rootRep = new RootRepertoire(mockConfig);
        String jsonSrc = mockConfig.getRootPath() + "/getAllPhotoFromAllInRealToJsonTEST.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonSrc);
        List<Photo> allPhotoFromSeanceRepertoireFromFile = new ArrayList<>();
        try {
            allPhotoFromSeanceRepertoireFromFile = objectMapper.readValue(file, new TypeReference<List<Photo>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        //todo
        List<Photo> repGroupOfPhotoFrom = rootRep.getGroupOfPhotoFrom(allPhotoFromSeanceRepertoireFromFile);

        //then
        System.out.println(" --> " + allPhotoFromSeanceRepertoireFromFile.size() + " == " + repGroupOfPhotoFrom.size() + " <-- ");
        assertEquals(allPhotoFromSeanceRepertoireFromFile.size(), repGroupOfPhotoFrom.size());
    }
}
