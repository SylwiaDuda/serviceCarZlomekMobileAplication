package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;

import java.util.List;

/**
 * Created by sylwi on 20.11.2018.
 */

public class ListVisitsAdapter {
    private LayoutInflater mLayoutInflater;
    private int mViewResourceId;
    /*private List<Visit> visit;


    public ListVisitsAdapter(Context context, int textViewResourceId, List<Visit> visits) {
        super(context, textViewResourceId, visits);
        this.cars=cars;
        mViewResourceId = textViewResourceId;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parents){
        convertView= mLayoutInflater.inflate(mViewResourceId,null);
        Visit visit = visits.get(position);

        if(visit!=null){
            TextView brandAndModel = (TextView)convertView.findViewById(R.id.car_brand_and_model);
            TextView registrationNumber = (TextView)convertView.findViewById(R.id.registration_number);
            TextViev for data
            brandAndModel.setText(visit.getBrandName()+" "+visit.getModel());
            registrationNumber.setText(visit.getRegistrationNumber());
        }
        return convertView;
    }*/
}
