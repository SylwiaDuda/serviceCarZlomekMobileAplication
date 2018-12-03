package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.AddVisit;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
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

public class AddVisitActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener{
    private String accessToken;
    private Context mContext;
    private String ip;
    private boolean serverIsActive = true;
    private GetCarsTask mGetCarsTask = null;
    private Response response = null;
    private Date visitDate = null;
    private List<Car> carsList;
    private String hour = "";

    public void setHour(String hour) {
        this.hour = hour;
    }

    private Car car = null;

    public void setVisitDate(Date date){
        visitDate = date;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void notifyDateProblem(){
        Toast.makeText(getApplicationContext(), R.string.visit_date_invalid, Toast.LENGTH_LONG).show();
    }

    public void notifyTimeProblem(){
        Toast.makeText(getApplicationContext(), R.string.visit_time_invalid, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit_layout);
        findViewById(R.id.activity_add_visit).getBackground().setAlpha(50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_visit);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = getApplicationContext();
        ip=getString(R.string.ip);
        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        accessToken = internalStorageDirMnager.getToken(mContext);
        mGetCarsTask = new GetCarsTask(accessToken);
        mGetCarsTask.execute((Void) null);



        Button addVisitButton = (Button)findViewById(R.id.add_visit_button);
        addVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVisitModel model = collectData();
                if(model == null){
                    return;
                }
                AddVisit addVisit = new AddVisit(model, AddVisitActivity.this);
                addVisit.execute();
            }
        });


        Button dateButton = (Button) findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVisitActivity.this.visitDate = null;
                DialogFragment newFragment = new DatePicker(AddVisitActivity.this);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        Button timeButton = (Button) findViewById(R.id.time_button);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVisitActivity.this.hour = "";
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
                if(carsList.size()>0){
                    car = carsList.get(0);
                }else {
                    car = null;
                }
            }
        });
    }

    public String parseDateToString(){
        String day = "",month = "";
        Calendar date = null;
        if(this.visitDate != null) {
            date = Calendar.getInstance();
            date.setTime(this.visitDate);
            day = (date.get(Calendar.DAY_OF_MONTH)<10)?"0"+date.get(Calendar.DAY_OF_MONTH):
                    Integer.toString(date.get(Calendar.DAY_OF_MONTH));
            month = (date.get(Calendar.MONTH)+1<10)?"0"+date.get(Calendar.MONTH):
                    Integer.toString(date.get(Calendar.MONTH)+1);
        }
        return day + "-" + month+ "-" + ((date != null)?date.get(Calendar.YEAR): "")
                + " " + ((hour!=null && !hour.equals(""))? hour:"00:00");
    }

    public void updateDateView(){
        TextView currentDate = (TextView)findViewById(R.id.current_date);
        currentDate.setText(parseDateToString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_visit);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_visit);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public AddVisitModel collectData(){
        if (visitDate == null) {
            this.notifyDateProblem();
            return null;
        }
        Log.d("data", this.getVisitDate().toString());
        Calendar visitDate = Calendar.getInstance();
        visitDate.setTime(this.visitDate);
        boolean checked = ((CheckBox)findViewById(R.id.is_overview)).isChecked();
        boolean dataCorrect = true;
        if(!validateDate(visitDate)){
            this.notifyDateProblem();
            dataCorrect = false;
        }
        if("".equals(this.hour)){
            this.notifyTimeProblem();
            dataCorrect = false;
        }
        return (dataCorrect)?
                new AddVisitModel(Long.parseLong(car.getId()), this.parseDateToString(), checked, this.accessToken):null;
    }

    public boolean validateTime(int hour){
        return hour>=8 && hour<16;
    }

    public boolean validateDate(Calendar calendar){
        GregorianCalendar min = new GregorianCalendar();
        GregorianCalendar max = new GregorianCalendar();
        max.add(GregorianCalendar.DAY_OF_YEAR, 21);
        return calendar.before(max) && calendar.after(min);
    }
    public class GetCarsTask extends AsyncTask<Void, Void, Integer> {

        private final String accessToken;

        GetCarsTask(String token) {
            accessToken = token;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST getCars = new REST();
            response = getCars.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/car/getAllClientsCars",new TokenModel(accessToken));
            if(response!=null) {
                serverIsActive=true;
                int status=response.getResponseStatus();
                Log.d("status", Integer.toString(status));
                carsList = response.getListCar();
                return status;
            }else{
                serverIsActive=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetCarsTask= null;
            switch (status) {
                case 200:
                    Spinner spinner = findViewById(R.id.cars);
                    List<String> cars = new ArrayList<>();
                    for (Car car: carsList){
                        cars.add(car.getBrandName()+" "+car.getModel()+" "+car.getRegistrationNumber());
                    }
                    car = carsList.get(0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, cars);
                    spinner.setAdapter(adapter);
                    break;
                case 401:
                    //showProgress(false);
                    break;
                default:
                    break;
            }
        }
        @Override
        protected void onCancelled() {
        }
    }
}

