package com.example.sylwi.servicecarzlomekmobileaplication.rest;


import android.util.Log;
import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            dataOutputStream.writeBytes(gson.toJson(objectJSON));
            dataOutputStream.flush();
            dataOutputStream.close();
            response = new Response();
            response.setResponseStatus(httpURLConnection.getResponseCode());
            inputStream= httpURLConnection.getInputStream();
            response.setInputStream(inputStream);
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
            //dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            //Gson gson = new Gson();
            //dataOutputStream.writeBytes(gson.toJson(objectJSON));
            ///dataOutputStream.flush();
           // dataOutputStream.close();
            response = new Response();
            response.setResponseStatus(httpURLConnection.getResponseCode());
            inputStream= httpURLConnection.getInputStream();
            response.setInputStream(inputStream);
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