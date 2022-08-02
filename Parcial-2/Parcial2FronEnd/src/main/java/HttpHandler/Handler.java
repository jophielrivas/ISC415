package HttpHandler;

import com.google.gson.Gson;
import model.Encuestador;
import model.Formulario;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Handler {
    public Encuestador[] HandlerHTTPEncuestador() {
        try  {

            URL url = new URL("http://localhost:7072/encuestadores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String totalOutput = "";


            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                totalOutput += output;
            }

            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Encuestador[] target = gson.fromJson(totalOutput, Encuestador[].class);
            /*
            System.out.println(target.toString());

            for (int i = 0; i < target.length; i++) {
                System.out.println(target[i].getUserName());
            }*/
            conn.disconnect();
            return target;

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    };

    public Formulario[] HandlerHTTPFormulario() {
        try  {

            URL url = new URL("http://localhost:7072/encuestados");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String totalOutput = "";


            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                totalOutput += output;
            }

            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Formulario[] target = gson.fromJson(totalOutput, Formulario[].class);
            conn.disconnect();
            return target;

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    };



    public void HandlerHTTPEnviarEncuesta (String respuesta){
        var URL = "http://localhost:7072/formulario";
        byte[] postData = respuesta.getBytes(StandardCharsets.UTF_8);

        try {

            var myurl = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection)  myurl.openConnection();
            conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            try (var wr = new DataOutputStream(conn.getOutputStream())) {

                wr.write(postData);
            }

            System.out.println(conn.getResponseCode());
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
    }
    };
}
