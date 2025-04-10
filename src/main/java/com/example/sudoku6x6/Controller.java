package com.example.sudoku6x6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label statusLabel; // se conecta con fx:id="statusLabel" en FXML

    public Controller() {
        // Constructor vacío requerido por FXMLLoader
    }

    @FXML
    public void initialize() {
        System.out.println("Iniciando SudokuController");
        if (statusLabel != null) {
            statusLabel.setText("SUDOKU 6X6 :D");
        }
    }

    public void Start(ActionEvent actionEvent) {
    }

    public void Restart(ActionEvent actionEvent) {
    }

    public void Incognit(ActionEvent actionEvent) {
    }

    public void Help(ActionEvent actionEvent) {
    }
}
