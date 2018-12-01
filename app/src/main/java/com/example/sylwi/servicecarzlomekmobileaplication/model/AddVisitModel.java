package com.example.sylwi.servicecarzlomekmobileaplication.model;

import java.util.Calendar;

public class AddVisitModel {
    private long carId;
    private String  visitDate;
    private boolean isOverview;
    private String accessToken;

    public AddVisitModel(long carId, Calendar date, boolean isOverview, String accessToken) {
        this.carId = carId;
        String minute = (date.get(Calendar.MINUTE)<10)?"0"+date.get(Calendar.MINUTE):
                Integer.toString(date.get(Calendar.MINUTE));
        String hour = (date.get(Calendar.HOUR_OF_DAY)<10)?"0"+date.get(Calendar.HOUR_OF_DAY):
                Integer.toString(date.get(Calendar.HOUR_OF_DAY));
        String day = (date.get(Calendar.DAY_OF_MONTH)<10)?"0"+date.get(Calendar.DAY_OF_MONTH):
                Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        String month = (date.get(Calendar.MONTH)+1<10)?"0"+date.get(Calendar.MONTH):
                Integer.toString(date.get(Calendar.MONTH)+1);
        this.visitDate = day + "-" + month+ "-" + date.get(Calendar.YEAR)
        + " " + hour + ":" + minute;
        this.isOverview = isOverview;
        this.accessToken = accessToken;
    }
}
