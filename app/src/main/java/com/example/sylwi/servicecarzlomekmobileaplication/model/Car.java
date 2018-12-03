package com.example.sylwi.servicecarzlomekmobileaplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sylwi on 19.11.2018.
 */

public class Car implements Parcelable{

    private String vin;
    private String registrationNumber;
    private String model;
    private String productionYear;
    private String brandName;
    private String id;

    public Car(String vin, String registrationNumber, String model, String productionYear, String brandName) {
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.productionYear = productionYear;
        this.brandName = brandName;
    }

    public Car() {
    }

    protected Car(Parcel in) {
        vin = in.readString();
        registrationNumber = in.readString();
        model = in.readString();
        productionYear = in.readString();
        brandName = in.readString();
        id = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vin);
        dest.writeString(registrationNumber);
        dest.writeString(model);
        dest.writeString(productionYear);
        dest.writeString(brandName);
        dest.writeString(id);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vin='" + vin + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", model='" + model + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", brandName='" + brandName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
