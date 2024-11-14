package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RootRepertoire {

    private final MyConfig config;

    public RootRepertoire(MyConfig config) {
        this.config = config;
    }

    public List<SeanceRepertoire> getAllSeanceRepertoire(int typeOfSceance) {

        List<SeanceRepertoire> expectedList = new ArrayList<>();

        // Using enhanced for-each loop
        for (SeanceRepertoire seanceRepertoire : config.getSeanceRepertoire()) {
            if (typeOfSceance == 0) {
                if (seanceRepertoire.getNomunique().contentEquals("AllIn")) {
                    expectedList.add(seanceRepertoire);
                }
            } else {
                expectedList.add(seanceRepertoire);
            }
        }
        return expectedList;
    }
}
