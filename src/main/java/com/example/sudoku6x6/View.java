package com.example.sudoku6x6;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

//Contiene el tablero y el titulo
public class View {
	private GridPane boardGrid;
	private Text title;

	//Constructor que recibe el Gridpane y el titulo
	public View(GridPane boardGrid, Text title) {
		this.boardGrid = boardGrid;
		this.title = title;
	}

	// Este método revisa si el número ya está en la fila, columna o bloque 2x3
	private boolean esEntradaValida(int fila, int columna, String valor) {
		for (int i = 0; i < 6; i++) {
			// Verifica fila
			if (i != columna) {
				TextField cell = getCell(fila, i);
				if (cell != null && valor.equals(cell.getText())) {
					return false;
				}
			}
			// Verifica columna
			if (i != fila) {
				TextField cell = getCell(i, columna);
				if (cell != null && valor.equals(cell.getText())) {
					return false;
				}
			}
		}

		// Verifica el bloque 2x3
		int bloqueFila = (fila / 2) * 2;
		int bloqueCol = (columna / 3) * 3;

		for (int i = bloqueFila; i < bloqueFila + 2; i++) {
			for (int j = bloqueCol; j < bloqueCol + 3; j++) {
				if (i == fila && j == columna) continue;
				TextField cell = getCell(i, j);
				if (cell != null && valor.equals(cell.getText())) {
					return false;
				}
			}
		}

		return true;
	}

	// Metodo para la validación de celdas para que solo acepten numeros del 1 al 6, de no ser asi muestra un mensaje de error
	public void setupCellValidation(String[][] tablero, Label cuatros) {
		String sustituto[][] = new String[6][6];;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				TextField cell = getCell(i, j);
				sustituto[i][j] = tablero[i][j];
				int fila = i;
				int col = j;
				if (cell != null) {
					cell.textProperty().addListener((observable, oldValue, newValue) -> {
						sustituto[fila][col] = newValue;
						cuatros(sustituto, cuatros);
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


	public void cuatros(String[][] tablero, Label Labelcuatros) {
		int cuatros = 6;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j].equals("4")) cuatros--;
			}
		}
		if (cuatros > 0) Labelcuatros.setText("Faltan " + cuatros + " Cuatros");
		if (cuatros < 0) Labelcuatros.setText("Tienes " +cuatros +" Cuatros de mas");
	}




	// alerta de error comun
	private void showErrorAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// Metodo para manipular celdas desde la view
	public void updateCell(int row, int col, String value) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setText(value);
		}
	}

    //cambia estilo visual de la celda
	public void setCellStyle(int row, int col, String style) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setStyle(style);
		}
	}
    // activa o desactiva la celda
	public void setCellEditable(int row, int col, boolean editable) {
		TextField cell = getCell(row, col);
		if (cell != null) {
			cell.setEditable(editable);
		}
	}

	// limpia el tablero visualmente
	public void clearBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				updateCell(i, j, "");
				setCellStyle(i, j, "-fx-border-color: black;");
				setCellEditable(i, j, true);
			}
		}
	}
    //agrega las pistas generadas y bloquea las celdas
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

	// Texto del titulo
	public void setTitle(String text) {
		if (title != null) {
			title.setText(text);
		}
	}
    //Busca y retorna el TextField correspondiente a la posición (row, col) usando su ID en el GridPane
	private TextField getCell(int row, int col) {
		String cellId = "cell" + row + col;
		return (TextField) boardGrid.lookup("#" + cellId);
	}
}