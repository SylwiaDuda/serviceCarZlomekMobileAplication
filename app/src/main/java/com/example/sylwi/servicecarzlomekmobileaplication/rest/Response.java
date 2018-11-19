package com.example.sylwi.servicecarzlomekmobileaplication.rest;

import android.util.Log;

import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
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
                Log.d("peeeeeeek",jsonReader.peek().toString());
                jsonReader.beginObject(); // Start processing the JSON object
                while (jsonReader.hasNext()) { // Loop through all keys
                    String key = jsonReader.nextName(); // Fetch the next key
                    if (key.equals("accessToken")) { // Check if desired key
                        // Fetch the value as a String
                        stringValue= jsonReader.nextString();

                        // Do something with the value
                        // ...

                        break; // Break out of the loop
                    } else {
                        jsonReader.skipValue(); // Skip values of other keys
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
                            carlist.add(car);
                            carlist.add(car);
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


}
