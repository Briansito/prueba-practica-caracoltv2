package model;


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
    private URL apiUrl;
    private HttpURLConnection connection;
    private static ArrayList<Opcion> opciones;


    public PollDaddyClient(String apiUrl, HashMap<String, Object> data) {
        this.data = data;
        try {
            this.apiUrl = new URL(apiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.opciones = new ArrayList<>();
    }


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

    public void connect() {
        try {
            this.connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendJson(String requestBody) {
        try {
            OutputStream os = connection.getOutputStream();
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            PollDaddyResponseHandler handler = new PollDaddyResponseHandler(response.toString());
            opciones = handler.getOpciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }


    public void disconnect() {
        connection.disconnect();
    }
}
