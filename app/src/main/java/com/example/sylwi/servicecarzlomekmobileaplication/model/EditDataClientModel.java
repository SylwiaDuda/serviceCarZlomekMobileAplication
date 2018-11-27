package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 26.11.2018.
 */

public class EditDataClientModel extends Client {
    private String accessToken;
    private String password;
    private String confirmPassword;

    public EditDataClientModel(String accessToken, String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword) {
        super(firstName, lastName, email, phoneNumber, cityName, streetName, buildNum, aptNum, zipCode);
        this.accessToken =accessToken;
        this.password = password;
        this.confirmPassword =  confirmPassword;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
