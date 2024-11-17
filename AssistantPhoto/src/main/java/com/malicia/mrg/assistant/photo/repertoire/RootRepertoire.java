package com.malicia.mrg.assistant.photo.repertoire;

import com.malicia.mrg.assistant.photo.MyConfig;
import com.malicia.mrg.assistant.photo.parameter.RepertoireOfType;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import org.springframework.stereotype.Component;

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
}
