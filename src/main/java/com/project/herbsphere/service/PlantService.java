package com.project.herbsphere.service;

import com.opencsv.CSVReader;
import com.project.herbsphere.model.Plant;
import com.project.herbsphere.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public void savePlantsFromCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] data;
            List<Plant> plants = new ArrayList<>();

            while ((data = csvReader.readNext()) != null) {
                if (data.length >= 5) {
                    Plant plant = new Plant();
                    plant.setPlantName(data[0].trim());
                    plant.setPartUsed(data[1].trim());
                    plant.setRegion(data[2].trim());       // Region is at index 2
                    plant.setMedicinalBenefits(data[3].trim());  // MedicinalBenefits is at index 3
                    plant.setImageUrl(data[4].trim());     // ImageUrl is at index 4
                    plants.add(plant);
                }
            }

            plantRepository.saveAll(plants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plant> getRandomPlants(int count) {
        List<Plant> allPlants = plantRepository.findAll();
        Collections.shuffle(allPlants); // randomize the list
        return allPlants.stream().limit(count).toList();
    }

    public List<String> getAllPlantNames() {
        return plantRepository.findAll().stream()
                .map(Plant::getPlantName)
                .toList();
    }

    public Plant getPlantByName(String plantName) {
        return plantRepository.findByPlantName(plantName);
    }
}
