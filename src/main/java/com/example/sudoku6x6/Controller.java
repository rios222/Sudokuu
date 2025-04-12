package com.example.sudoku6x6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class Controller {
	String[][] tablerofantasma;
	String[][] clue;
	// Elementos del menú principal
	@FXML private Label statusLabel;
	@FXML private Button btnIniciar;
	@FXML private Button btnComoJugar;
	@FXML private Button verificarSudokuButton;
	
	// Elementos del tablero
	@FXML private GridPane boardGrid;
	@FXML private Text Title;

	// vista y modelo (MVC)
	private View view;
	private Model model;

			view.setupCellValidation();
			Button verificarBtn = view.getVerificarButton();
			if (!boardGrid.getChildren().contains(verificarBtn)) {
				verificarBtn.setOnAction(this::verificarVictoria); // Asocia al método en el controller
				boardGrid.add(verificarBtn, 6, 0);  // Agrega el botón en el GridPane
			}
			
			if (statusLabel != null) {
				statusLabel.setText("SUDOKU 6X6 :D");
			}
		}
	}


	public void iniciarJuego(ActionEvent event) throws Exception {
					Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/sudoku6x6/sudoku_main.fxml")));
					Stage stage = new Stage();
					stage.setTitle("SUDOKU 6X6!!!");
					stage.setScene(new Scene(root, 480, 400));
					stage.show();
					((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
				}
				//instrucciones basicas del juegp
	public void comoJugar(ActionEvent event) {
		showAlert("Como se juega? D:", "Instrucciones del sudoku 6x6",
				"LLena la cuadrícula del 1 al 6 sin repetir número en filas, columnas o cuadros de 3x2.");
	}



	// encargado de iniciar la partida
	@FXML
	private void Start(ActionEvent event) {

		// 1. Mostrar alerta de confirmación
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Iniciar Partida");
		alert.setHeaderText("¿Quieres comenzar un nuevo juego?");
		alert.setContentText("Iniciando juego en 3..2..1...");

		// 2. Esperar respuesta del usuario
		alert.showAndWait().ifPresent(response -> {
			if (response == javafx.scene.control.ButtonType.OK) {
				// 3. Lógica para iniciar el juego si confirma
				view.clearBoard(); //limpia el tablero

				//Metodo encargado de generar los tableros
				String[][] generarClues = model.GenTablero();
				tablerofantasma = generarClues;
				clue = generarClues; //clue guarda la solucion
				for (int i = 0; i < tablerofantasma.length; i++) {
					for (int j = 0; j < tablerofantasma[i].length; j++) {
						int binario = (int) (Math.random() * 3); // Genera 0 o 1
						if (binario == 1) tablerofantasma[i][j] = generarClues[i][j]; //arreglar esto
						else tablerofantasma[i][j] = "";
					}
				}
				view.setGenTablero(tablerofantasma);
				view.setTitle("¡Juego Iniciado!");
			} //si hace click en cancelar no sucede nada
		});
	}

	//conexion con el boton de view y la ayuda
	@FXML
	private void Help(ActionEvent event) {
		comoJugar(event);
	}

	//Reinicia el juego y genera el tablero desde cero
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

				//Cambiar aqui con el tablero fantasma
				view.setGenTablero(tablerofantasma);


			}
		});
	}



	// Model // // / // / /// / / / / // / / / / / / // / / / / / / // / / / / / // / / / /

	@FXML
	private void Incognit(ActionEvent event) {
		int row = (int) (Math.random() * 6);
		int col = (int) (Math.random() * 6);


		// value genera el numero random
		view.updateCell(row, col, String.valueOf(clue[row][col]));
		System.out.println(clue[row][col]);
		view.setCellStyle(row, col, "-fx-border-color: blue; -fx-border-width: 2;");
		showAlert("Sugerencia", "Prueba con:", "Número " + clue[row][col]);
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

