package com.example.knapsack3d;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StartUIController {
    private Stage primaryStage;
    private Scene scene;
    private Parent root;
    public void switchToBoxABC(ActionEvent events) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ABCBox.fxml"));
        primaryStage = (Stage)((Node)events.getSource()).getScene().getWindow();
        primaryStage.setTitle("Knapsack");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public void switchToBoxLPT(ActionEvent events) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LPTBox.fxml"));
        primaryStage = (Stage)((Node)events.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setTitle("Knapsack");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void clickExit(ActionEvent e){
        System.exit(0);
    }
}
