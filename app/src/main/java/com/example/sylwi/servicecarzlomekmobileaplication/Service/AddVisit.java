package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.AddVisitActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.model.AddVisitModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

public class AddVisit extends AsyncTask<Void, Void, Void> {

    private AddVisitModel model;
    private AddVisitActivity activity;

    public AddVisit(AddVisitModel model, AddVisitActivity activity) {
        this.model = model;
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        REST login = new REST();
        Response response = login.requestWithMethodPOST("http://" + activity.getApplicationContext().
                getString(R.string.ip) + ":8080/warsztatZlomek/rest/visits/add", model);
        if(response!=null) {
            String token = response.getToken();
            if(token != null){
                activity.setAccessToken(token);
            }
        }
        return null;
    }
}
