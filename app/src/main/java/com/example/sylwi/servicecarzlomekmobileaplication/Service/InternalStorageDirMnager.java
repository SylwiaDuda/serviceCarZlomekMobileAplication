package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmConstraints;
import java.security.AlgorithmParameterGenerator;

/**
 * Created by sylwi on 24.11.2018.
 */

public class InternalStorageDirMnager {

    public InternalStorageDirMnager() {

    }

    public String getToken(Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationToken.csv");
        String token = getData(aplicationToken);
        return token;
    }

    public void setToken(String token, Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationToken.csv");
        saveFile(token, aplicationToken);
    }
    public void deleteToken(Context context){
        File aplicationToken = new File(context.getFilesDir(), "aplicationToken.csv");
        saveFile("", aplicationToken);
    }
    public String getEmail(Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationEmail.csv");
        String email = getData(aplicationToken);
        return email;
    }

    public void setEmail(String email, Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationEmail.csv");
        saveFile(email, aplicationToken);
    }
    public void deleteEmail(Context context){
        File aplicationToken = new File(context.getFilesDir(), "aplicationEmail.csv");
        saveFile("", aplicationToken);
    }
    public String getKey(Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationKey.csv");
        String key = getData(aplicationToken);
        return key;
    }

    public void setKey(String key, Context context) {
        File aplicationToken = new File(context.getFilesDir(), "aplicationKey.csv");
        saveFile(key, aplicationToken);
    }
    public void deleteKey(Context context){
        File aplicationToken = new File(context.getFilesDir(), "aplicationKey.csv");
        saveFile("", aplicationToken);
    }
    private void saveFile(String text, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getData( File file) {
        FileInputStream fileInputStream = null;

        String data = "";
        try {
            fileInputStream = new FileInputStream(file);
            int read;
            while ((read = fileInputStream.read()) != -1) {
                data += (char) read;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}