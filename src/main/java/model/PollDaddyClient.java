package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PollDaddyClient {
    private HashMap<String, Object> data;
    private URL apiUrl; // La dirección del API
    private HttpURLConnection connection; // La conexión con el API
    private static ArrayList<Opcion> opciones;


    public PollDaddyClient(String apiUrl, HashMap<String, Object> data) {
        this.data = data;
        try {
            this.apiUrl = new URL(apiUrl); // Asignar la dirección del API
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.opciones = new ArrayList<>();
    }

    // Declarar los métodos públicos para acceder y modificar los atributos

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public static ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    public URL getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(URL apiUrl) {
        this.apiUrl = apiUrl;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    // Declarar un método público para establecer la conexión con el API
    public void connect() {
        try {
            this.connection = (HttpURLConnection) apiUrl.openConnection(); // Abrir la conexión con el objeto URL
            connection.setRequestMethod("POST"); // Establecer el método de solicitud como POST
            connection.setRequestProperty("Content-Type", "application/json; utf-8"); // Establecer las propiedades del encabezado de la solicitud
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true); // Habilitar la entrada
            connection.setDoOutput(true); // Habilitar la salida
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Declarar un método público para enviar el objeto JSON al API
    public void sendJson(String requestBody) {
        try {
            OutputStream os = connection.getOutputStream(); // Obtener el flujo de salida de la conexión
            byte[] input = requestBody.getBytes("utf-8"); // Convertir el objeto JSON en un arreglo de bytes usando la codificación UTF-8
            os.write(input, 0, input.length); // Escribir el arreglo de bytes en el flujo de salida
            os.close(); // Cerrar el flujo de salida
            // Imprimir el código de respuesta del API
            //System.out.println("Response code: " + connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Declarar un método público para recibir la respuesta del API
    public String getResponse() {
        StringBuilder response = new StringBuilder(); // Crear un objeto StringBuilder que contenga la respuesta del API
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); // Obtener el flujo de entrada de la conexión y crear un objeto BufferedReader para leerlo usando la codificación UTF-8
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) { // Leer cada línea del flujo de entrada hasta que sea nula
                response.append(responseLine.trim()); // Añadir cada línea al objeto StringBuilder, eliminando los espacios en blanco al principio y al final
            }
            br.close();
            // Cerrar el flujo de entrada
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
                //System.out.println("response " + text + " " + total + " " + percent);
                Opcion opcion = new Opcion(total, percent, text);
                opciones.add(opcion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString(); // Devolver la respuesta del API como una cadena
    }

    // Declarar un método público para desconectar la conexión
    public void disconnect() {
        connection.disconnect(); // Desconectar la conexión
    }
}
