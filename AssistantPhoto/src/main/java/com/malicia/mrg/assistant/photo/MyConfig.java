package com.malicia.mrg.assistant.photo;

import com.malicia.mrg.assistant.photo.repertoire.SeanceRepertoire;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "assistant")
public class MyConfig {

    private String name;
    private List<SeanceRepertoire> seanceRepertoire;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeanceRepertoire> getSeanceRepertoire() {
        return seanceRepertoire;
    }

    public void setSeanceRepertoire(List<SeanceRepertoire> seanceRepertoire) {
        this.seanceRepertoire = seanceRepertoire;
    }
}
