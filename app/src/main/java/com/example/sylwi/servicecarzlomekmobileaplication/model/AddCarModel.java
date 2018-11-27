package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 27.11.2018.
 */

public class AddCarModel extends TokenModel {

    private String vin;
    private String registrationNumber;
    private String model;
    private String productionYear;
    private String brandName;

    public AddCarModel(String token, String vin, String registrationNumber, String model, String productionYear, String brandName) {
        super(token);
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.productionYear = productionYear;
        this.brandName = brandName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
