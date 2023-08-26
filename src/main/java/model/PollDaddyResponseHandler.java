package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PollDaddyResponseHandler {
    private ArrayList<Opcion> opciones;
    String json;

    public PollDaddyResponseHandler(String json) {
        this.json = json;
        this.opciones = new ArrayList<>();
        this.processResponseData();
    }

    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    public String getJson() {
        return json;
    }

    public void processResponseData(){
        System.out.println("metodo processResponseData json: " + this.getJson());
        JSONObject json = new JSONObject(this.getJson());
        JSONObject pdResponse = json.getJSONObject("pdResponse");
        JSONObject demands = pdResponse.getJSONObject("demands");
        JSONArray demand = demands.getJSONArray("demand");
        JSONObject result = demand.getJSONObject(0).getJSONObject("result");
        JSONObject answers = result.getJSONObject("answers");
        JSONArray answer = answers.getJSONArray("answer");
        //llenar las opciones de la encuesta
        for (int i = 0; i < answer.length(); i++) {
            JSONObject answerObj = answer.getJSONObject(i);
            String text = answerObj.getString("text");
            int total = answerObj.getInt("total");
            int percent = answerObj.getInt("percent");
            System.out.println("dentro de process");
            System.out.println("response " + text + " " + total + " " + percent);
            Opcion opcion = new Opcion(total, percent, text);
            opciones.add(opcion);
        }
    }


}
