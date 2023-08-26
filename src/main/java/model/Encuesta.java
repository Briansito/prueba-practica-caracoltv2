package model;

import java.util.ArrayList;

public abstract class Encuesta {
    private String titulo = "Resultados"; // Título de la encuesta
    private ArrayList<Opcion> opciones; // Opciones de la encuesta como ArrayList de Opcion

    public Encuesta() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<Opcion> opciones) {
        this.opciones = opciones;
    }

    public abstract void mostrarResultados();
}
