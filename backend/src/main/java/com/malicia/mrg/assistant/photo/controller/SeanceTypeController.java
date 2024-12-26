package com.malicia.mrg.assistant.photo.controller;

import com.malicia.mrg.assistant.photo.DTO.SeanceTypeDto;
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

}
