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
            statusTV.setText(visit.getVisitStatus());
            Date dateObject = new Date();
            String dataString = visit.getVisitDate();
            Long dateLong = Long.valueOf(dataString);
            dateObject.setTime(dateLong);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(dateObject);

            String hour = Integer.toString(gregorianCalendar.get(gregorianCalendar.HOUR_OF_DAY));
            String minuts = Integer.toString(gregorianCalendar.get(gregorianCalendar.MINUTE));
            String day = Integer.toString(gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH));
            String month = Integer.toString(gregorianCalendar.get(gregorianCalendar.MONTH)+1);
            String year = Integer.toString(gregorianCalendar.get(gregorianCalendar.YEAR));
            String dayMontYear = hour+":"+minuts+" "+day +"-"+month+"-"+year;
            dateTV.setText(dayMontYear);

        }
        return convertView;
    }
}
