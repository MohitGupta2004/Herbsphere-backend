package com.project.herbsphere.controller;

import com.project.herbsphere.model.Plant;
import com.project.herbsphere.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/plant")
public class PlantController {

    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("file") MultipartFile file) {
        plantService.savePlantsFromCsv(file);
        return "File uploaded and data saved successfully!";
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllPlantNames() {
        List<String> plantNames = plantService.getAllPlantNames();
        return new ResponseEntity<>(plantNames, HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<Plant> getPlantByName(@RequestParam String plantName) {
        Plant plant = plantService.getPlantByName(plantName);
        return new ResponseEntity<>(plant, HttpStatus.OK);
    }

    @GetMapping("/random")
    public List<Plant> getRandomPlants() {
        return plantService.getRandomPlants(8);
    }
}
