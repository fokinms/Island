package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.entity.Animal;
import com.javarush.fokin.island.islandfx.entity.Cell;
import com.javarush.fokin.island.islandfx.entity.Creature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunnableTaskListMaker {

    Cell[][] islandCells;
    LifeCycle lifeCycle;

    public RunnableTaskListMaker(Cell[][] islandCells) {
        this.islandCells = islandCells;
        this.lifeCycle = new LifeCycle(islandCells);
    }

    private List<Runnable> makeEatTaskList() {
        List<Runnable> runnableListEat = new ArrayList<>();
        for (Cell[] cell : islandCells) {
            for (Cell thisCell : cell) {
                Set<Creature> thisCreatures = new HashSet<>();
                thisCell.getCellLocation().getAnimals().values()
                        .forEach(thisCreatures::addAll);
                thisCell.getCellLocation().getPlants().values()
                        .forEach(thisCreatures::addAll);
                thisCreatures.forEach(creature -> runnableListEat.add(() -> {
                    if (creature instanceof Animal) {
                        if (lifeCycle.canEat((Animal) creature)) {
                            lifeCycle.toEat(lifeCycle.getFoodSet(thisCell, (Animal) creature), (Animal) creature);
                        }
                    }
                }));
            }
        }
        return runnableListEat;
    }

    private List<Runnable> makeBreedTaskList() {
        List<Runnable> runnableListBreed = new ArrayList<>();
        for (Cell[] cell : islandCells) {
            for (Cell thisCell : cell) {
                Set<Creature> thisCreatures = new HashSet<>();
                thisCell.getCellLocation().getAnimals().values()
                        .forEach(thisCreatures::addAll);
                thisCell.getCellLocation().getPlants().values()
                        .forEach(thisCreatures::addAll);
                thisCreatures.forEach(creature -> runnableListBreed.add(() -> {
                    if (lifeCycle.canBreed(thisCell, creature)) {
                        lifeCycle.toBreed(thisCell, creature);
                    }
                }));
            }
        }
        return runnableListBreed;
    }

    private List<Runnable> makeMoveTaskList() {
        List<Runnable> runnableListMove = new ArrayList<>();
        for (Cell[] cell : islandCells) {
            for (Cell thisCell : cell) {
                Set<Creature> thisCreatures = new HashSet<>();
                thisCell.getCellLocation().getAnimals().values()
                        .forEach(thisCreatures::addAll);
                thisCreatures.forEach(creature -> runnableListMove.add(() -> {
                    if (lifeCycle.canMove((Animal) creature)) {
                        lifeCycle.toMove(thisCell, (Animal) creature);
                    }
                }));
            }
        }
        return runnableListMove;
    }

    private List<Runnable> makeRemoveDeadsTaskList() {
        List<Runnable> runnableListRemoveDeads = new ArrayList<>();
        for (Cell[] cell : islandCells) {
            for (Cell thisCell : cell) {
                runnableListRemoveDeads.add(() -> lifeCycle.removeDeadCreatures(thisCell));
            }
        }
        return runnableListRemoveDeads;
    }

    public List<Runnable> MakeOneDayRunnableTaskList() {
        List<Runnable> runnableListOneDay = new ArrayList<>();
        runnableListOneDay.addAll(makeEatTaskList());
        runnableListOneDay.addAll(makeBreedTaskList());
        runnableListOneDay.addAll(makeMoveTaskList());
        runnableListOneDay.addAll(makeRemoveDeadsTaskList());
        return runnableListOneDay;
    }
}
