package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 29.11.2018.
 */

public class DeleteCarModel extends TokenModel{
    private String carId;

    public DeleteCarModel(String token, String carId) {
        super(token);
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
