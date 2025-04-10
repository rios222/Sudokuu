package com.example.sudoku6x6;

import java.util.ArrayList;


public class Model {
    ArrayList<String> lista = new ArrayList<>();

    private void AnimacionTablero() {

    }


    private void Tablero() {

        lista.add("Hola");
        lista.add("Mundo");
        String elemento = lista.get(0);  // "Hola"
        lista.size();        // Número de elementos
        lista.contains("Hola"); // true si el elemento está
        lista.remove("Mundo");  // Elimina por valor
        lista.remove(0);     // Elimina por índice
        lista.clear();       // Vacía la lista


    }


}