package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Sampler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.NetworkConnection;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.TextWatcherValidateForm;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.AddCarModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.sort;

public class AddCarActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private String ip;
    boolean cancel;
    private View mAddCarFormView;
    private View mProgressView;
    private GetBrandsTask mGetBrandsTask = null;
    private AddCarTask mAddCarTask = null;
    private boolean serverIsActive = true;
    private static Response response;
    private ArrayList<String> brandsList;
    private Spinner brandsSpinner;
    private String selectedBrand;
    private EditText modelET;
    private EditText registrationNumberET;
    private EditText productionYearET;
    private EditText vinNumber;
    private Button buttonCancel;
    private Button buttonAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_car);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mContext = getApplicationContext();
        ip = getString(R.string.ip);
        mGetBrandsTask = new GetBrandsTask();
        mGetBrandsTask.execute((Void)null);

        mAddCarFormView = (View)findViewById(R.id.add_car_form);
        mProgressView = (View)findViewById(R.id.add_car_progress);
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

        modelET = (EditText)findViewById(R.id.model_form);
        modelET.addTextChangedListener(new TextWatcherValidateForm(modelET,mContext));
        registrationNumberET = (EditText)findViewById(R.id.registration_number_form);
        registrationNumberET.addTextChangedListener(new TextWatcherValidateForm(registrationNumberET,mContext));
        productionYearET = (EditText)findViewById(R.id.production_year_form);
        productionYearET.addTextChangedListener(new TextWatcherValidateForm(productionYearET,mContext));
        vinNumber = (EditText)findViewById(R.id.vin_number_form);
        vinNumber.addTextChangedListener(new TextWatcherValidateForm(vinNumber,mContext));

        buttonCancel = (Button)findViewById(R.id.cancel_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(CarsActivity.class);
            }
        });
        buttonAddCar = (Button)findViewById(R.id.add_car_button);
        buttonAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddCar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_car);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_add_car);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    brandsList = response.getCarBrands();
                    Collections.sort(brandsList);
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
    //------------------------------------------------------------------------------------------------------progressBar
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddCarFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddCarFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddCarFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAddCarFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    //------------------------------------------------------------------------------------------------------tryCreareAccount
    public void attemptAddCar() {
        NetworkConnection networkConnection = new NetworkConnection(mContext);
        boolean networkIsReachable = networkConnection.checkIfNetworkIsReachable();
        if(networkIsReachable) {
            if (mAddCarTask != null) {
                return;
            }
            cancel = false;
            View focusView = null;
            validateAddCarForm();
            if (cancel) {
                //focusView.requestFocus();
            } else {
                String model = modelET.getText().toString();
                String registrationNumber = registrationNumberET.getText().toString();
                String productionYear = productionYearET.getText().toString();
                String vinNumber = this.vinNumber.getText().toString();

                showProgress(true);
                InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
                String accessToken = internalStorageDirMnager.getToken(mContext);

                mAddCarTask = new AddCarTask(accessToken, vinNumber, registrationNumber, model, productionYear, selectedBrand);
                mAddCarTask.execute((Void) null);
            }

        }
    }
    //------------------------------------------------------------------------------------------------------Validation
    public void validateAddCarForm(){
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
                case R.id.production_year_form:
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
                case R.id.registration_number_form:
                    Pattern pattern2 = Pattern.compile("[A-Z]{1,3}\\s[0-9 A-Z]{4,5}");
                    Matcher matcher2 = pattern2.matcher(text);
                    boolean correctText2 = matcher2.matches();
                    if(!correctText2 ){
                        editText.setError("Niepoprawny numer rejestracyjny! Wprowadź dane w postaci np: XX XXXX");
                        cancel = true;
                    }
                    break;
                case R.id.vin_number_form:
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
    public class AddCarTask extends AsyncTask<Void, Void, Integer> {

        private final String accessToken;
        private final String vin;
        private final String registrationNumber;
        private final String model;
        private final String productionYear;
        private final String brandName;

        public AddCarTask(String accessToken, String vin, String registrationNumber, String model, String productionYear, String brandName) {
            this.accessToken = accessToken;
            this.vin = vin;
            this.registrationNumber = registrationNumber;
            this.model = model;
            this.productionYear = productionYear;
            this.brandName = brandName;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST addCar= new REST();
            response =addCar.requestWithMethodPOST("http://"+ip+":8080/warsztatZlomek/rest/updateClient/addCar",new AddCarModel(accessToken, vin, registrationNumber, model, productionYear, brandName));
            if(!(response==null)) {
                serverIsActive=true;
                int status=response.getResponseStatus();
                Log.d("status ADD CAR: ", String.valueOf(status));
                return status;
            }else{
                serverIsActive=false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mAddCarTask = null;
            switch (status) {
                case 200:
                    showToast();
                    goToActivity(CarsActivity.class);
                    break;
                case 400:
                    showProgress(false);
                    break;
                case -1:
                    if(!serverIsActive){
                        showProgress(false);
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
            mAddCarTask = null;
            showProgress(false);
        }
    }
    public void showToast(){
        String toastText = "Samochód został dodany!";
        Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
