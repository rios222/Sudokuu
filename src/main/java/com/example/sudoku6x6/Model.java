package com.example.sudoku6x6;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
	ArrayList <ArrayList <String>> sudoku = new ArrayList <>();
	
	ArrayList <ArrayList <String>> restriccionesFila = new ArrayList <>();
	ArrayList <ArrayList <String>> restriccionesColumna = new ArrayList <>();
	ArrayList <ArrayList <String>> restriccionesBloque = new ArrayList <>();
	private View view;
	
	public void setView(View view) {
		this.view = view;
	}
	
	
	int min = 1, max = 6;
	int numeroAleatorio;
	String letraBuscada = "";
	boolean encontrada = false;
	
	final ArrayList <String> letrasDisponibles = new ArrayList <>(Arrays.asList("1", "2", "3", "4", "5", "6"));
	
	public void numeroYletra() {
		int indice = (int) (Math.random() * letrasDisponibles.size());
		letraBuscada = letrasDisponibles.get(indice);
		numeroAleatorio = Integer.parseInt(letraBuscada);
	}
	
	public int obtenerIndiceBloque(int fila, int columna) {
		int filaBloque = fila / 2;
		int colBloque = columna / 3;
		return filaBloque * 2 + colBloque;
	}
	
	
	public String[][] GenTablero() {
		sudoku.clear();
		for (int i = 0; i < 6; i++) {
			ArrayList<String> fila = new ArrayList<>();
			for (int j = 0; j < 6; j++) {
				fila.add("");
			}
			sudoku.add(fila);
		}
		
		resolverSudoku(0, 0); // Generar solución completa
		
		String[][] clues = new String[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				clues[i][j] = sudoku.get(i).get(j);
			}
		}
		
		// Inicializar bloques con posiciones disponibles
		ArrayList<ArrayList<int[]>> bloques = new ArrayList<>();
		for (int bloque = 0; bloque < 6; bloque++) {
			ArrayList<int[]> posiciones = new ArrayList<>();
			int filaBase = (bloque / 2) * 2;
			int colBase = (bloque % 2) * 3;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					posiciones.add(new int[]{filaBase + i, colBase + j});
				}
			}
			bloques.add(posiciones);
		}
		
		// Elegimos aleatoriamente un bloque que solo tendrá 1 pista
		int bloqueConUnaPista = (int)(Math.random() * 6);
		
		// Inicializamos todas las celdas como vacías
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				clues[i][j] = "";
			}
		}
		
		// Ahora colocamos pistas en los bloques
		for (int b = 0; b < 6; b++) {
			ArrayList<int[]> posiciones = bloques.get(b);
			java.util.Collections.shuffle(posiciones);
			int pistasEnEsteBloque = (b == bloqueConUnaPista) ? 1 : 2;
			
			for (int k = 0; k < pistasEnEsteBloque; k++) {
				int[] pos = posiciones.get(k);
				clues[pos[0]][pos[1]] = sudoku.get(pos[0]).get(pos[1]);
			}
		}
		
		return clues;
	}
	private boolean resolverSudoku(int fila, int col) {
		if (fila == 6) return true;
		
		int nextFila = (col == 5) ? fila + 1 : fila;
		int nextCol = (col + 1) % 6;
		
		if (!sudoku.get(fila).get(col).equals("")) {
			return resolverSudoku(nextFila, nextCol);
		}
		
		for (String letra : letrasDisponibles) {
			if (esValido(fila, col, letra)) {
				sudoku.get(fila).set(col, letra);
				
				if (resolverSudoku(nextFila, nextCol)) return true;
				
				sudoku.get(fila).set(col, ""); // Backtrack
			}
		}
		
		return false;
	}
	
	private boolean esValido(int fila, int col, String letra) {
		// Revisar fila y columna
		for (int i = 0; i < 6; i++) {
			if (sudoku.get(fila).get(i).equals(letra)) return false;
			if (sudoku.get(i).get(col).equals(letra)) return false;
		}
		
		// Revisar bloque 2x3
		int filaBloque = (fila / 2) * 2;
		int colBloque = (col / 3) * 3;
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (sudoku.get(filaBloque + i).get(colBloque + j).equals(letra)) return false;
			}
		}
		
		return true;
	}
	
	// Verifica el tablero actual del usuario usando la vista
	public boolean esSudokuValido() {
		if (view == null) return false;
		
		for (int fila = 0; fila < 6; fila++) {
			for (int col = 0; col < 6; col++) {
				String valor = view.getCellValue(fila, col);
				
				if (!valor.matches("[1-6]")) return false;
				
				view.updateCell(fila, col, ""); // Vacía temporalmente para no autocompararse
				
				if (!esValidoDesdeVista(fila, col, valor)) {
					view.updateCell(fila, col, valor); // Restaurar
					return false;
				}
				
				view.updateCell(fila, col, valor); // Restaurar
			}
		}
		
		return true;
	}
	
	private boolean esValidoDesdeVista(int fila, int col, String valor) {
		// Validar fila y columna
		for (int i = 0; i < 6; i++) {
			if (i != col && view.getCellValue(fila, i).equals(valor)) return false;
			if (i != fila && view.getCellValue(i, col).equals(valor)) return false;
		}
		
		// Validar bloque 2x3
		int filaBloque = (fila / 2) * 2;
		int colBloque = (col / 3) * 3;
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				int f = filaBloque + i;
				int c = colBloque + j;
				if ((f != fila || c != col) && view.getCellValue(f, c).equals(valor)) return false;
			}
		}
		
		return true;
	}
}

	
