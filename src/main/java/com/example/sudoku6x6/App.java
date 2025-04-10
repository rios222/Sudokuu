package com.example.sudoku6x6;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/sudoku6x6/sudoku_main.fxml")));
        primaryStage.setTitle("Sudoku 6x6");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();

    }
}
