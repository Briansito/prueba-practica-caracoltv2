package PollApi;

import model.EncuestaPollDaddy;
import model.PollDaddyClient;
import java.util.HashMap;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Object> encuestaAttr = new HashMap<>();

        JSONObject pdRequest = new JSONObject();
        JSONObject demands = new JSONObject();
        JSONObject demand = new JSONObject();
        JSONObject poll = new JSONObject();
        JSONObject jsonbody = new JSONObject();

        poll.put("id", "10503173");
        demand.put("poll", poll);
        demand.put("id", "GetPollResults");
        demands.put("demand", demand);
        pdRequest.put("partnerGUID", "5719aa21-c585-5aa9-42dd-00005547ef78");
        pdRequest.put("userCode", "$P$BzM55aacFGir8JbSGtmXdZ7WGnKXiV1");
        pdRequest.put("demands", demands);
        jsonbody.put("pdRequest", pdRequest);
        encuestaAttr.put("json", jsonbody);

        PollDaddyClient encPollDaddy = new PollDaddyClient("https://api.crowdsignal.com/v1",encuestaAttr);

        encPollDaddy.connect();
        String requestBody = encPollDaddy.getData().get("json").toString();

        encPollDaddy.sendJson(requestBody);

        String response = encPollDaddy.getResponse();

        encPollDaddy.disconnect();

        EncuestaPollDaddy encuesta = new EncuestaPollDaddy(encPollDaddy.getOpciones());
        encuesta.mostrarResultados();
    }

}

