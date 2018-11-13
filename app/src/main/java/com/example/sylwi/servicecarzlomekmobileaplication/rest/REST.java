package com.example.sylwi.servicecarzlomekmobileaplication.rest;


import android.util.Log;

import com.example.sylwi.servicecarzlomekmobileaplication.model.CheckEmailModel;
import com.google.gson.Gson;
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return httpURLConnection;
        }
    }
    public Response request(String url, Object objectJSON) {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        response=null;
        try {

            httpURLConnection = getConnection(url);
            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            Gson gson = new Gson();
            dataOutputStream.writeBytes(gson.toJson(objectJSON));
            dataOutputStream.flush();
            dataOutputStream.close();
            response = new Response();
            response.setResponseStatus(httpURLConnection.getResponseCode());
            response.setResponseBody(httpURLConnection.getInputStream());
        } catch (UnsupportedEncodingException e) {
            Log.d("catch","UnsupportedEncodingException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("catch","IOException");
            //e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
            return response;
        }
    }
}