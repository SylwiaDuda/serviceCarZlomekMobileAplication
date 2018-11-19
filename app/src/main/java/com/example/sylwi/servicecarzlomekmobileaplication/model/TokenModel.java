package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 18.11.2018.
 */

public class TokenModel {
    private String accessToken;
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TokenModel(String token) {
        this.accessToken = token;
    }


}
