package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 17.10.2018.
 */

public class SignInModel {
    private String email;
    private String password;

    public SignInModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
