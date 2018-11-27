package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 26.11.2018.
 */

public class ChangeCientPasswordModel extends Client {
    private String password;
    private String confirmPassword;
    public ChangeCientPasswordModel(Client client, String password, String confirmPassword) {
        super(client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhoneNumber(),
                client.getCityName(), client.getStreetName(), client.getBuildNum(), client.getAptNum(),
                client.getZipCode());
        this.password = password;
        this.confirmPassword =  confirmPassword;
    }

}
