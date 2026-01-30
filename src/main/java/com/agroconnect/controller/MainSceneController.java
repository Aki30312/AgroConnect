package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Platform;

public class MainSceneController {

    @FXML private Button farmersBtn;
    @FXML private Button productsBtn;
    @FXML private Button ordersBtn;
    @FXML private Button exitBtn;

    @FXML
    public void initialize() {

        farmersBtn.setOnAction(e ->
                System.out.println("Farmer Management clicked"));

        productsBtn.setOnAction(e ->
                System.out.println("Products clicked"));

        ordersBtn.setOnAction(e ->
                System.out.println("Orders clicked"));

        exitBtn.setOnAction(e -> Platform.exit());
    }
}
