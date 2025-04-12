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
	// Elementos del menú principal
	@FXML private Label statusLabel;
	@FXML private Button btnIniciar;
	@FXML private Button btnComoJugar;
	@FXML private Button verificarSudokuButton;
	
	// Elementos del tablero Sudoku
	@FXML private GridPane boardGrid;
	@FXML private Text Title;
	
	private View view;
	private Model model;
	private String[][] clues;
	
	@FXML
	private void verificarVictoria(ActionEvent event) {
		// Verificar si todas las celdas están llenas
		if (!view.isBoardComplete()) {
			showAlert("Error", "El tablero aún tiene celdas vacías.", "Por favor, completa todas las celdas antes de verificar.");
			return;
		}
		
		boolean esValido = model.esSudokuValido(); // Asumiendo que tienes un modelo que realiza la validación
		
		if (esValido) {
			showAlert("¡Felicidades!", null, "El Sudoku ha sido completado correctamente 🎉");
		} else {
			showAlert("Sudoku no válido", null, "Revisa tus números e intenta nuevamente.");
		}
	}
	@FXML
	private void verificarSudoku() {
		if (model.esSudokuValido()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("¡Sudoku correcto!");
			alert.setHeaderText(null);
			alert.setContentText("¡Felicidades! Completaste correctamente el Sudoku.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error en el Sudoku");
			alert.setHeaderText(null);
			alert.setContentText("Hay errores en el tablero. Verifica los valores ingresados.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void initialize() {
		if (model == null) {
			model = new Model();  // Instancia del Modelo
		}
		
		if (view == null && boardGrid != null && Title != null) {
			view = new View(boardGrid, Title);  // Instancia de la Vista
			model.setView(view);  // Pasamos la vista al modelo
			
			// Llamamos a setupCellValidation solo si view ya ha sido inicializada
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

	

	/* Métodos para el menú principal  */
	public void iniciarJuego(ActionEvent event) throws Exception {
					Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/sudoku6x6/sudoku_main.fxml")));
					Stage stage = new Stage();
					stage.setTitle("SUDOKU 6X6!!!");
					stage.setScene(new Scene(root, 480, 400));
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

		// 1. Mostrar alerta de confirmación
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Iniciar Partida");
		alert.setHeaderText("¿Quieres comenzar un nuevo juego?");
		alert.setContentText("Iniciando juego en 3..2..1...");

		// 2. Esperar respuesta del usuario
		alert.showAndWait().ifPresent(response -> {
			if (response == javafx.scene.control.ButtonType.OK) {
				// 3. Lógica para iniciar el juego si confirma
				view.clearBoard();
				String[][] generarClues = model.GenTablero();
				view.setGenTablero(generarClues);
				view.setTitle("¡Juego Iniciado!");
			} //si hace click en cancelar no sucede nada
		});
	}

	@FXML
	private void Help(ActionEvent event) {
		comoJugar(event);
	}


	// Model //// // // / / // / / /// / / / // / / / /

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



	// Model // // / // / /// / / / / // / / / / / / // / / / / / / // / / / / / // / / / /

	@FXML
	private void Incognit(ActionEvent event) {
		int randomNum = (int) (Math.random() * 6) + 1;
		int row = (int) (Math.random() * 6);
		int col = (int) (Math.random() * 6);

		// value genera el numero random
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

