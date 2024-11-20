package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        assertEquals(6, allPhotoFromSeanceRepertoire.size());
    }

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
}
