package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 20.11.2018.
 */

public class Visit {
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

    public Visit() {
        car=new Car();
    }

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
    public String toString() {
        return "Visit{" +
                "id='" + id + '\'' +
                ", visitDate='" + visitDate + '\'' +
                ", car=" + car +
                ", visitStatus='" + visitStatus + '\'' +
                '}';
    }
}
