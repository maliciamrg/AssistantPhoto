package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    // recuperer uniquement les Repertoires AllIn (photo non rafiné a tirer /grouper)
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
        assertEquals("10-Assistant_work", allSeanceRepertoire.get(0).getPath());
    }
}