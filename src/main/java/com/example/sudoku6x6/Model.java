package com.example.sudoku6x6;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    ArrayList<ArrayList<String>> sudoku = new ArrayList<>();

    ArrayList<ArrayList<String>> restriccionesFila = new ArrayList<>();
    ArrayList<ArrayList<String>> restriccionesColumna = new ArrayList<>();
    ArrayList<ArrayList<String>> restriccionesBloque = new ArrayList<>();

    int min = 1, max = 6;
    int numeroAleatorio;
    String letraBuscada = "";
    boolean encontrada = false;

    final ArrayList<String> letrasDisponibles = new ArrayList<>(Arrays.asList("X", "Y", "Z", "A", "B", "C"));

    public void numeroYletra() {
        numeroAleatorio = (int) (Math.random() * (max - min + 1) + min);
        if (numeroAleatorio == 1) letraBuscada = "X";
        if (numeroAleatorio == 2) letraBuscada = "Y";
        if (numeroAleatorio == 3) letraBuscada = "Z";
        if (numeroAleatorio == 4) letraBuscada = "A";
        if (numeroAleatorio == 5) letraBuscada = "B";
        if (numeroAleatorio == 6) letraBuscada = "C";
    }

    public int obtenerIndiceBloque(int fila, int columna) {
        int filaBloque = fila / 2;
        int colBloque = columna / 3;
        return filaBloque * 2 + colBloque;
    }

    public String[][] GenTablero() {
        String[][] clues = new String[6][6];
        boolean tableroCompleto = false;

        while (!tableroCompleto) {
            // Limpiar todo
            sudoku.clear();
            restriccionesFila.clear();
            restriccionesColumna.clear();
            restriccionesBloque.clear();

            for (int i = 0; i < 6; i++) {
                restriccionesFila.add(new ArrayList<>());
                restriccionesColumna.add(new ArrayList<>());
                restriccionesBloque.add(new ArrayList<>());
            }

            for (int i = 0; i < 36; i++) {
                sudoku.add(new ArrayList<>());
            }

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    clues[i][j] = "";
                }
            }

            boolean fallo = false;

            for (int i = 0; i < 6 && !fallo; i++) {
                for (int j = 0; j < 6 && !fallo; j++) {
                    encontrada = true;
                    int intentos = 50;
                    int bloqueIndex = obtenerIndiceBloque(i, j);

                    while (encontrada && intentos > 0) {
                        numeroYletra();

                        boolean enFila = restriccionesFila.get(i).contains(letraBuscada);
                        boolean enColumna = restriccionesColumna.get(j).contains(letraBuscada);
                        boolean enBloque = restriccionesBloque.get(bloqueIndex).contains(letraBuscada);

                        if (!enFila && !enColumna && !enBloque) {
                            encontrada = false;
                        } else {
                            intentos--;
                        }
                    }

                    if (encontrada) {
                        // Revisa todas las letras en orden
                        boolean encontradaOrden = false;
                        for (String letra : letrasDisponibles) {
                            boolean enFila = restriccionesFila.get(i).contains(letra);
                            boolean enColumna = restriccionesColumna.get(j).contains(letra);
                            boolean enBloque = restriccionesBloque.get(bloqueIndex).contains(letra);

                            if (!enFila && !enColumna && !enBloque) {
                                letraBuscada = letra;
                                numeroAleatorio = letrasDisponibles.indexOf(letra) + 1;
                                encontradaOrden = true;
                                encontrada = false;
                                break;
                            }
                        }

                        if (!encontradaOrden) {
                            // No hay letra válida, fallamos y reiniciamos el tablero
                            fallo = true;
                            break;
                        }
                    }

                    if (!encontrada) {
                        int posicionActual = i * 6 + j;
                        sudoku.get(posicionActual).add(String.valueOf(numeroAleatorio));
                        restriccionesFila.get(i).add(letraBuscada);
                        restriccionesColumna.get(j).add(letraBuscada);
                        restriccionesBloque.get(bloqueIndex).add(letraBuscada);
                        clues[i][j] = String.valueOf(numeroAleatorio);

                        System.out.println("Fila " + i + " Columna " + j + ": " + letraBuscada);
                    }
                }
            }

            tableroCompleto = !fallo;
        }

        System.out.println("Restricciones por fila: " + restriccionesFila);
        System.out.println("Restricciones por columna: " + restriccionesColumna);
        System.out.println("Restricciones por bloque: " + restriccionesBloque);

        return clues;
    }
}
