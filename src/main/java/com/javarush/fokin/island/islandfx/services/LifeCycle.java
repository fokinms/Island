package com.javarush.fokin.island.islandfx.services;

import com.javarush.fokin.island.islandfx.constants.*;
import com.javarush.fokin.island.islandfx.entity.Animal;
import com.javarush.fokin.island.islandfx.entity.Cell;
import com.javarush.fokin.island.islandfx.entity.Creature;
import com.javarush.fokin.island.islandfx.entity.Plant;
import java.util.*;
import java.util.stream.Collectors;

public class LifeCycle {
    Cell[][] islandCells;
    public LifeCycle(Cell[][] islandCells) {
        this.islandCells = islandCells;
    }

    public boolean canEat(Animal animal) {
        boolean caneat = false;
        if (animal.getWeight() < animal.getMinWeight()) {
            animal.setStatus(AnimalStatus.DEAD);
        } else if (animal.getWeight() > animal.getMinWeight() && animal.getWeight() <= animal.getMaxWeight()) {
            caneat = true;
        }
        return caneat;
    }

    public Set<Creature> getFoodSet(Cell cell, Animal animal) {
        HashMap<AnimalTypes, HashSet<Animal>> animals = cell.getCellLocation().getAnimals();
        HashMap<PlantTypes, HashSet<Plant>> plants = cell.getCellLocation().getPlants();

        Set<AnimalTypes> canEatAnimalTypes = animal.getEatProbability().entrySet().stream()
                .filter(x -> x.getValue() > 0)
                .filter(y -> {
                    boolean type;
                    type = !Arrays.stream(PlantTypes.values())
                            .map(Enum::name)
                            .collect(Collectors.toSet())
                            .contains(y.getKey().toUpperCase());
                    return type;
                })
                .map(t -> AnimalTypes.valueOf(t.getKey().toUpperCase()))
                .collect(Collectors.toSet());

        Set<PlantTypes> canEatPlantTypes = animal.getEatProbability().entrySet().stream()
                .filter(x -> x.getValue() > 0)
                .filter(y -> {
                    boolean type;
                    type = !Arrays.stream(AnimalTypes.values())
                            .map(Enum::name)
                            .collect(Collectors.toSet())
                            .contains(y.getKey().toUpperCase());
                    return type;
                })
                .map(t -> PlantTypes.valueOf(t.getKey().toUpperCase()))
                .collect(Collectors.toSet());

        Set<Animal> canEatAnimals = new HashSet<>();
        Set<Plant> canEatPlants = new HashSet<>();
        animals.entrySet().stream()
                .filter(x -> canEatAnimalTypes.contains(x.getKey()))
                .forEach(h -> h.getValue().stream().
                        filter(doa -> doa.getStatus() != AnimalStatus.DEAD).
                        forEach(canEatAnimals::add));
        plants.entrySet().stream()
                .filter(x -> canEatPlantTypes.contains(x.getKey()))
                .forEach(h -> canEatPlants.addAll(h.getValue()));

        Set<Creature> canEatCreatures = new HashSet<>();
        canEatCreatures.addAll(canEatAnimals);
        canEatCreatures.addAll(canEatPlants);

        return canEatCreatures;
    }

    public void toEat(Set<Creature> creatureSet, Animal animal) {
        ThreadRandomData threadRandomData = new ThreadRandomData();
        int numberOfFood = creatureSet.size();
        int makeChoice = threadRandomData.getThreadRandomData().nextInt(numberOfFood);
        Creature victim = creatureSet.stream().toList().get(makeChoice);

        int eatChanceData = animal.getEatProbability().entrySet().stream()
                .filter(x -> x.getKey().equalsIgnoreCase(victim.getClass().getSimpleName()))
                .map(Map.Entry::getValue)
                .toList()
                .get(0);

        int eatChanceRandom = threadRandomData.getThreadRandomData().nextInt(100);
        if (eatChanceRandom <= eatChanceData) {
            if (victim instanceof Plant) ((Plant) victim).setStatus(PlantStatus.DEAD);
            else ((Animal) victim).setStatus(AnimalStatus.DEAD);
        } else {
            return;
        }

        double maxWeight = animal.getMaxWeight();
        double victimWeight;
        if (victim instanceof Plant) {
            victimWeight = ((Plant) victim).getWeight();
        } else {
            victimWeight = ((Animal) victim).getWeight();
        }
        if (animal.getWeight() + victimWeight > maxWeight) {
            animal.setWeight(maxWeight);
        } else {
            animal.setWeight(animal.getWeight() + victimWeight);
        }
    }

    public boolean canBreed(Cell cell, Creature creature) {
        boolean canbreed = false;
        if (creature instanceof Animal) {
            if (((Animal) creature).getStatus() != AnimalStatus.DEAD) {
                Set<Animal> sameAnimalSet = new HashSet<>();
                cell.getCellLocation().getAnimals().entrySet().stream()
                        .filter(x -> creature.getClass().getSimpleName().equalsIgnoreCase(x.getKey().toString()))
                        .forEach(y -> sameAnimalSet.addAll(y.getValue()));
                int numberOfPopulation = sameAnimalSet.size();
                if (numberOfPopulation >= 2 && numberOfPopulation < cell.getCellLocation().getMaxAnimalCapacity().get(AnimalTypes.valueOf(creature.getClass().getSimpleName().toUpperCase()))) {
                    canbreed = true;
                }
            }
        }
        if (creature instanceof Plant) {
            if (((Plant) creature).getStatus() != PlantStatus.DEAD) {
                Set<Plant> samePlantSet = new HashSet<>();
                cell.getCellLocation().getPlants().entrySet().stream()
                        .filter(x -> creature.getClass().getSimpleName().equalsIgnoreCase(x.getKey().toString()))
                        .forEach(y -> samePlantSet.addAll(y.getValue()));
                int numberOfPopulation = samePlantSet.size();
                if (numberOfPopulation < cell.getCellLocation().getMaxPlantCapacity().get(PlantTypes.valueOf(creature.getClass().getSimpleName().toUpperCase()))) {
                    canbreed = true;
                }
            }
        }
        return canbreed;
    }

    public int getNumberOfOffspring(Creature creature) {
        ThreadRandomData threadRandomData = new ThreadRandomData();
        int numberOfOffspring = 0;
        if (creature instanceof Animal) {
            numberOfOffspring = threadRandomData.getThreadRandomData().nextInt(((Animal) creature).getOffspring());
        }
        if (creature instanceof Plant) {
            numberOfOffspring = threadRandomData.getThreadRandomData().nextInt(((Plant) creature).getOffspring());
        }
        return numberOfOffspring;
    }

    public void toBreed(Cell cell, Creature creature) {
        if (creature instanceof Animal) {
            HashMap<AnimalTypes, HashSet<Animal>> animals = cell.getCellLocation().getAnimals();
            HashSet<Animal> newAnimals = new HashSet<>();
            AnimalFactory animalFactory = new AnimalFactory();
            for (int i = 0; i < getNumberOfOffspring(creature); i++) {
                newAnimals.add(animalFactory.createAnimal(AnimalTypes.valueOf(creature.getClass().getSimpleName().toUpperCase())));
            }
            HashSet<Animal> oldAnimals = new HashSet<>();
                    animals.entrySet().stream()
                    .filter(x -> creature.getClass().getSimpleName().equalsIgnoreCase(x.getKey().name()))
                    .forEach(y -> oldAnimals.addAll(y.getValue()));
            HashSet<Animal> actualAnimals = new HashSet<>();
            actualAnimals.addAll(oldAnimals);
            actualAnimals.addAll(newAnimals);
            animals.put(AnimalTypes.valueOf(creature.getClass().getSimpleName().toUpperCase()), actualAnimals);
        }
        if (creature instanceof Plant) {
            HashMap<PlantTypes, HashSet<Plant>> plants = cell.getCellLocation().getPlants();
            HashSet<Plant> newPlants = new HashSet<>();
            PlantFactory plantFactory = new PlantFactory();
            for (int i = 0; i < getNumberOfOffspring(creature); i++) {
                newPlants.add(plantFactory.createPlant(PlantTypes.valueOf(creature.getClass().getSimpleName().toUpperCase())));
            }
            HashSet<Plant> oldPlants = new HashSet<>();
            plants.entrySet().stream()
                    .filter(x -> creature.getClass().getSimpleName().equalsIgnoreCase(x.getKey().name()))
                    .forEach(y -> oldPlants.addAll(y.getValue()));
            HashSet<Plant> actualPlants = new HashSet<>();
            actualPlants.addAll(oldPlants);
            actualPlants.addAll(newPlants);
            plants.put(PlantTypes.valueOf(creature.getClass().getSimpleName().toUpperCase()), actualPlants);
        }
    }

    public boolean canMove(Animal animal) {
        return animal.getStatus().equals(AnimalStatus.ALIVE);
    }

    public void toMove(Cell cell, Animal animal) {
        Cell workCell = cell;
        ThreadRandomData threadRandomData = new ThreadRandomData();
        int speed = animal.getSpeed();
        int steps;
        boolean makeDecision = threadRandomData.getThreadRandomData().nextInt(100) >= 50;
        if (makeDecision) {
            steps = threadRandomData.getThreadRandomData().nextInt(speed);
        } else {
            return;
        }

        for (int i = 0; i < steps; i++) {
            Set<Cell> neighbours = workCell.getCellLocation().getNeighbours();
            int randomNeighbour = threadRandomData.getThreadRandomData().nextInt();
            Cell newCell = (Cell) neighbours.toArray()[randomNeighbour];
            workCell = newCell;
        }

        HashSet<Animal> newCellAnimals = new HashSet<>();
        workCell.getCellLocation().getAnimals().entrySet().stream()
                .filter(x -> animal.getClass().getSimpleName().equalsIgnoreCase(x.getKey().name()))
                .forEach(y -> newCellAnimals.addAll(y.getValue()));
        HashSet<Animal> newCellActualAnimals = new HashSet<>(newCellAnimals);
        newCellActualAnimals.add(animal);
        workCell.getCellLocation().getAnimals().put(AnimalTypes.valueOf(animal.getClass().getSimpleName().toUpperCase()), newCellActualAnimals);

        HashSet<Animal> thisCellAnimals = new HashSet<>();
        cell.getCellLocation().getAnimals().entrySet().stream()
                .filter(x -> animal.getClass().getSimpleName().equalsIgnoreCase(x.getKey().name()))
                .forEach(y -> thisCellAnimals.addAll(y.getValue()));
        HashSet<Animal> thisCellActualAnimals = new HashSet<>(thisCellAnimals);
        thisCellActualAnimals.remove(animal);
        cell.getCellLocation().getAnimals().put(AnimalTypes.valueOf(animal.getClass().getSimpleName().toUpperCase()), thisCellActualAnimals);
    }

    public void removeDeadCreatures(Cell cell) {
        HashMap<AnimalTypes, HashSet<Animal>> animals = cell.getCellLocation().getAnimals();
        HashMap<PlantTypes, HashSet<Plant>> plants = cell.getCellLocation().getPlants();
        HashMap<AnimalTypes, HashSet<Animal>> animalsWithoutDead = new HashMap<>();
        HashMap<PlantTypes, HashSet<Plant>> plantsWithoutDead = new HashMap<>();

        for (Map.Entry<AnimalTypes, HashSet<Animal>> entry : animals.entrySet()) {
            HashSet<Animal> notDeadAnimals = new HashSet<>();
            for (Animal animal: entry.getValue()) {
                if (animal.getStatus() != AnimalStatus.DEAD) {
                    notDeadAnimals.add(animal);
                }
            }
            animalsWithoutDead.put(entry.getKey(), notDeadAnimals);
        }
        cell.getCellLocation().setAnimals(animalsWithoutDead);

        for (Map.Entry<PlantTypes, HashSet<Plant>> entry : plants.entrySet()) {
            HashSet<Plant> notDeadPlants = new HashSet<>();
            for (Plant plant: entry.getValue()) {
                if (plant.getStatus() != PlantStatus.DEAD) {
                    notDeadPlants.add(plant);
                }
            }
            plantsWithoutDead.put(entry.getKey(), notDeadPlants);
        }
        cell.getCellLocation().setPlants(plantsWithoutDead);
    }
}
