package com.example.sudoku6x6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class Controller {
	// Elementos del menú principal
	@FXML private Label statusLabel;
	@FXML private Button btnIniciar;
	@FXML private Button btnComoJugar;
	
	// Elementos del tablero Sudoku
	@FXML private GridPane boardGrid;
	@FXML private Text Title;
	
	private View view;
	
	@FXML
	public void initialize() {
		if (statusLabel != null) {
			statusLabel.setText("SUDOKU 6X6 :D");
		}
		// Inicializa la View con los componentes gráficos
		if (boardGrid != null && Title != null) {
			view = new View(boardGrid, Title);
		}
	}
	
	/* Métodos para el menú principal  */
	public void iniciarJuego(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/sudoku6x6/sudoku_main.fxml")));
		Stage stage = new Stage();
		stage.setTitle("SUDOKU 6X6!!!");
		stage.setScene(new Scene(root, 700, 600));
		stage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
	
	public void comoJugar(ActionEvent event) {
		showAlert("Como se juega? D:", "Instrucciones del sudoku 6x6",
				"LLena la cuadrícula del 1 al 6 sin repetir número en filas, columnas o cuadros de 3x2.");
	}
	
	/* Métodos para el tablero Sudoku (modificados para usar View) ESPERANDO AL MODEL CON LA LOGICA DEL
	* SUDOKUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU */
	@FXML
	private void Start(ActionEvent event) {
		view.clearBoard();
		
		String[][] exampleClues = {
				{"1", "", "3", "", "5", ""},
				{"", "5", "", "1", "", "3"},
				{"2", "", "", "", "6", ""},
				{"", "6", "", "2", "", "4"},
				{"", "", "2", "", "4", ""},
				{"3", "", "", "", "1", ""}
		};
		
		view.setExampleClues(exampleClues);
		view.setTitle("¡Juego Iniciado!");
	}
	
	@FXML
	private void Help(ActionEvent event) {
		comoJugar(event);
	}
	
	@FXML
	private void Restart(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Reiniciar Juego");
		alert.setHeaderText("¿Estás seguro?");
		alert.setContentText("Se perderán todos los cambios.");
		
		alert.showAndWait().ifPresent(response -> {
			if (response == javafx.scene.control.ButtonType.OK) {
				view.clearBoard();
				view.setTitle("Sudoku 6x6 - Reiniciado");
			}
		});
	}
	
	@FXML
	private void Incognit(ActionEvent event) {
		int randomNum = (int) (Math.random() * 6) + 1;
		int row = (int) (Math.random() * 6);
		int col = (int) (Math.random() * 6);
		
		view.updateCell(row, col, String.valueOf(randomNum));
		view.setCellStyle(row, col, "-fx-border-color: blue; -fx-border-width: 2;");
		
		showAlert("Sugerencia", "Prueba con:", "Número " + randomNum);
	}
	
	/* Metodo auxiliar  */
	private void showAlert(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}