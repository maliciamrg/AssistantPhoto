package com.malicia.mrg.assistant.photo.controller;

import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seance-types")
public class SeanceTypeController {

    @GetMapping
    public List<SeanceTypeDto> getSeanceTypes() {
        return Arrays.stream(SeanceTypeEnum.values())
                .map(seanceType -> new SeanceTypeDto(seanceType.name(), seanceType.toString()))
                .collect(Collectors.toList());
    }

    // DTO for the response
    public static class SeanceTypeDto {
        private String id;
        private String name;

        public SeanceTypeDto(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
