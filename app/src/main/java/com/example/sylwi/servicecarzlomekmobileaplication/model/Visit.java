package com.example.sylwi.servicecarzlomekmobileaplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sylwi on 20.11.2018.
 */

public class Visit implements Parcelable {
    private String id;
    private String visitDate;
    private Car car;
    private String visitStatus;

    public Visit(String id, String visitDate, Car car, String visitStatus) {
        this.id = id;
        this.visitDate = visitDate;
        this.car = car;
        this.visitStatus = visitStatus;
    }

    protected Visit(Parcel in) {
        id = in.readString();
        visitDate = in.readString();
        car = in.readParcelable(Car.class.getClassLoader());
        visitStatus = in.readString();
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getVisitStatus() {
        return visitStatus;
    }
    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(visitDate);
        dest.writeParcelable(car, flags);
        dest.writeString(visitStatus);
    }
}
