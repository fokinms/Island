package com.javarush.fokin.island.islandfx.entity;

import com.javarush.fokin.island.islandfx.constants.AnimalTypes;
import com.javarush.fokin.island.islandfx.constants.PlantTypes;
import com.javarush.fokin.island.islandfx.constants.CreatureProperties;
import com.javarush.fokin.island.islandfx.services.GetDataFromXML;

import java.util.HashMap;
import java.util.Map;

public class Island{

    private final Cell[][] islandCells;
    private final long timeOfCreation;

    public Island(int width, int height) {
        long timeOfBeginning = System.currentTimeMillis();
        islandCells = new Cell[width][height];
        initIslandCells(islandCells);
        setNeighbours(islandCells);
        setMaxAnimalCapacity(islandCells);
        setMaxPlantCapacity(islandCells);
        setAnimals(islandCells);
        setPlants(islandCells);
        timeOfCreation = System.currentTimeMillis() - timeOfBeginning;
    }

    public void setPlants(Cell[][] islandCells) {
        for (Cell[] islandCell : islandCells) {
            for (Cell cell : islandCell) {
                cell.getCellLocation().initPlants();
            }
        }
    }

    public void setAnimals(Cell[][] islandCells) {
        for (Cell[] islandCell : islandCells) {
            for (Cell cell : islandCell) {
                cell.getCellLocation().initAnimals();
            }
        }
    }

    public void setMaxAnimalCapacity(Cell[][] islandCells) {
        for (Cell[] islandCell : islandCells) {
            for (Cell cell : islandCell) {
                cell.getCellLocation().setMaxAnimalCapacity(initMaxAnimalCapacityFromMap());
            }
        }
    }

    public void setMaxPlantCapacity(Cell[][] islandCells) {
        for (Cell[] islandCell : islandCells) {
            for (Cell cell : islandCell) {
                cell.getCellLocation().setMaxPlantCapacity(initMaxPlantCapacityFromMap());
            }
        }
    }

    private HashMap<AnimalTypes, Integer> initMaxAnimalCapacityFromMap() {
        GetDataFromXML getDataFromXML = new GetDataFromXML();
        HashMap<AnimalTypes, Integer> maxCapacity = new HashMap<>();
        for (AnimalTypes type : AnimalTypes.values()) {
            HashMap<String, String> propertiesMap = getDataFromXML.createPropertiesMap(type.name());
            for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                if (entry.getKey().equals(CreatureProperties.MAX_CAPACITY)) {
                    maxCapacity.put(type, Integer.parseInt(entry.getValue()));
                }
            }
        }
        return maxCapacity;
    }

    private HashMap<PlantTypes, Integer> initMaxPlantCapacityFromMap() {
        GetDataFromXML getDataFromXML = new GetDataFromXML();
        HashMap<PlantTypes, Integer> maxCapacity = new HashMap<>();
        for (PlantTypes type : PlantTypes.values()) {
            HashMap<String, String> propertiesMap = getDataFromXML.createPropertiesMap(type.name());
            for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                if (entry.getKey().equals(CreatureProperties.MAX_CAPACITY)) {
                    maxCapacity.put(type, Integer.parseInt(entry.getValue()));
                }
            }
        }
        return maxCapacity;
    }

    public void setNeighbours(Cell[][] islandCells) {
        for (int i = 0; i < islandCells.length; i++) {
            for (int j = 0; j < islandCells[i].length; j++) {
                for (int si = -1; si < 2; si++) {
                    for (int sj = -1; sj < 2; sj++) {
                        if ((i + si >= 0) && (i + si < islandCells.length) && (j + sj >= 0) && (j + sj < islandCells[i].length)) {
                            islandCells[i][j].getCellLocation().getNeighbours().add(islandCells[i + si][j + sj]);
                        }
                    }
                }
            }
        }
    }

    private void initIslandCells(Cell[][] islandCells) {
        for (int i = 0; i < islandCells.length; i++) {
            for (int j = 0; j < islandCells[i].length; j++) {
                islandCells[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getIslandCells() {
        return islandCells;
    }

    public long getTimeOfCreation() {
        return timeOfCreation;
    }
}
