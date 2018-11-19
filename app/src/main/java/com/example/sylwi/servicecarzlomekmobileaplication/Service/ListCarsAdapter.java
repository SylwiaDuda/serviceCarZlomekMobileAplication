package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylwi on 19.11.2018.
 */

public class ListCarsAdapter extends ArrayAdapter<Car> {


    private LayoutInflater mLayoutInflater;
    private int mViewResourceId;
    private List<Car>cars;


    public ListCarsAdapter(Context context, int textViewResourceId, List<Car> cars) {
        super(context, textViewResourceId, cars);
        this.cars=cars;
        mViewResourceId = textViewResourceId;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parents){
        convertView= mLayoutInflater.inflate(mViewResourceId,null);
        Car car = cars.get(position);

        if(car!=null){
            TextView brandAndModel = (TextView)convertView.findViewById(R.id.car_brand_and_model);
            TextView registrationNumber = (TextView)convertView.findViewById(R.id.registration_number);
            brandAndModel.setText(car.getBrandName()+" "+car.getModel());
            registrationNumber.setText(car.getRegistrationNumber());
        }
        return convertView;
    }
}
