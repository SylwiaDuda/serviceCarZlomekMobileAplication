package com.example.sylwi.servicecarzlomekmobileaplication.rest;

import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Client;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Visit;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by sylwi on 12.11.2018.
 */
public class Response {

    private int responseStatus;
    private InputStream inputStream;
    private List<Visit> newVisitList;
    private ArrayList<String> carBrandsList;
    private String accessToken;
    private List<Car> listCar;
    private Client client;

    public Response() {
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Visit> getNewVisitList() {
        return newVisitList;
    }

    public void setNewVisitList(String inputStream){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(inputStream);
        JsonObject visitsObj = element.getAsJsonObject();
        JsonArray array = visitsObj.getAsJsonArray("visits");
        ArrayList<Visit> visitList = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < array.size(); i++) {
            visitList.add(gson.fromJson(array.get(i), Visit.class));
        }
        this.newVisitList = visitList;
    }

    public ArrayList<String> getCarBrandsList() {
        return carBrandsList;
    }

    public void setCarBrandsList(String inputStream) {
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(inputStream).getAsJsonObject();
        JsonArray carBrands = object.getAsJsonArray("brandNames");
        ArrayList<String> brands = new ArrayList<>();
        for (int i = 0; i < carBrands.size(); i++) {
            brands.add(carBrands.get(i).getAsString());
        }
        Collections.sort(brands);
        this.carBrandsList = brands;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String  inputStream) {
        JsonParser parser = new JsonParser();
        JsonObject jsonReader = parser.parse(inputStream).getAsJsonObject();
        String stringValue = null;
        if (jsonReader != null) {
            try {
                stringValue = jsonReader.get("accessToken").getAsString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.accessToken = stringValue;
    }

    public List<Car> getListCar() {
        return listCar;
    }

    public void setListCar(String inputStream) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(inputStream);
        JsonObject visitsObj = element.getAsJsonObject();
        JsonArray array = visitsObj.getAsJsonArray("cars");
        ArrayList<Car> carlist = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < array.size(); i++) {
            carlist.add(gson.fromJson(array.get(i), Car.class));
        }
        this.listCar = carlist;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(String inputStream) {
        Gson gson = new Gson();
        this.client = gson.fromJson(inputStream, Client.class);
    }
}