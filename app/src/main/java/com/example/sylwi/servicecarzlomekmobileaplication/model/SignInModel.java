package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 17.10.2018.
 */

public class SignInModel {
    private String username;
    private String password;

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
