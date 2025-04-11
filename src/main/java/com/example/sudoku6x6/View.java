package com.example.sudoku6x6;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class View {
	private GridPane boardGrid;
	private Text title;
	
	public View(GridPane boardGrid, Text title) {
		this.boardGrid = boardGrid;
		this.title = title;
	}
	
	// Metodos para manipular celdas desde la view
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
	
	public void setExampleClues(String[][] clues) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (!clues[i][j].isEmpty()) {
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
	
	// Metodo auxiliar privado
	private TextField getCell(int row, int col) {
		String cellId = "cell" + row + col;
		return (TextField) boardGrid.lookup("#" + cellId);
	}
}
