package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.AddVisit;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.ListCarsAdapter;
import com.example.sylwi.servicecarzlomekmobileaplication.dialogs.DatePicker;
import com.example.sylwi.servicecarzlomekmobileaplication.dialogs.TimePicker;
import com.example.sylwi.servicecarzlomekmobileaplication.model.AddVisitModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddVisitActivity extends AppCompatActivity {
    private String accessToken;
    private Date visitDate = null;
    private List<Car> carsList;
    private Context context;
    private Car car = null;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setVisitDate(Date date){
        visitDate = date;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void notifyDateProblem(){
        Toast.makeText(getApplicationContext(), R.string.visit_date_invalid, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);
        Intent intent = getIntent();
        this.accessToken = intent.getStringExtra("token");
        context = getApplicationContext();
        Button addVisitButton = (Button)findViewById(R.id.add_visit_button);
        addVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVisitModel model = collectData();
                if(model == null){
                    Log.d("tag", "Taki...");
                    return;
                }
                AddVisit addVisit = new AddVisit(model, AddVisitActivity.this);
                addVisit.execute();
            }
        });
        findViewById(R.id.activity_add_visit).getBackground().setAlpha(50);

        Button dateButton = (Button) findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePicker(AddVisitActivity.this);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        Button timeButton = (Button) findViewById(R.id.time_button);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timeFragment = new TimePicker(AddVisitActivity.this);
                timeFragment.show(getFragmentManager(), "timePicker");
            }
        });

        Spinner spinner = findViewById(R.id.cars);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                car = carsList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                car = null;
            }
        });
        GetCarsTask task = new GetCarsTask(this.accessToken);
        task.execute();
    }
    public AddVisitModel collectData(){
        Calendar visitDate = Calendar.getInstance();
        visitDate.setTime(this.visitDate);
        boolean checked = ((CheckBox)findViewById(R.id.is_overview)).isChecked();
        return (validateDate(visitDate) && validateTime(visitDate.get(Calendar.HOUR_OF_DAY)))?
                new AddVisitModel(Long.parseLong(car.getId()), visitDate, checked, this.accessToken):null;
    }

    public boolean validateTime(int hour){
        return hour>=8 && hour<16;
    }

    public boolean validateDate(Calendar calendar){
        GregorianCalendar min = new GregorianCalendar();
        GregorianCalendar max = new GregorianCalendar();
        max.add(GregorianCalendar.DAY_OF_YEAR, 21);
        Log.d("Date", Boolean.toString(calendar.before(max) && calendar.after(min)));
        return calendar.before(max) && calendar.after(min);
    }
    public class GetCarsTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;

        GetCarsTask(String token) {
            mToken = token;
        }
        Response response;
        @Override
        protected Integer doInBackground(Void... params) {
            REST login = new REST();
            Log.d("ip", getString(R.string.ip));
            response = login.requestWithMethodPOST("http://" + getString(R.string.ip) + ":8080/warsztatZlomek/rest/car/getAllClientsCars",new TokenModel(mToken));
            if(response!=null) {
                int status=response.getResponseStatus();
                Log.d("status", Integer.toString(status));
                return status;
            }else{
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            switch (status) {
                case 200:
                    carsList = response.getCarList();
                    Spinner spinner = findViewById(R.id.cars);
                    List<String> cars = new ArrayList<>();
                    for (Car car: carsList){
                        cars.add(car.getBrandName()+" "+car.getModel()+" "+car.getRegistrationNumber());
                    }
                    car = carsList.get(0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, cars);
                    spinner.setAdapter(adapter);
                    break;
                case 401:
                    //showProgress(false);
                    break;
            }
        }
        @Override
        protected void onCancelled() {
        }
    }
}

