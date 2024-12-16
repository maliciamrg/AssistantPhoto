package com.malicia.mrg.assistant.photo.controller;

import com.malicia.mrg.assistant.photo.parameter.SeanceTypeEnum;
import com.malicia.mrg.assistant.photo.repertoire.Photo;
import com.malicia.mrg.assistant.photo.repertoire.RootRepertoire;
import com.malicia.mrg.assistant.photo.repertoire.SeanceRepertoire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoController {

    @Autowired
    RootRepertoire rootRep;

    @GetMapping("/photos")
    public ResponseEntity<List<Photo>> index(@RequestParam("seanceType") SeanceTypeEnum seanceType) {
        try {
            List<SeanceRepertoire> assistantRepertoire = rootRep.getAllSeanceRepertoire(seanceType);
            List<Photo> allPhotoFromSeanceRepertoire = rootRep.getAllPhotoFromSeanceRepertoire(assistantRepertoire);
            return ResponseEntity.ok(allPhotoFromSeanceRepertoire);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle invalid SeanceTypeEnum
        }
    }
}