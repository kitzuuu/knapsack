package com.example.knapsack3d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import java.io.IOException;

public class UI extends Application {

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartUI.fxml"));
        stage.setTitle("Knapsack");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }
    public static void main(String[] args){
        launch(args);
    }
}
