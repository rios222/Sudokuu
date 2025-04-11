package com.example.sudoku6x6;

import java.util.ArrayList;
import java.util.Objects;


public class Model {
    ArrayList<ArrayList<String>> sudoku = new ArrayList<>(); //Este los va a guardar
    ArrayList<ArrayList<String>> restricciones = new ArrayList<>(); // 0-5, 6-11, 12-17, 18-23, 24-29, 30-35 /
    // X=1, Y=2, Z=3, A=4, B=5, C=6   restricciones.get(0).add("A"); restricciones.get(0).add("B");
    int min = 1, max = 6;
    int numeroAleatorio = (int) (Math.random() * (max - min + 1) + min); // Entre 1 y 6

    String letraBuscada = "";
    boolean encontrada = false;

    //private void AnimacionTablero() {}

    public void numeroYletra() {
        //Numero y letra
        numeroAleatorio = (int) (Math.random() * (max - min + 1) + min); // Entre 1 y 6
        //Definir la letra de restriccion
        if (numeroAleatorio == 1) letraBuscada = "X";
        if (numeroAleatorio == 2) letraBuscada = "Y";
        if (numeroAleatorio == 3) letraBuscada = "Z";
        if (numeroAleatorio == 4) letraBuscada = "A";
        if (numeroAleatorio == 5) letraBuscada = "B";
        if (numeroAleatorio == 6) letraBuscada = "C";
    }

    public String[][] GenTablero() {
        // Inicializar el tablero y las restricciones
        sudoku.clear();
        restricciones.clear();
        for (int i = 0; i < 36; i++) {
            sudoku.add(new ArrayList<>());
            restricciones.add(new ArrayList<>());
        }
        String[][] clues = new String[6][6]; // ¡Este es el que se retorna!

        for (int i = 0; i < 6; i++) { // Inicializa el arreglo
            for (int j = 0; j < 6; j++) {
                clues[i][j] = "";
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                encontrada = false;
                numeroYletra();

                //como determino si se encuentra?
                if (Objects.equals(letraBuscada, restricciones.get(i).get(j))) {
                    encontrada = true;
                }

                while (encontrada) {
                    encontrada = false;
                    numeroYletra();
                    if (Objects.equals(letraBuscada, restricciones.get(i).get(j))) { // el error esta aqui
                        encontrada = true;
                        }
                    }

                if (!encontrada) { //Agregarla
                    int posicionActual = i * 6 + j; //conversion a "vector" de una matriz
                    sudoku.get(posicionActual).add(String.valueOf(numeroAleatorio)); //Agrega el numero al sudoku

                    restricciones.get(i).add(letraBuscada); // 0-1-2-3-4-5 vista en filas
                    restricciones.get(i + 1).add(letraBuscada); // 6-7-8-9-10-11
                    restricciones.get(i + 2).add(letraBuscada); // 12-13-14-15-16-17
                    restricciones.get(i + 3).add(letraBuscada); // 18-19-20-21-22-23
                    restricciones.get(i + 4).add(letraBuscada); // 24-25-26-27-28-29
                    restricciones.get(i + 5).add(letraBuscada); // 30-31-32-33-34-35
                    System.out.println(restricciones);
                    //restricciones.get(j).add(letraBuscada); // 0-6-12-18-24-30 vista en columnas
                    restricciones.get(j + 6).add(letraBuscada); // 1-7-13-19-25-31
                    restricciones.get(j + 12).add(letraBuscada); // 2-8-14-20-26-32
                    restricciones.get(j + 18).add(letraBuscada); // 3-9-15-21-27-33
                    restricciones.get(j + 24).add(letraBuscada); // 4-10-16-22-28-34
                    restricciones.get(j + 30).add(letraBuscada); // 5-11-17-23-29-35
                    System.out.println(restricciones);

                    // restricciones.get(posicionActual).add(letraBuscada); //celdas 3x2
                    clues[i][j] = String.valueOf(numeroAleatorio);
                    System.out.println("La letra " + letraBuscada + " fue agregada.");
                    }
                }
            }
            System.out.println(restricciones);
            return clues;
        }
    }
