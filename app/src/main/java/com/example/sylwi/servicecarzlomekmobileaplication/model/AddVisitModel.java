package com.example.sylwi.servicecarzlomekmobileaplication.model;

import java.util.Calendar;

public class AddVisitModel {
    private long carId;
    private String  visitDate;
    private boolean isOverview;
    private String accessToken;

    public AddVisitModel(long carId, String date, boolean isOverview, String accessToken) {
        this.carId = carId;
        this.visitDate = date;
        this.isOverview = isOverview;
        this.accessToken = accessToken;
    }
}
