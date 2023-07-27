package com.javarush.fokin.island.islandfx;


import com.javarush.fokin.island.islandfx.constants.Configuration;
import com.javarush.fokin.island.islandfx.entity.Cell;
import com.javarush.fokin.island.islandfx.entity.Island;
import com.javarush.fokin.island.islandfx.services.Statistics;
import com.javarush.fokin.island.islandfx.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.javarush.fokin.island.islandfx.services.RunnableTaskListMaker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class IslandController extends View implements Initializable {

    Cell[][] islandCells;
    Statistics statistics;
    ScheduledExecutorService scheduledExecutorService;
    ExecutorService mainExecutorService;
    public AtomicInteger daysOfEmulation = new AtomicInteger(0);
    private Cell currentCell;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Button createIslandButton;
    @FXML
    private Button startIslandButton;
    @FXML
    private Button stopIslandButton;
    @FXML
    private GridPane islandGridPane;
    @FXML
    private TextField xSizeTF;
    @FXML
    private TextField ySizeTF;
    @FXML
    public Label cellWolfQuantity;
    @FXML
    public Label cellMouseQuantity;
    @FXML
    public Label cellBoaQuantity;
    @FXML
    public Label cellFoxQuantity;
    @FXML
    public Label cellBearQuantity;
    @FXML
    public Label cellEagleQuantity;
    @FXML
    public Label cellHorseQuantity;
    @FXML
    public Label cellDeerQuantity;
    @FXML
    public Label cellRabbitQuantity;
    @FXML
    public Label cellGoatQuantity;
    @FXML
    public Label cellSheepQuantity;
    @FXML
    public Label cellBoarQuantity;
    @FXML
    public Label cellBuffaloQuantity;
    @FXML
    public Label cellDuckQuantity;
    @FXML
    public Label cellCaterpillarQuantity;
    @FXML
    public Label cellGrassQuantity;
    @FXML
    public Label islandWolfQuantity;
    @FXML
    public Label islandMouseQuantity;
    @FXML
    public Label islandBoaQuantity;
    @FXML
    public Label islandFoxQuantity;
    @FXML
    public Label islandBearQuantity;
    @FXML
    public Label islandEagleQuantity;
    @FXML
    public Label islandHorseQuantity;
    @FXML
    public Label islandDeerQuantity;
    @FXML
    public Label islandRabbitQuantity;
    @FXML
    public Label islandGoatQuantity;
    @FXML
    public Label islandSheepQuantity;
    @FXML
    public Label islandBoarQuantity;
    @FXML
    public Label islandBuffaloQuantity;
    @FXML
    public Label islandDuckQuantity;
    @FXML
    public Label islandCaterpillarQuantity;
    @FXML
    public Label islandGrassQuantity;
    @FXML
    public Label dayOfEmulationLabel;
    @FXML
    public Label numberOfAnimalsLabel;
    @FXML
    public Label numberOfPlantsLabel;
    @FXML
    public Label timeOfCreationLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setGrid() {
        Island island;
        if (checkTheParameters()) {
            island = new Island(Integer.parseInt(xSizeTF.getCharacters().toString()), Integer.parseInt(ySizeTF.getCharacters().toString()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Configuration.ERROR_WINDOW_TITLE);
            alert.setContentText(Configuration.ERROR_WINDOW_CONTENT);
            alert.showAndWait();
            return;
        }

        createIslandButton.setDisable(true);
        startIslandButton.setDisable(false);
        stopIslandButton.setDisable(true);
        xSizeTF.setDisable(true);
        ySizeTF.setDisable(true);

        Background activeCellBackground = new Background(new BackgroundFill(Color.ORANGERED, new CornerRadii(5), null));
        Background ordinaryCellBackground = new Background(new BackgroundFill(Color.BLUE, new CornerRadii(5), null));

        daysOfEmulation.set(0);

        Label[] infoLabels = {dayOfEmulationLabel, numberOfPlantsLabel, numberOfAnimalsLabel,
                cellBearQuantity, cellBoaQuantity, cellBoarQuantity, cellBuffaloQuantity, cellCaterpillarQuantity,
                cellDeerQuantity, cellDuckQuantity, cellEagleQuantity, cellFoxQuantity, cellHorseQuantity,
                cellMouseQuantity, cellRabbitQuantity, cellSheepQuantity, cellWolfQuantity, cellGoatQuantity,
                cellGrassQuantity, islandBearQuantity, islandBoaQuantity, islandBoarQuantity, islandBuffaloQuantity,
                islandCaterpillarQuantity, islandDeerQuantity, islandDuckQuantity, islandEagleQuantity, islandFoxQuantity,
                islandHorseQuantity, islandMouseQuantity, islandRabbitQuantity, islandSheepQuantity, islandWolfQuantity,
                islandGoatQuantity, islandGrassQuantity};

        for (Label label : infoLabels) {
            label.setText("N/A");
        }

        timeOfCreationLabel.setText(new SimpleDateFormat(Configuration.TIME_OF_CREATION_FORMAT).format(new Date(island.getTimeOfCreation())));
        islandCells = island.getIslandCells();
        statistics = new Statistics(islandCells, this);
        int x = islandCells.length;
        int y = islandCells[0].length;

        GridPane gp = islandGridPane;
        gp.getColumnConstraints().clear();
        gp.getRowConstraints().clear();
        gp.getChildren().clear();

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth((1.0f / ((float) x)) * 100.0f);
        for (int i = 0; i < x; i++) {
            gp.getColumnConstraints().add(column);
        }

        RowConstraints line = new RowConstraints();
        line.setPercentHeight((1.0f / ((float) y)) * 100.0f);
        for (int i = 0; i < y; i++) {
            gp.getRowConstraints().add(line);
        }

        for (int i = 0; i < islandCells.length; i++) {
            for (int j = 0; j < islandCells[i].length; j++) {
                islandCells[i][j].setBackground(ordinaryCellBackground);
                int finalI = i;
                int finalJ = j;
                islandCells[i][j].setOnMouseClicked(e -> {
                    currentCell.setBackground(ordinaryCellBackground);
                    currentCell = islandCells[finalI][finalJ];
                    islandCells[finalI][finalJ].setBackground(activeCellBackground);
                    System.err.println(currentCell);
                });
                gp.add(islandCells[i][j], i, j);
            }
        }
        currentCell = islandCells[0][0];
        currentCell.setBackground(activeCellBackground);
    }

    public void startEmulation() {
        createIslandButton.setDisable(true);
        startIslandButton.setDisable(true);
        stopIslandButton.setDisable(false);

        RunnableTaskListMaker runnableTaskListMaker = new RunnableTaskListMaker(islandCells);
        List<Runnable> OneDayFullList = new ArrayList<>();

        mainExecutorService = Executors.newFixedThreadPool(8);
        scheduledExecutorService = Executors.newScheduledThreadPool(8);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            OneDayFullList.clear();
            OneDayFullList.addAll(runnableTaskListMaker.MakeOneDayRunnableTaskList());
            for (Runnable runnable : OneDayFullList) {
                mainExecutorService.submit(runnable);
            }
            Platform.runLater(statistics);
        }, 3, 5, TimeUnit.SECONDS);
    }

    public void stopEmulation() {
        createIslandButton.setDisable(false);
        startIslandButton.setDisable(true);
        stopIslandButton.setDisable(true);

        xSizeTF.setDisable(false);
        ySizeTF.setDisable(false);
        xSizeTF.clear();
        ySizeTF.clear();


        mainExecutorService.shutdownNow();
        scheduledExecutorService.shutdownNow();
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    private boolean checkTheParameters() {
        return (!xSizeTF.getCharacters().isEmpty() && !ySizeTF.getCharacters().isEmpty() && xSizeTF.getCharacters().toString().matches("[-+]?\\d+") && ySizeTF.getCharacters().toString().matches("[-+]?\\d+"));
    }
}