package com.malicia.mrg.assistant.photo;

import com.malicia.mrg.assistant.photo.parameter.RepertoireOfType;
import com.malicia.mrg.assistant.photo.parameter.SeanceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "assistant")
public class MyConfig {

    private String name;
    private List<SeanceType> seanceType;
    private List<RepertoireOfType> repertoireOfType;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeanceType> getSeanceType() {
        return seanceType;
    }

    public void setSeanceType(List<SeanceType> seanceType) {
        this.seanceType = seanceType;
    }

    public List<RepertoireOfType> getRepertoireOfType() {
        return repertoireOfType;
    }

    public void setRepertoireOfType(List<RepertoireOfType> repertoireOfType) {
        this.repertoireOfType = repertoireOfType;
    }
}
