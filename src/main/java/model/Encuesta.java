package model;

import java.util.ArrayList;

public abstract class Encuesta {
    private String titulo = "Resultados";
    private ArrayList<Opcion> opciones;

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
