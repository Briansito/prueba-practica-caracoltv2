package model;

import java.util.ArrayList;

public class EncuestaPollDaddy extends Encuesta {

    public EncuestaPollDaddy(ArrayList<Opcion> opciones) {
        super.setOpciones(opciones);
    }

    @Override
    public void mostrarResultados() {
        System.out.println("------------------" + getTitulo() + "------------------------");
        System.out.format("%-20s %-10s %-10s\n", "Titulo", "Votos", "Porcentaje");
        for (Opcion opcion : getOpciones()) {
            System.out.format("%-20s %-10s %-10s\n", opcion.getNombre(), opcion.getVotos(), opcion.getPorcentaje() + "%");
        }
    }
}
