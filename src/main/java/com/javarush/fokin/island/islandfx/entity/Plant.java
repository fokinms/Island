package com.javarush.fokin.island.islandfx.entity;

import com.javarush.fokin.island.islandfx.constants.PlantStatus;

public abstract class Plant extends Creature {
    private PlantStatus status;
    private double weight;
    private int offspring;


    public PlantStatus getStatus() {
        return status;
    }

    public void setStatus(PlantStatus status) {
        this.status = status;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getOffspring() {
        return offspring;
    }

    public void setOffspring(int offspring) {
        this.offspring = offspring;
    }

    void toBreed() {
    }
}
