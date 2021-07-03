package com.property.manager.data.service;

import com.property.manager.rest.dto.PropertyDTO;
import com.property.manager.rest.uri.GeoapifyUri;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

@Service
public class GeoService {
    private final OkHttpClient okHttpClient;

    @Value("${geoapify.api.key}")
    private String apiKey;

    @Value("${geoapify.retry.time}")
    private Long retryTime;

    public GeoService() {
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * Retrieve coordinates form Geoapify and sets coordinates of given propertyDto's, ge
     *
     * @param propertyDTOs
     * @throws IOException
     * @throws InterruptedException
     */
    public void setPropertiesCoordinates(ArrayList<PropertyDTO> propertyDTOs) throws IOException, InterruptedException {
        Response response = getPropertyInfoFromGeocodingBatch(propertyDTOs);

        //waiting for Geoapify to execute the job of batch call
        while (response.code() == HttpStatus.ACCEPTED.value()) {
            Thread.sleep(retryTime);
            response = getBatchJobById(new JSONObject(response.body().string()).getString("id"));
        }

        //getting response form batch call
        String responseBodyStr = response.body().string();

        //Constructing json object from response
        JSONObject jsonObject = new JSONObject(responseBodyStr);

        //ResultsArray which contains response for each propertyDTO
        JSONArray resultsArray = jsonObject
                .getJSONArray("results");


        for (int i = 0; i < propertyDTOs.size(); i++) {
            //response result for corresponding element
            JSONObject results = resultsArray.getJSONObject(i)
                    .getJSONObject("result");

            //IdOfElement is id key of result's element which is equal to propertyDTO's index
            int idOfElement = resultsArray.getJSONObject(i).getInt("id");

            Double lon = results.getJSONArray("features").getJSONObject(0)
                    .getJSONObject("properties")
                    .getDouble("lon");

            Double lat = results.getJSONArray("features").getJSONObject(0)
                    .getJSONObject("properties")
                    .getDouble("lat");

            propertyDTOs.get(idOfElement).setLon(lon);
            propertyDTOs.get(idOfElement).setLat(lat);

        }
    }

    /**
     * Gets response form batch call of GEOCODING api
     *
     * @param propertyDTOs
     * @return An HTTP response of batch call
     * @throws IOException
     */
    private Response getPropertyInfoFromGeocodingBatch(ArrayList<PropertyDTO> propertyDTOs) throws IOException {
        String url = HttpUrl.parse(GeoapifyUri.BATCFULL)
                .newBuilder().addQueryParameter("apiKey", apiKey).build().toString();
        Collection<JSONObject> inputJson = new LinkedList<>();
        for (int i = 0; i < propertyDTOs.size(); i++) {
            PropertyDTO propertyDTO = propertyDTOs.get(i);

            JSONObject paramsJson = new JSONObject().
                    put("housenumber", propertyDTO.getNumber().toString())
                    .put("street", propertyDTO.getStreet())
                    .put("postcode", propertyDTO.getPostalCode())
                    .put("city", propertyDTO.getCity())
                    .put("country", propertyDTO.getCountry())
                    .put("limit", 1);

            inputJson.add(
                    new JSONObject()
                            .put("id", i)
                            .put("params", paramsJson));
        }

        JSONObject bodyJson = new JSONObject().put("apiKey", apiKey)
                .put("api", GeoapifyUri.GEOCODING)
                .put("inputs", inputJson);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(bodyJson.toString(), JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * Gets batch job's response by id
     *
     * @param jobId
     * @return An HTTP response of batch call of given id
     * @throws IOException
     */
    private Response getBatchJobById(String jobId) throws IOException {

        String url = HttpUrl.parse(GeoapifyUri.BATCFULL)
                .newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .addQueryParameter("id", jobId).build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        return okHttpClient.newCall(request).execute();
    }
}