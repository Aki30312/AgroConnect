package com.agroconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.agroconnect.util.DBInit;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DBInit.createTables();

        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Agri-Connect");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
