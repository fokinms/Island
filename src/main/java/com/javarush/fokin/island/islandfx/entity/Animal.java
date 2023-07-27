package com.javarush.fokin.island.islandfx.entity;

import com.javarush.fokin.island.islandfx.constants.AnimalStatus;

import java.util.HashMap;

public abstract class Animal extends Creature {

    private AnimalStatus status;
    private double maxWeight;
    private double minWeight;
    private double weight;
    private int speed;
    private double weightToSaturation;
    private HashMap<String, Integer> eatProbability;
    private int offspring;


    public HashMap<String, Integer> getEatProbability() {
        return eatProbability;
    }

    public void setEatProbability(HashMap<String, Integer> eatProbability) {
        this.eatProbability = eatProbability;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getWeightToSaturation() {
        return weightToSaturation;
    }

    public void setWeightToSaturation(double weightToSaturation) {
        this.weightToSaturation = weightToSaturation;
    }

    public int getOffspring() {
        return offspring;
    }

    public void setOffspring(int offspring) {
        this.offspring = offspring;
    }

    public void setStatus(AnimalStatus status) {
        this.status = status;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public AnimalStatus getStatus() {
        return status;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getMinWeight() {
        return minWeight;
    }
}
