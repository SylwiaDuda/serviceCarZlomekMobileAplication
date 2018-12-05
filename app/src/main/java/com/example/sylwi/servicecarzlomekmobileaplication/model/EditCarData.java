package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 05.12.2018.
 */

public class EditCarData extends AddCarModel {
    String carId;

    public EditCarData(String token, String vin, String registrationNumber, String model, String productionYear, String brandName, String carId) {
        super(token, vin, registrationNumber, model, productionYear, brandName);
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
