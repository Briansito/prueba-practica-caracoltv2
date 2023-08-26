package model;

import java.util.ArrayList;

public class EncuestaConsola extends Encuesta {


    public EncuestaConsola(ArrayList<Opcion> opciones) {
        super.setOpciones(opciones);
    }

    @Override
    public void mostrarResultados() {
        System.out.println("----------------------" + getTitulo() + "------------------------");
        System.out.format("%-20s %-10s %-10s\n", "Titulo", "Votos", "Porcentaje");
        for (Opcion opcion : getOpciones()) {
            System.out.format("%-20s %-10s %-10s\n", opcion.getNombre(), opcion.getVotos(), opcion.getPorcentaje() + "%");
        }
    }
}
