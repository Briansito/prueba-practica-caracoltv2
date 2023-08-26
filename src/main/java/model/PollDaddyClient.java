package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PollDaddyClient {
    private static String idEncuesta; // ID del usuario de polldaddy
    private static final String API_URL = "https://api.crowdsignal.com/v1";
    private static final String PARTNER_GUID = "5719aa21-c585-5aa9-42dd-00005547ef78";
    private static final String USER_CODE = "$P$BzM55aacFGir8JbSGtmXdZ7WGnKXiV1";
    private static ArrayList<Opcion> opciones;


    public PollDaddyClient(String idEncuesta) {
        this.idEncuesta = idEncuesta;
        this.opciones = new ArrayList<>(); // Inicializar el ArrayList vacío
        this.obtenerResultadosEncuesta();
    }

    public static ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    public static void setOpciones(ArrayList<Opcion> opciones) {
        PollDaddyClient.opciones = opciones;
    }

    public static String getIdEncuesta() {
        return idEncuesta;
    }

    public static void setIdEncuesta(String idEncuesta) {
        PollDaddyClient.idEncuesta = idEncuesta;
    }

    public static void obtenerResultadosEncuesta() {
        try {
            // URL de la API
            URL url = new URL(API_URL);
            // Cuerpo JSON
            String jsonBody = "{"
                    + "    \"pdRequest\": {"
                    + "        \"partnerGUID\": \"" + PARTNER_GUID + "\","
                    + "        \"userCode\": \"" + USER_CODE + "\","
                    + "        \"demands\": {"
                    + "            \"demand\": {"
                    + "                \"poll\": {"
                    + "                    \"id\": \"" + idEncuesta + "\""
                    + "                }, \"id\": \"GetPollResults\""
                    + "            }"
                    + "        }"
                    + "    }"
                    + "}";
            // Establecer la conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Configurar la solicitud como POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            // Escribir el cuerpo JSON en la solicitud
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonBody.getBytes());
            outputStream.flush();
            outputStream.close();
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JSONObject json = new JSONObject(response.toString());
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
                    Opcion opcion = new Opcion(total, percent, text);
                    opciones.add(opcion);
                }
            } else {
                System.out.println("Request failed. Response Code: " + responseCode);
            }
            // Cerrar la conexión
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("Algo salió mal en el cliente " + e.getMessage());
            //e.printStackTrace(System.out);
            System.exit(0);
        }
    }
}
