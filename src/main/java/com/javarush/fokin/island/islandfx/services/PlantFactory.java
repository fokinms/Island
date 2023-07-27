package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.constants.PlantStatus;
import com.javarush.fokin.island.islandfx.constants.PlantTypes;
import com.javarush.fokin.island.islandfx.constants.CreatureProperties;
import com.javarush.fokin.island.islandfx.entity.Grass;
import com.javarush.fokin.island.islandfx.entity.Plant;
import java.util.HashMap;
import java.util.Map;

public class PlantFactory {
    public Plant createPlant(PlantTypes plantType) {
        Plant plant = null;
        GetDataFromXML getDataFromXML = new GetDataFromXML();

        switch (plantType) {
            case GRASS:
                plant = new Grass();
                plant.setStatus(PlantStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(plantType.toString()), plant);
                break;
        }
        return plant;
    }

    private void initPropertiesFromMap(HashMap<String, String> propertiesMap, Plant plant) {
        for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
            if (entry.getKey().equals(CreatureProperties.WEIGHT)) {
                plant.setWeight(Double.parseDouble(entry.getValue()));
            }
            if (entry.getKey().equals(CreatureProperties.OFFSPRING)) {
                plant.setOffspring(Integer.parseInt(entry.getValue()));
            }
        }
    }
}
