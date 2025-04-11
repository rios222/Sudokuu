package com.example.sudoku6x6;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

public class View {
	private GridPane boardGrid;
	private Text title;

	public View(GridPane boardGrid, Text title) {
		this.boardGrid = boardGrid;
		this.title = title;
	}

	// Metodo para la validación de celdas
	public void setupCellValidation() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				TextField cell = getCell(i, j);
				if (cell != null) {
					cell.textProperty().addListener((observable, oldValue, newValue) -> {
						if (!newValue.isEmpty()) {  // Solo validar si hay contenido
							try {
								int num = Integer.parseInt(newValue);
								if (num < 1 || num > 6) {
									cell.setText(oldValue); // Revertir al valor anterior
									showErrorAlert("Error", "El número digitado no está entre 1 y 6");
								}
							} catch (NumberFormatException e) {
								cell.setText(oldValue); // revertir al valor anterior
								showErrorAlert("Entrada inválida", "Solo se permiten números del 1 al 6");
							}
						}
					});
				}
			}
		}
	}

	// Metodo para mostrar alertas de error
	private void showErrorAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// Métodos para manipular celdas desde la view
	public void updateCell(int row, int col, String value) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setText(value);
		}
	}

	public void setCellStyle(int row, int col, String style) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setStyle(style);
		}
	}

	public void setCellEditable(int row, int col, boolean editable) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setEditable(editable);
		}
	}

	// Métodos para el tablero completo
	public void clearBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				updateCell(i, j, "");
				setCellStyle(i, j, "-fx-border-color: black;");
				setCellEditable(i, j, true);
			}
		}
	}

	public void setGenTablero(String[][] clues) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (clues[i][j] != null && !clues[i][j].isEmpty()) {
					updateCell(i, j, clues[i][j]);
					setCellStyle(i, j, "-fx-border-color: black; -fx-background-color: #f0f0f0;");
					setCellEditable(i, j, false);
				}
			}
		}
	}

	// Métodos para el título
	public void setTitle(String text) {
		if (title != null) {
			title.setText(text);
		}
	}

	// Método auxiliar privado
	private TextField getCell(int row, int col) {
		String cellId = "cell" + row + col;
		return (TextField) boardGrid.lookup("#" + cellId);
	}
}