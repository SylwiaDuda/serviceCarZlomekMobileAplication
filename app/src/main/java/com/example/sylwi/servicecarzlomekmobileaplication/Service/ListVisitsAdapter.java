package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Visit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by sylwi on 20.11.2018.
 */

public class ListVisitsAdapter extends ArrayAdapter<Visit> {
    private LayoutInflater mLayoutInflater;
    private int mViewResourceId;
    private List<Visit> visits;


    public ListVisitsAdapter(Context context, int textViewResourceId, List<Visit> visits) {
        super(context, textViewResourceId, visits);
        this.visits=visits;
        mViewResourceId = textViewResourceId;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parents){
        convertView= mLayoutInflater.inflate(mViewResourceId,null);
        Visit visit = visits.get(position);

        if(visit!=null){
            TextView brandAndModelTV = (TextView)convertView.findViewById(R.id.car_brand_and_model);
            TextView registrationNumberTV = (TextView)convertView.findViewById(R.id.registration_number);
            TextView dateTV = (TextView)convertView.findViewById(R.id.date_visit);
            TextView statusTV = (TextView)convertView.findViewById(R.id.status_visit);
            brandAndModelTV.setText(visit.getCar().getBrandName()+" "+visit.getCar().getModel());
            registrationNumberTV.setText(visit.getCar().getRegistrationNumber());
            statusTV.setText(editStatus(visit.getVisitStatus()));
            Date dateObject = new Date();
            String dataString = visit.getVisitDate();
            Long dateLong = Long.valueOf(dataString);
            dateObject.setTime(dateLong);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(dateObject);

            dateTV.setText(parseDate(gregorianCalendar));

        }
        return convertView;
    }

    private String parseDate(GregorianCalendar gregorianCalendar){
        int minute = gregorianCalendar.get(Calendar.MINUTE);
        int hourOfDay = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
        String currentMinute = (minute < 10) ? "0" + Integer.toString(minute) :
                Integer.toString(minute);
        String hour = (hourOfDay < 10) ? "0" + Integer.toString(hourOfDay) :
                Integer.toString(hourOfDay);
        String time = hour+":"+currentMinute;
        String day = (gregorianCalendar.get(Calendar.DAY_OF_MONTH)<10)?"0"+gregorianCalendar.get(Calendar.DAY_OF_MONTH):
                Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String month = (gregorianCalendar.get(Calendar.MONTH)+1<10)?"0"+gregorianCalendar.get(Calendar.MONTH):
                Integer.toString(gregorianCalendar.get(Calendar.MONTH)+1);
        return day + "-" + month+ "-" + gregorianCalendar.get(Calendar.YEAR)
                + " " + time;
    }

    private String editStatus(String currentStatus){
        switch (currentStatus) {
            case "NEW": {return "Nowa";}
            case "ACCEPTED": {return "Zaakceptowano";}
            case "IN_PROGRESS": {return "W toku";}
            case "FOR_PICKUP": {return "Do odbioru";}
            case "FINISHED": {return "Zakończona";}
        }
        return "";
    }
}
