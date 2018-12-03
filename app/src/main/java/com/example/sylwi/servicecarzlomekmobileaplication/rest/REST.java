package com.example.sylwi.servicecarzlomekmobileaplication.rest;


import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by sylwi on 12.11.2018.
 */

public class REST {

    private Response response;
    public REST() {}

    private HttpURLConnection getConnection(String urlString)  {
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }
    public Response requestWithMethodPOST(String url, Object objectJSON) {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream =null;
        response=null;
        try {
            httpURLConnection = getConnection(url);
            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            Gson gson = new Gson();
            Log.d("String", gson.toJson(objectJSON));
            dataOutputStream.writeBytes(gson.toJson(objectJSON));
            dataOutputStream.flush();
            dataOutputStream.close();
            response = new Response();
            response.setResponseStatus(httpURLConnection.getResponseCode());
            inputStream= httpURLConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            if(url.contains("/authorization/getFutureVisits")){
                response.setNewVisitList(responseStrBuilder.toString());
            }else if(url.contains("/authorization/signIn")){
                response.setAccessToken(responseStrBuilder.toString());
            }else if(url.contains("/authorization/getFullClientData")){
                response.setClient(responseStrBuilder.toString());
            }else if(url.contains("/car/getAllClientsCars")){
                response.setListCar(responseStrBuilder.toString());
            }
            inputStream.close();
            return response;
        } catch (UnsupportedEncodingException e) {
            Log.d("catch","UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("catch","IOException");
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return response;
    }
    public Response requestWithMethodGET(String url) {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream =null;
        response=null;
        try {
            httpURLConnection = getConnection(url);
            httpURLConnection.setRequestMethod("GET");
            response = new Response();
            response.setResponseStatus(httpURLConnection.getResponseCode());
            inputStream= httpURLConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            if(url.contains("/car/getAllCarBrands")){
                response.setCarBrandsList(responseStrBuilder.toString());
            }
            return response;
        } catch (UnsupportedEncodingException e) {
            Log.d("catch","UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("catch","IOException");
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            }
        return response;
    }
}