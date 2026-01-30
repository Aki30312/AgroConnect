package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainSceneController {

    @FXML private StackPane contentArea;

    // Make this accessible by other controllers
    private static MainSceneController instance;

    @FXML
    public void initialize() {
        instance = this;
        switchView("login.fxml"); // first screen
    }

    public static MainSceneController getInstance() {
        return instance;
    }

    public void switchView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}