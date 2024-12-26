package com.malicia.mrg.assistant.photo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malicia.mrg.assistant.photo.DTO.SeanceTypeDto;
import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import com.malicia.mrg.assistant.photo.repertoire.file.WorkWithFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SeanceTypeControllerTest {


    private MockMvc mockMvc;

    @Test
    void getSeanceTypes_ShouldReturnSeanceTypes() throws Exception {
        // Initialize controller with the real RootRepertoire bean
        SeanceTypeController controller = new SeanceTypeController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //given
        Path rootTest = Paths.get("src", "test", "resources");
        String jsonDest = "./" + rootTest + "/" + "/getSeanceTypes_ShouldReturnSeanceTypesTEST.json";

        // Perform the request and capture the result
        MvcResult result = mockMvc.perform(get("/api/seance-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(SeanceTypeEnum.values().length)))
                .andExpect(jsonPath("$[0].id", is(SeanceTypeEnum.values()[0].name())))
                .andExpect(jsonPath("$[0].name", is(SeanceTypeEnum.values()[0].toString())))
                .andReturn();


        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonResponse = result.getResponse().getContentAsString();
            // Map JSON string to List<SeanceTypeDto>
            List<SeanceTypeDto> seanceTypeList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
            WorkWithFile.putIntoJsonFile(seanceTypeList, jsonDest);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
