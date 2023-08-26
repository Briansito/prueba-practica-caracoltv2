package PollApi;

import model.EncuestaPollDaddy;
import model.PollDaddyClient;

public class Main {
    public static void main(String[] args) {

        PollDaddyClient client = new PollDaddyClient("105031734");
        EncuestaPollDaddy encuesta = new EncuestaPollDaddy(client.getOpciones());
        encuesta.mostrarResultados();

    }

}