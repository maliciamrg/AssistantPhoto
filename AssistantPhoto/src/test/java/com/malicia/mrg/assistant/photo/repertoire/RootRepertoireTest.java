package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
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

    @Test
    void getAllSeanceRepertoireAllin() {
        //given
        RootRepertoire rootRep = new RootRepertoire(mockConfig);

        //when
        List<SeanceRepertoire> seanceRepertoires = rootRep.getAllSeanceRepertoire(0);

        //then
        System.out.println(seanceRepertoires);
        for (SeanceRepertoire seanceRepertoire : seanceRepertoires) {
            System.out.println(seanceRepertoire.getNomunique());
        }
        assertEquals(2, seanceRepertoires.size());
    }
}