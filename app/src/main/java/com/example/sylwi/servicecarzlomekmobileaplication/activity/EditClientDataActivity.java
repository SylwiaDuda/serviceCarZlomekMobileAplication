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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.NetworkConnection;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.TextWatcherValidateForm;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Client;
import com.example.sylwi.servicecarzlomekmobileaplication.model.EditDataClientModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditClientDataActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener{

    private Client client=null;
    private Context mContext;
    private String ip;
    boolean cancel;
    private View mEditClientDataFormView;
    private View mProgressView;
    private SaveNewClientDataTask mSaveNewClientDataTask = null;
    private boolean serverIsActive = true;
    private Response response=null;
    private EditText firstNameView;
    private EditText lastNameView;
    private TextView emailView;
    private EditText phoneNumberView;
    private EditText cityNameView;
    private EditText streetNameView;
    private EditText buildNumView;
    private EditText aptNumView;
    private EditText zipCodeView;
    private Button saveButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client_data_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_edit_data_client);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        String token =internalStorageDirMnager.getToken(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        client = bundle.getParcelable("CLIENT");
        mContext = getApplicationContext();
        ip = getString(R.string.ip);
        mEditClientDataFormView = findViewById(R.id.edit_data_client_form);
        mProgressView = findViewById(R.id.registration_progress);
        firstNameView = (EditText) findViewById(R.id.first_name_form);
        firstNameView.addTextChangedListener(new TextWatcherValidateForm(firstNameView,mContext));
        lastNameView = (EditText) findViewById(R.id.last_name_form);
        lastNameView.addTextChangedListener(new TextWatcherValidateForm(lastNameView,mContext));
        emailView = (TextView) findViewById(R.id.email_form);
        phoneNumberView = (EditText) findViewById(R.id.phone_number_form);
        phoneNumberView.addTextChangedListener(new TextWatcherValidateForm(phoneNumberView,mContext));
        cityNameView = (EditText) findViewById(R.id.city_name_form);
        cityNameView.addTextChangedListener(new TextWatcherValidateForm(cityNameView,mContext));
        streetNameView = (EditText) findViewById(R.id.street_name_form);
        streetNameView.addTextChangedListener(new TextWatcherValidateForm(streetNameView,mContext));
        buildNumView = (EditText) findViewById(R.id.build_num_form);
        buildNumView.addTextChangedListener(new TextWatcherValidateForm(buildNumView,mContext));
        aptNumView = (EditText) findViewById(R.id.apt_num_form);
        aptNumView.addTextChangedListener(new TextWatcherValidateForm(aptNumView,mContext));
        zipCodeView = (EditText) findViewById(R.id.zip_code_form);
        zipCodeView.addTextChangedListener(new TextWatcherValidateForm(zipCodeView,mContext));
        setClientData();
        saveButton = (Button) findViewById(R.id.save_data_client_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSaveNewClientData();
            }
        });
        cancelButton = (Button) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ClientDataActivity.class);
            }
        });
        //mRegistrationFormView = findViewById(R.id.registration_form);
       // mProgressView = findViewById(R.id.registration_progress);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_edit_data_client);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_edit_data_client);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//------------------------------------------------------------------------------------------------------progressBar
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEditClientDataFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mEditClientDataFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEditClientDataFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mEditClientDataFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public void setClientData(){
        firstNameView.setText(client.getFirstName());
        lastNameView.setText(client.getLastName());
        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        emailView.setText(internalStorageDirMnager.getEmail(mContext));
        phoneNumberView.setText(client.getPhoneNumber());
        cityNameView.setText(client.getCityName());
        streetNameView.setText(client.getStreetName());
        buildNumView.setText(client.getBuildNum());
        aptNumView.setText(client.getAptNum());
        zipCodeView.setText(client.getZipCode());

    }
    //------------------------------------------------------------------------------------------------------tryCreareAccount
    public void attemptSaveNewClientData() {
        NetworkConnection networkConnection = new NetworkConnection(mContext);
        boolean networkIsReachable = networkConnection.checkIfNetworkIsReachable();
        if(networkIsReachable) {
            if (mSaveNewClientDataTask != null) {
                return;
            }
            cancel = false;
            View focusView = null;
            validateRegistrationForm();
            if (cancel) {
                //focusView.requestFocus();
            } else {
                String firstName = firstNameView.getText().toString();
                String lastName = lastNameView.getText().toString();
                String email = client.getEmail();
                String phoneNumber = phoneNumberView.getText().toString();
                String cityName = cityNameView.getText().toString();
                String streetName = streetNameView.getText().toString();
                String buildNum = buildNumView.getText().toString();
                String aptNum = aptNumView.getText().toString();
                String zipCode = zipCodeView.getText().toString();
                showProgress(true);
                InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
                String accessToken = internalStorageDirMnager.getToken(mContext);
                String password = internalStorageDirMnager.getKey(mContext);
                String confirmPassword = password;


                mSaveNewClientDataTask = new SaveNewClientDataTask(accessToken,firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode, password, confirmPassword);
                mSaveNewClientDataTask.execute((Void) null);
            }

        }
    }
    //------------------------------------------------------------------------------------------------------Validation
    public void validateRegistrationForm(){
        validate(firstNameView);
        validate(lastNameView);
        validate(phoneNumberView);
        validate(cityNameView);
        validate(streetNameView);
        validate(buildNumView);
        validate(aptNumView);
        validate(zipCodeView);
    }
    public void validate(EditText editText){
        String text = editText.getText().toString();
        boolean textIsEmpty = TextUtils.isEmpty(text);
        if(textIsEmpty){
            if(editText.getId()!=R.id.apt_num_form){
                editText.setError(getString(R.string.error_field_required));
                cancel = true;
            }
        }else{
            switch (editText.getId()){
                case R.id.first_name_form:
                case R.id.last_name_form:
                    if(text.length()<3 || text.length()>30){
                        editText.setError("Wprowadź od 3 do 30 znaków");
                        cancel = true;
                    }
                    else {
                        patternForNamesWithUnicode(editText,text);
                    }
                    break;
                case R.id.phone_number_form:
                    if(text.length()<9 || text.length()>15){
                        editText.setError("Wprowadź od 9 do 15 znaków");
                        cancel = true;
                    }else {
                        Pattern pattern = Pattern.compile("\\+48\\d{9}");
                        Matcher matcher = pattern.matcher(text);
                        boolean correctPhoneNumber = matcher.matches();
                        if(!correctPhoneNumber ){
                            editText.setError("Wprowadź dane w postaci: +48xxxxxxxxx");
                            cancel = true;
                        }
                    }
                    break;
                case R.id.city_name_form:
                    if(text.length()<3 || text.length()>20){
                        editText.setError("Wprowadź od 2 do 20 znaków");
                        cancel = true;
                    }else{
                        patternForNamesWithUnicode(editText,text);
                    }
                    break;
                case R.id.street_name_form:
                    if(text.length()<3 || text.length()>40){
                        editText.setError("Wprowadź od 3 do 40 znaków");
                        cancel = true;
                    }else{
                        patternForNamesWithUnicode(editText,text);
                    }
                    break;
                case R.id.build_num_form:
                    if(text.length()<1 || text.length()>5){
                        editText.setError("Wprowadź od 1 do 5 znaków");
                        cancel = true;
                    }else {
                        patternForNumber(editText,text);
                    }
                    break;
                case R.id.apt_num_form:
                    if(text.length()>5 ){
                        editText.setError("Wprowadź do 5 znaków");
                        cancel = true;
                    }else {
                        patternForNumber(editText,text);
                    }
                    break;
                case R.id.zip_code_form:
                    Pattern pattern = Pattern.compile("[0-9]{2}+-+[0-9]{3}");
                    Matcher matcher = pattern.matcher(text);
                    boolean correctZipCode = matcher.matches();
                    if(!correctZipCode ){
                        editText.setError("Wprowadź dane w postaci: xx-xxx");
                        cancel = true;
                    }
                    break;
                default:

            }
        }
    }
    public void patternForNamesWithUnicode(EditText editText, String text){
        Pattern pattern = Pattern.compile("[A-ZŹĄĘÓŁŻ]{1}+[a-z,ąęółńćźż]{2,}");
        Matcher matcher = pattern.matcher(text);
        boolean correctText = matcher.matches();
        if(!correctText ){
            editText.setError("Wprowadź dane w postaci: Xxxx...");
            cancel = true;
        }
    }
    public void patternForNumber(EditText editText, String text){
        Pattern pattern = Pattern.compile("[1-9]{1,}+[a-z,ąęółńćźż]{0,}");
        Matcher matcher = pattern.matcher(text);
        boolean correctText = matcher.matches();
        if(!correctText ){
            editText.setError("Numer powinien zaczynać się od cyfry większej od 0 w postaci np:. 123c");
            cancel = true;
        }
    }

    //------------------------------------------------------------------------------------------------work in background
    public class SaveNewClientDataTask extends AsyncTask<Void, Void, Integer> {

        private String accessToken;
        private final String firstName;
        private final String lastName;
        private final String email;
        private final String phoneNumber;
        private final String cityName;
        private final String streetName;
        private final String buildNum;
        private final String aptNum;
        private final String zipCode;
        private String password;
        private String confirmPassword;

        SaveNewClientDataTask(String accessToken, String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword){
            this.accessToken = accessToken;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.cityName = cityName;
            this.streetName = streetName;
            this.buildNum = buildNum;
            this.aptNum = aptNum;
            this.zipCode = zipCode;
            this.password = password;
            this.confirmPassword =  confirmPassword;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST saveNewClientData= new REST();
            response = saveNewClientData.requestWithMethodPOST("http://"+ip+":8080/warsztatZlomek/rest/updateClient/editClientData",new EditDataClientModel(accessToken,firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode,password, confirmPassword));
            if(!(response==null)){
                serverIsActive =true;
                int status=response.getResponseStatus();
                Log.d("STATUS EDIT: ", String.valueOf(status));
                return status;
            }else{
                serverIsActive =false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mSaveNewClientDataTask = null;
            switch (status) {
                case 200:
                    showToast();
                    goToActivity(ClientDataActivity.class);
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
            mSaveNewClientDataTask = null;
            showProgress(false);
        }
    }
    public void showToast(){
        String toastText = "Zmiana danych powiodłą się!";
        Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
