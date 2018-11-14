package com.example.sylwi.servicecarzlomekmobileaplication.rest;

import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by sylwi on 12.11.2018.
 */

public class Response {

    private int responseStatus;
    private InputStream responseBody;

    public Response() {
        this.responseStatus=-1;
        this.responseBody=null;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public InputStream getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(InputStream responseBody) {
        this.responseBody = responseBody;
    }

    public JsonReader getJsonReader() {
        InputStreamReader responseBodyReader = null;
        JsonReader jsonReader =null;
        try{
            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            jsonReader = new JsonReader(responseBodyReader);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return  jsonReader;
        }
    }
}
