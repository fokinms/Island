package com.javarush.fokin.island.islandfx.entity;

import javafx.scene.layout.Pane;

public class Cell extends Pane {
    private final Location cellLocation;

    public Cell() {
        cellLocation = new Location();
    }

    public Location getCellLocation() {
        return cellLocation;
    }
}

