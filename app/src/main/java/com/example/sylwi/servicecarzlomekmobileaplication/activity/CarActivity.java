package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.NetworkConnection;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.TextWatcherValidateForm;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.AddCarModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.EditCarData;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarActivity  extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private Response response;
    private Car car;
    private TextView brandTV;
    private TextView modelTV;
    private TextView registrationNumberTV;
    private TextView productionYearTV;
    private TextView vinNumberTV;
    private View mEditView;
    private View mDataView;
    private boolean serverIsActive=true;
    private Context mContext;
    private String ip;
    private GetBrandsTask mGetBrandsTask = null;
    private EditTask mEditTask = null;
    boolean cancel;
    private ArrayList<String> brandsList;
    private Spinner brandsSpinner;
    private String selectedBrand;
    private EditText modelET;
    private EditText registrationNumberET;
    private EditText productionYearET;
    private EditText vinNumber;
    private Button buttonCancel;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_car);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDataView = (View)findViewById(R.id.car_data);
        mDataView.setVisibility(View.VISIBLE);
        mEditView = (View)findViewById(R.id.car_edit);
        mEditView.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        car = bundle.getParcelable("CAR");
        brandTV = (TextView)findViewById(R.id.brand_form);
        modelTV = (TextView)findViewById(R.id.model_form);
        registrationNumberTV = (TextView)findViewById(R.id.registration_number_form);
        productionYearTV = (TextView)findViewById(R.id.production_year_form);
        vinNumberTV = (TextView)findViewById(R.id.vin_number_form);
        setCarData();
        mContext = getApplicationContext();
        ip = getString(R.string.ip);
        Button editCarButton = (Button)findViewById(R.id.edit_car_button);
        editCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditView.setVisibility(View.VISIBLE);
                mDataView.setVisibility(View.INVISIBLE);
                mGetBrandsTask = new GetBrandsTask();
                mGetBrandsTask.execute((Void)null);
                modelET = (EditText)findViewById(R.id.model_form2);
                registrationNumberET = (EditText)findViewById(R.id.registration_number_form2);
                productionYearET = (EditText)findViewById(R.id.production_year_form2);
                vinNumber = (EditText)findViewById(R.id.vin_number_form2);
                setCarDataForEdit();
                modelET.addTextChangedListener(new TextWatcherValidateForm(modelET,mContext));
                registrationNumberET.addTextChangedListener(new TextWatcherValidateForm(registrationNumberET,mContext));
                productionYearET.addTextChangedListener(new TextWatcherValidateForm(productionYearET,mContext));
                vinNumber.addTextChangedListener(new TextWatcherValidateForm(vinNumber,mContext));
            }
        });
        brandsSpinner = (Spinner)findViewById(R.id.spinner_band_form);
        brandsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBrand = brandsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCancel = (Button)findViewById(R.id.cancel_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditView.setVisibility(View.INVISIBLE);
                mDataView.setVisibility(View.VISIBLE);
                setCarData();
            }
        });
        saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptEditCar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_car);
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

      /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_car);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setCarData(){
        brandTV.setText(car.getBrandName());
        modelTV.setText(car.getModel());
        registrationNumberTV.setText(car.getRegistrationNumber());
        productionYearTV.setText(car.getProductionYear());
        vinNumberTV.setText(car.getVin());
    }
    private void setCarDataForEdit(){
        modelET.setText(car.getModel());
        registrationNumberET.setText(car.getRegistrationNumber());
        productionYearET.setText(car.getProductionYear());
        vinNumber.setText(car.getVin());
    }
    public class GetBrandsTask extends AsyncTask<Void, Void, Integer> {

        public GetBrandsTask() {
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST login = new REST();
            response = login.requestWithMethodGET("http://" + ip + ":8080/warsztatZlomek/rest/car/getAllCarBrands");
            if(!(response==null)) {
                serverIsActive=true;
                int status=response.getResponseStatus();
                brandsList = response.getCarBrandsList();
                return status;
            }else{
                serverIsActive=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetBrandsTask= null;
            switch (status) {
                case 200:
                    setBrandsInSpinner();
                    //setDataCient(client);
                    break;
                case 401:
                    //showProgress(false);
                    break;
                case -1:
                    if(!serverIsActive){
                        //showProgress(false);
                        // String toastText = "Server is unreachable!";
                        //Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
                        //toast.show();
                    }
                    break;
                default:
                    // showProgress(false);
                    break;
            }
        }
        @Override
        protected void onCancelled() {
            mGetBrandsTask = null;
            //showProgress(false);
        }
    }
    public void setBrandsInSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, brandsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandsSpinner.setAdapter(adapter);
        selectedBrand = brandsList.get(0);
    }
    public void attemptEditCar() {
        NetworkConnection networkConnection = new NetworkConnection(mContext);
        boolean networkIsReachable = networkConnection.checkIfNetworkIsReachable();
        if(networkIsReachable) {
            if (mEditTask != null) {
                return;
            }
            cancel = false;
            View focusView = null;
            validateEditCarForm();
            if (cancel) {
                Log.d("cannnnnnnnn","celll");
                //focusView.requestFocus();
            } else {
                validateEditCarForm();
                String model = modelET.getText().toString();
                String registrationNumber = registrationNumberET.getText().toString();
                String productionYear = productionYearET.getText().toString();
                String vinNumber = this.vinNumber.getText().toString();

                InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
                String accessToken = internalStorageDirMnager.getToken(mContext);

                mEditTask = new EditTask(accessToken, vinNumber, registrationNumber, model, productionYear, selectedBrand,car.getId());
                mEditTask.execute((Void) null);
            }

        }
    }
    //------------------------------------------------------------------------------------------------------Validation
    public void validateEditCarForm(){
        validate(modelET);
        validate(registrationNumberET);
        validate(productionYearET);
        validate(vinNumber);
    }
    public void validate(EditText editText){
        String text = editText.getText().toString();
        boolean textIsEmpty = TextUtils.isEmpty(text);
        if(textIsEmpty){
            editText.setError(getString(R.string.error_field_required));
            cancel = true;
        }else{
            switch (editText.getId()){
                case R.id.production_year_form2:
                    Pattern pattern = Pattern.compile("[0-9]{4}");
                    Matcher matcher = pattern.matcher(text);
                    boolean correctText = matcher.matches();
                    if(!correctText ){
                        editText.setError("Niepoprawny rok produkcji!");
                        cancel = true;
                    }else {
                        int year = Integer.parseInt(text);
                        Time now = new Time();
                        now.setToNow();
                        int currentYear = now.year;
                        if(year>currentYear){
                            editText.setError("Niepoprawny rok produkcji!");
                            cancel = true;
                        }
                    }
                    break;
                case R.id.registration_number_form2:
                    Pattern pattern2 = Pattern.compile("[A-Z]{1,3}\\s[0-9 A-Z]{4,5}");
                    Matcher matcher2 = pattern2.matcher(text);
                    boolean correctText2 = matcher2.matches();
                    if(!correctText2 ){
                        editText.setError("Niepoprawny numer rejestracyjny! Wprowadź dane w postaci np: XX XXXX");
                        cancel = true;
                    }
                    break;
                case R.id.vin_number_form2:
                    if(text.length()!=17){
                        editText.setError("Wprowadź 17 znaków");
                        cancel = true;
                    }
                    break;
                default:
            }
        }
    }
    //------------------------------------------------------------------------------------------------work in background
    public class EditTask extends AsyncTask<Void, Void, Integer> {

        private final String accessToken;
        private final String vin;
        private final String registrationNumber;
        private final String model;
        private final String productionYear;
        private final String brandName;
        private final String carId;

        public EditTask(String accessToken, String vin, String registrationNumber, String model, String productionYear, String brandName, String carId) {
            this.accessToken = accessToken;
            this.vin = vin;
            this.registrationNumber = registrationNumber;
            this.model = model;
            this.productionYear = productionYear;
            this.brandName = brandName;
            this.carId = carId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST addCar= new REST();
            response =addCar.requestWithMethodPOST("http://"+ip+":8080/warsztatZlomek/rest/car/editCar",new EditCarData(accessToken, vin, registrationNumber, model, productionYear, brandName,carId));
            if(!(response==null)) {
                serverIsActive=true;
                int status=response.getResponseStatus();
                Log.d("status Edit CAR: ", String.valueOf(status));
                return status;
            }else{
                serverIsActive=false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mEditTask = null;
            switch (status) {
                case 200:
                    showToast();
                    goToActivity(CarsActivity.class);
                    break;
                case 400:
                    //showProgress(false);
                    String toastTextERROR = "Błedne dane!";
                    Toast toastERROR = Toast.makeText(mContext, toastTextERROR, Toast.LENGTH_LONG);
                    toastERROR.show();
                    break;
                case -1:
                    if(!serverIsActive){
                        //showProgress(false);
                        String toastText = "Server is unreachable!";
                        Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                default:
                    break;
            }
        }
        @Override
        protected void onCancelled() {
            mEditTask = null;
            //showProgress(false);
        }
    }
    public void showToast(){
        String toastText = "Dane samochodu zostały zmienione!";
        Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
        toast.show();
    }

}
