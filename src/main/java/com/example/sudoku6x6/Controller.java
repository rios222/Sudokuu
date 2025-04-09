package com.example.sudoku6x6;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    //  Boardgrip con los textfield, cada textfield tiene su etiqueta id que corresponde a su numero de celda

    private final Label statusLabel;

    public Controller(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    @FXML
    public void initialize() {
        // llamado para que cargue automaticamente el fxml
        System.out.println("Iniciando SudokuController");
        if (statusLabel !=null){
            statusLabel.setText("SUDOKU 6X6 :D");
        }
    }
}
