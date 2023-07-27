package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.constants.AnimalStatus;
import com.javarush.fokin.island.islandfx.constants.AnimalTypes;
import com.javarush.fokin.island.islandfx.constants.CreatureProperties;
import com.javarush.fokin.island.islandfx.entity.*;

import java.util.HashMap;
import java.util.Map;

public class AnimalFactory {
    public Animal createAnimal(AnimalTypes animalType) {
        Animal animal = null;
        GetDataFromXML getDataFromXML = new GetDataFromXML();

        switch (animalType) {
            case BOA -> {
                animal = new Boa();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case BEAR -> {
                animal = new Bear();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case FOX -> {
                animal = new Fox();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case BOAR -> {
                animal = new Boar();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case DEER -> {
                animal = new Deer();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case DUCK -> {
                animal = new Duck();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case GOAT -> {
                animal = new Goat();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case WOLF -> {
                animal = new Wolf();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case EAGLE -> {
                animal = new Eagle();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case HORSE -> {
                animal = new Horse();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case MOUSE -> {
                animal = new Mouse();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case SHEEP -> {
                animal = new Sheep();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case RABBIT -> {
                animal = new Rabbit();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case BUFFALO -> {
                animal = new Buffalo();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
            case CATERPILLAR -> {
                animal = new Caterpillar();
                animal.setStatus(AnimalStatus.ALIVE);
                initPropertiesFromMap(getDataFromXML.createPropertiesMap(animalType.toString()), animal);
                initProbabilityFromMap(getDataFromXML.createProbabilityMap(animalType.toString()), animal);
            }
        }
        return animal;
    }

    private void initProbabilityFromMap(HashMap<String, String> probabilityMap, Animal animal) {
        HashMap<String, Integer> probabilityHashMap = new HashMap<>();
        for (Map.Entry<String, String> entry : probabilityMap.entrySet()) {
            probabilityHashMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));

        }
        animal.setEatProbability(probabilityHashMap);
    }

    private void initPropertiesFromMap(HashMap<String, String> propertiesMap, Animal animal) {
        for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
            if (entry.getKey().equals(CreatureProperties.SPEED)) {
                animal.setSpeed(Integer.parseInt(entry.getValue()));
            }
            if (entry.getKey().equals(CreatureProperties.WEIGHT)) {
                double weight = Double.parseDouble(entry.getValue());
                animal.setWeight(weight);
                animal.setMinWeight(weight/2);
                animal.setMaxWeight(weight*2);
            }
            if (entry.getKey().equals(CreatureProperties.WEIGHT_TO_SATURATION)) {
                animal.setWeightToSaturation(Double.parseDouble(entry.getValue()));
            }
            if (entry.getKey().equals(CreatureProperties.OFFSPRING)) {
                animal.setOffspring(Integer.parseInt(entry.getValue()));
            }
        }
    }
}
