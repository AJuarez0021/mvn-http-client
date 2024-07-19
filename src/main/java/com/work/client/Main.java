package com.work.client;

import com.work.client.service.ClientHttpImpl;
import com.work.client.service.ClientHttp;
import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author linux
 */
public class Main {

    private static final Logger LOG = Logger.getLogger("Main");

    public static void main(String[] args) {
        try {
            ClientHttp client = ClientHttpImpl.getInstance();
            HttpClientRequestDto request = new HttpClientRequestDto();
            request.setUrl("https://jsonmock.hackerrank.com/api/medical_records?userId=1&page=1");
            HttpClientResponseDto response = client.sendGet(request);
            if (client.isOk(response.getStatusCode())) {
                String json = response.getResponse();
                LOG.log(Level.INFO, "Response: {0}", json);
                LOG.log(Level.INFO, "\n"); 

                JsonReader jsonReader = Json.createReader(new StringReader(json));
                JsonObject jobj = jsonReader.readObject();

                int totalPages = jobj.getInt("total_pages");

                JsonArray array = jobj.getJsonArray("data");

                LOG.log(Level.INFO, "TotalPages: {0}", totalPages);

                for (int i = 0; i < array.size(); i++) {
                    JsonObject object = array.getJsonObject(i);
                    
                    LOG.log(Level.INFO, "Username: {0}", object.getString("userName"));
                                       
                    double bodyTemperature = object.getJsonObject("vitals").getJsonNumber("bodyTemperature").doubleValue();
                    LOG.log(Level.INFO, "Vitals-bodyTemperature: {0}", bodyTemperature);

                    String diagnosisName = object.getJsonObject("diagnosis").getString("name");
                    LOG.log(Level.INFO, "Diagnosis-name: {0}", diagnosisName);

                    String doctorName = object.getJsonObject("doctor").getString("name");
                    LOG.log(Level.INFO, "Doctor-name: {0}", doctorName);
                    
                    LOG.log(Level.INFO, "\n");                    
                }

            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }
}
