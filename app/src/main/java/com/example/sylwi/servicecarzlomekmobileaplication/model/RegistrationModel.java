package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 06.11.2018.
 */

public class RegistrationModel extends Client {
    private String password;
    private String confirmPassword;

    public RegistrationModel(String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword) {
        super(firstName, lastName, email, phoneNumber, cityName, streetName, buildNum, aptNum, zipCode);
        this.password = password;
        this.confirmPassword = confirmPassword;
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
