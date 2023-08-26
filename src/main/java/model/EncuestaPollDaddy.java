package model;

import java.util.ArrayList;

public class EncuestaPollDaddy extends Encuesta {

    public EncuestaPollDaddy(ArrayList<Opcion> opciones) {
        super.setOpciones(opciones);
    }

    @Override
    public void mostrarResultados() {
        EncuestaConsola ec = new EncuestaConsola(getOpciones());
        ec.mostrarResultados();
    }
}
