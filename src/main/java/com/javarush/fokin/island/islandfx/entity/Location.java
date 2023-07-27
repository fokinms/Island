package com.javarush.fokin.island.islandfx.entity;

import com.javarush.fokin.island.islandfx.constants.AnimalTypes;
import com.javarush.fokin.island.islandfx.constants.PlantTypes;
import com.javarush.fokin.island.islandfx.services.AnimalFactory;
import com.javarush.fokin.island.islandfx.services.PlantFactory;
import com.javarush.fokin.island.islandfx.services.ThreadRandomData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Location {
    private final Set<Cell> neighbours;
    private HashMap<AnimalTypes, HashSet<Animal>> animals;
    private HashMap<PlantTypes, HashSet<Plant>> plants;
    private HashMap<AnimalTypes, Integer> maxAnimalCapacity;
    private HashMap<PlantTypes, Integer> maxPlantCapacity;


    public Location() {
        neighbours = new HashSet<>();
        animals = new HashMap<>();
        plants = new HashMap<>();
        maxAnimalCapacity = null;
        maxPlantCapacity = null;
    }

    public Set<Cell> getNeighbours() {
        return neighbours;
    }

    public void initPlants() {
        ThreadRandomData randomizer = new ThreadRandomData();
        PlantFactory plantFactory = new PlantFactory();
        for (Map.Entry<PlantTypes, Integer> entry : maxPlantCapacity.entrySet()) {
            HashSet<Plant> hashSetPlants = new HashSet<>();
            for (int i = 0; i < randomizer.getThreadRandomData().nextInt(100, entry.getValue()); i++) {
                hashSetPlants.add(plantFactory.createPlant(PlantTypes.valueOf(entry.getKey().toString())));
            }
            plants.put(PlantTypes.valueOf(entry.getKey().toString()), hashSetPlants);
        }
    }

    public void initAnimals() {
        ThreadRandomData randomizer = new ThreadRandomData();
        AnimalFactory animalFactory = new AnimalFactory();
        for (Map.Entry<AnimalTypes, Integer> entry : maxAnimalCapacity.entrySet()) {
            HashSet<Animal> hashSetAnimals = new HashSet<>();
            for (int i = 0; i < randomizer.getThreadRandomData().nextInt(entry.getValue()); i++) {
                hashSetAnimals.add(animalFactory.createAnimal(AnimalTypes.valueOf(entry.getKey().toString())));
            }
            animals.put(AnimalTypes.valueOf(entry.getKey().toString()), hashSetAnimals);
        }
    }

    public HashMap<AnimalTypes, Integer> getMaxAnimalCapacity() {
        return maxAnimalCapacity;
    }

    public void setMaxAnimalCapacity(HashMap<AnimalTypes, Integer> maxAnimalCapacity) {
        this.maxAnimalCapacity = maxAnimalCapacity;
    }

    public HashMap<PlantTypes, Integer> getMaxPlantCapacity() {
        return maxPlantCapacity;
    }

    public void setMaxPlantCapacity(HashMap<PlantTypes, Integer> maxPlantCapacity) {
        this.maxPlantCapacity = maxPlantCapacity;
    }

    public HashMap<AnimalTypes, HashSet<Animal>> getAnimals() {
        return animals;
    }

    public HashMap<PlantTypes, HashSet<Plant>> getPlants() {
        return plants;
    }

    public void setAnimals(HashMap<AnimalTypes, HashSet<Animal>> animals) {
        this.animals = animals;
    }

    public void setPlants(HashMap<PlantTypes, HashSet<Plant>> plants) {
        this.plants = plants;
    }
}
