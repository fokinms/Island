package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.IslandController;
import com.javarush.fokin.island.islandfx.constants.AnimalTypes;
import com.javarush.fokin.island.islandfx.constants.PlantTypes;
import com.javarush.fokin.island.islandfx.entity.Animal;
import com.javarush.fokin.island.islandfx.entity.Cell;
import com.javarush.fokin.island.islandfx.entity.Plant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics implements Runnable {
    Cell[][] islandCells;
    IslandController islandController;

    public Statistics(Cell[][] islandCells, IslandController islandController) {
        this.islandCells = islandCells;
        this.islandController = islandController;
    }

    @Override
    public void run() {
        setIslandTic();
        setCellStatistics();
        setNumberOfAnimalsAndPlantsOnIsland();
        setIslandStatistics();
    }

    private void setCellStatistics() {
        HashMap<AnimalTypes, HashSet<Animal>> animals = islandController.getCurrentCell().getCellLocation().getAnimals();
        HashMap<PlantTypes, HashSet<Plant>> plants = islandController.getCurrentCell().getCellLocation().getPlants();
        for (Map.Entry<AnimalTypes, HashSet<Animal>> animalEntry : animals.entrySet()) {
            switch (AnimalTypes.valueOf(animalEntry.getKey().name())) {
                case BEAR -> islandController.cellBearQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case BOA -> islandController.cellBoaQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case BOAR -> islandController.cellBoarQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case BUFFALO ->
                        islandController.cellBuffaloQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case CATERPILLAR ->
                        islandController.cellCaterpillarQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case DEER -> islandController.cellDeerQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case DUCK -> islandController.cellDuckQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case EAGLE ->
                        islandController.cellEagleQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case FOX -> islandController.cellFoxQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case GOAT -> islandController.cellGoatQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case HORSE ->
                        islandController.cellHorseQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case MOUSE ->
                        islandController.cellMouseQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case RABBIT ->
                        islandController.cellRabbitQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case SHEEP ->
                        islandController.cellSheepQuantity.setText(Integer.toString(animalEntry.getValue().size()));
                case WOLF -> islandController.cellWolfQuantity.setText(Integer.toString(animalEntry.getValue().size()));
            }
        }
        for (Map.Entry<PlantTypes, HashSet<Plant>> plantEntry : plants.entrySet()) {
            switch (PlantTypes.valueOf(plantEntry.getKey().name())) {
                case GRASS ->
                        islandController.cellGrassQuantity.setText(Integer.toString(plantEntry.getValue().size()));
            }
        }
    }

    private void setIslandTic() {
        islandController.dayOfEmulationLabel.setText(Integer.toString(islandController.daysOfEmulation.incrementAndGet()));
    }

    private void setNumberOfAnimalsAndPlantsOnIsland() {
        AtomicInteger animalCounter = new AtomicInteger();
        AtomicInteger plantCounter = new AtomicInteger();
        for (Cell[] cells : islandCells) {
            for (Cell cell : cells) {
                cell.getCellLocation().getAnimals().forEach((key, animal) -> {
                    animalCounter.addAndGet(animal.size());
                });
                cell.getCellLocation().getPlants().forEach((key, plant) -> {
                    plantCounter.addAndGet(plant.size());
                });
            }
        }
        islandController.numberOfAnimalsLabel.setText(Integer.toString(animalCounter.get()));
        islandController.numberOfPlantsLabel.setText(Integer.toString(plantCounter.get()));
    }

    private void setIslandStatistics() {
        HashMap<AnimalTypes, Integer> animalsCountMap = new HashMap<>();
        HashMap<PlantTypes, Integer> plantsCountMap = new HashMap<>();
        for (Cell[] cells : islandCells) {
            for (Cell cell : cells) {
                cell.getCellLocation().getAnimals().forEach((x, y) -> {
                    if (animalsCountMap.containsKey(x)) {
                        animalsCountMap.put(x, (animalsCountMap.get(x) + y.size()));
                    } else {
                        animalsCountMap.put(x, y.size());
                    }
                });
                cell.getCellLocation().getPlants().forEach((x, y) -> {
                    if (plantsCountMap.containsKey(x)) {
                        plantsCountMap.put(x, (plantsCountMap.get(x) + y.size()));
                    } else {
                        plantsCountMap.put(x, y.size());
                    }
                });
            }
        }
        islandController.islandWolfQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.WOLF)));
        islandController.islandBoaQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.BOA)));
        islandController.islandFoxQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.FOX)));
        islandController.islandBearQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.BEAR)));
        islandController.islandEagleQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.EAGLE)));
        islandController.islandHorseQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.HORSE)));
        islandController.islandDeerQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.DEER)));
        islandController.islandRabbitQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.RABBIT)));
        islandController.islandMouseQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.MOUSE)));
        islandController.islandGoatQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.GOAT)));
        islandController.islandSheepQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.SHEEP)));
        islandController.islandBoarQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.BOAR)));
        islandController.islandBuffaloQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.BUFFALO)));
        islandController.islandDuckQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.DUCK)));
        islandController.islandCaterpillarQuantity.setText(Integer.toString(animalsCountMap.get(AnimalTypes.CATERPILLAR)));
        islandController.islandGrassQuantity.setText(Integer.toString(plantsCountMap.get(PlantTypes.GRASS)));
    }
}
