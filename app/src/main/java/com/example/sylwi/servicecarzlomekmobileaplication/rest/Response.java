package com.example.sylwi.servicecarzlomekmobileaplication.rest;

import android.util.Log;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Client;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Visit;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sylwi on 12.11.2018.
            */
public class Response {

    private int responseStatus;
    private InputStream inputStream;

    public Response(int responseStatus, InputStream inputStream) {
        this.responseStatus = responseStatus;
        this.inputStream = inputStream;
    }

    public Response() {
        this.responseStatus=-1;
        this.inputStream=null;
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

    public JsonReader getJsonReader(InputStream inputStream) {
        InputStreamReader responseBodyReader = null;
        JsonReader jsonReader = null;
        try{
            responseBodyReader = new InputStreamReader(inputStream, "UTF-8");
            jsonReader = new JsonReader(responseBodyReader);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return  jsonReader;
        }
    }
    public String getToken(){
        JsonReader jsonReader =getJsonReader(inputStream);
        String stringValue = null;
        if(jsonReader!=null){
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("accessToken")) {
                        stringValue= jsonReader.nextString();
                        break;
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringValue;
    }
    public List<Car> getCarList(){
        JsonReader jsonReader= getJsonReader(inputStream);
        List<Car> carlist = null;
        Car car = null;
        if(jsonReader!=null){
            try {
                carlist = new ArrayList<Car>();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("cars")) {
                        jsonReader.beginArray();
                        String innerName=null;
                        while (jsonReader.hasNext()){
                            jsonReader.beginObject();
                            car = new Car();
                            while (jsonReader.hasNext()){
                                innerName = jsonReader.nextName();
                                if(innerName.equals("vin")){
                                    car.setVin(jsonReader.nextString());
                                }else if (innerName.equals("registrationNumber")) {
                                    car.setRegistrationNumber(jsonReader.nextString());
                                } else if (innerName.equals("model")) {
                                    car.setModel(jsonReader.nextString());
                                } else if (innerName.equals("productionYear")) {
                                    car.setProductionYear(jsonReader.nextString());
                                } else if (innerName.equals("brandName")) {
                                    car.setBrandName(jsonReader.nextString());
                                }else if (innerName.equals("id")) {
                                    car.setId(jsonReader.nextString());
                                }else{
                                    jsonReader.skipValue();
                                }
                            }
                            carlist.add(car);
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    }
                    else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return carlist;
    }
    public Client getDataClient(){
        JsonReader jsonReader= getJsonReader(inputStream);
        Client client = null;
        if(jsonReader!=null){
            client=new Client();
            //tu
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    try {
                        String key = jsonReader.nextName();
                        if (key.equals("firstName")) {
                            client.setFirstName(jsonReader.nextString());
                        } else if (key.equals("lastName")) {
                            client.setLastName(jsonReader.nextString());
                        } else if (key.equals("email")) {
                            client.setEmail(jsonReader.nextString());
                        } else if (key.equals("phoneNumber")) {
                            client.setPhoneNumber(jsonReader.nextString());
                        } else if (key.equals("cityName")) {
                            client.setCityName(jsonReader.nextString());
                        } else if (key.equals("streetName")) {
                            client.setStreetName(jsonReader.nextString());
                        } else if (key.equals("buildNum")) {
                            client.setBuildNum(jsonReader.nextString());
                        } else if (key.equals("aptNum")) {
                            client.setAptNum(jsonReader.nextString());
                        } else if (key.equals("zipCode")) {
                            client.setZipCode(jsonReader.nextString());
                        } else {
                            jsonReader.skipValue();
                        }
                    }catch (Exception e ){
                        jsonReader.skipValue();
                    }

                }
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
    public List<Visit> getNewVisit(){
        JsonReader jsonReader= getJsonReader(inputStream);
        List visitList = null;
        Visit visit=null;
        Car car = null;
        if(jsonReader!=null){
            try {
                visitList = new ArrayList<Car>();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("visits")) {
                        jsonReader.beginArray();
                        String innerName=null;
                        while (jsonReader.hasNext()){
                            jsonReader.beginObject();
                            visit = new Visit();
                            while (jsonReader.hasNext()) {
                                innerName = jsonReader.nextName();
                                if (innerName.equals("id")) {
                                    visit.setId(jsonReader.nextString());
                                }else if(innerName.equals("visitDate")){
                                    visit.setVisitDate(jsonReader.nextString());
                                }else if (innerName.equals("car")) {
                                    car=new Car();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()){
                                        String innerInnerName = jsonReader.nextName();
                                        if(innerInnerName.equals("vin")){
                                            car.setVin(jsonReader.nextString());
                                        }else if (innerInnerName.equals("registrationNumber")) {
                                            car.setRegistrationNumber(jsonReader.nextString());
                                        } else if (innerInnerName.equals("model")) {
                                            car.setModel(jsonReader.nextString());
                                        } else if (innerInnerName.equals("productionYear")) {
                                            car.setProductionYear(jsonReader.nextString());
                                        } else if (innerInnerName.equals("brandName")) {
                                            car.setBrandName(jsonReader.nextString());
                                        }else if (innerInnerName.equals("id")) {
                                            car.setId(jsonReader.nextString());
                                        }else{
                                            jsonReader.skipValue();
                                        }
                                    }
                                    visit.setCar(car);
                                    jsonReader.endObject();
                                }else{
                                    jsonReader.skipValue();
                                }
                            }
                            visitList.add(visit);
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    }
                    else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return visitList;
    }
    public ArrayList<String> getCarBrands(){
        JsonReader jsonReader= getJsonReader(inputStream);
        ArrayList<String> brands = null;
        if(jsonReader!=null){
            try {
                brands = new ArrayList<>();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("brandNames")) {
                        jsonReader.beginArray();
                        String innerName=null;
                        while (jsonReader.hasNext()) {
                            innerName = jsonReader.nextString();
                            brands.add(innerName);
                        }
                        jsonReader.endArray();
                    }
                    else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return brands;
    }
}