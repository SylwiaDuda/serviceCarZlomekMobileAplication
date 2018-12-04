package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.AddVisitActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.MainActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.VisitsActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.model.AddVisitModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

public class AddVisit extends AsyncTask<Void, Void, Integer> {

    private AddVisitModel model;
    private AddVisitActivity activity;

    public AddVisit(AddVisitModel model, AddVisitActivity activity) {
        this.model = model;
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        REST addVisit = new REST();
        Response response = addVisit.requestWithMethodPOST("http://" + activity.getApplicationContext().
                getString(R.string.ip) + ":8080/warsztatZlomek/rest/visits/add", model);
        if(response!=null) {
            return response.getResponseStatus();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer responseStatus) {
        if(responseStatus == 200){
            Toast.makeText(activity.getApplicationContext(), "Dodawanie wizyty powiodło się",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity.getApplicationContext(), VisitsActivity.class);
            activity.startActivity(intent);
        }
    }
}
