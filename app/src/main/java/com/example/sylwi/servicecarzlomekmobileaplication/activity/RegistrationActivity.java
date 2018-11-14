package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.NetworkConnection;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.TextWatcherValidateForm;
import com.example.sylwi.servicecarzlomekmobileaplication.menuManager.MenuForNotLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.CheckEmailModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.RegistrationModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.SignInModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends MenuForNotLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private EditText firstNameView;
    private EditText lastNameView;
    private EditText emailView;
    private EditText phoneNumberView;
    private EditText cityNameView;
    private EditText streetNameView;
    private EditText buildNumView;
    private EditText aptNumView;
    private EditText zipCodeView;
    private EditText passwordView;
    private EditText confirmPasswordView;
    private Button createAccountButton;
    private Button cancelButton;

    private static Context mContext;

    private String  ip;
    boolean cancel;
    private View mRegistrationFormView;
    private View mProgressView;
    private RegistrationTask mRegistrationTask = null;
    private CheckEmailTask mCheckEmailTask = null;
    private boolean serverIsActive = true;
    private Response response=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_registration_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = getApplicationContext();
        ip = getString(R.string.ip);
        firstNameView = (EditText) findViewById(R.id.first_name_form);
        firstNameView.addTextChangedListener(new TextWatcherValidateForm(firstNameView,mContext));
        lastNameView = (EditText) findViewById(R.id.last_name_form);
        lastNameView.addTextChangedListener(new TextWatcherValidateForm(lastNameView,mContext));
        emailView = (EditText) findViewById(R.id.email_form);
        emailView.addTextChangedListener(new TextWatcherValidateForm(emailView,mContext));
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
        passwordView = (EditText) findViewById(R.id.password_form);
        passwordView.addTextChangedListener(new TextWatcherValidateForm(passwordView,mContext));
        confirmPasswordView = (EditText) findViewById(R.id.conf_password_form);
        confirmPasswordView.addTextChangedListener(new TextWatcherValidateForm(confirmPasswordView,mContext));

        createAccountButton = (Button) findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        cancelButton = (Button) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegistrationFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.registration_progress);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_registration_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//--------------------------------------------------------------------------------------------nav-draver
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_registration_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//------------------------------------------------------------------------------------------------------progressBar
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
//------------------------------------------------------------------------------------------------------tryCreareAccount
    public void attemptRegister() {
        NetworkConnection networkConnection = new NetworkConnection(mContext);
        boolean networkIsReachable = networkConnection.checkIfNetworkIsReachable();
        if(networkIsReachable) {
            if (mCheckEmailTask != null) {
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
                String email = emailView.getText().toString();
                String phoneNumber = phoneNumberView.getText().toString();
                String cityName = cityNameView.getText().toString();
                String streetName = streetNameView.getText().toString();
                String buildNum = buildNumView.getText().toString();
                String aptNum = aptNumView.getText().toString();
                String zipCode = zipCodeView.getText().toString();
                String password = passwordView.getText().toString();
                String confirmPassword = confirmPasswordView.getText().toString();
                showProgress(true);
                mCheckEmailTask = new CheckEmailTask(firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode,password,confirmPassword);
                mCheckEmailTask.execute((Void) null);
            }

        }
    }
//------------------------------------------------------------------------------------------------------Validation
    public void validateRegistrationForm(){
        validate(firstNameView);
        validate(lastNameView);
        validate(emailView);
        validate(phoneNumberView);
        validate(cityNameView);
        validate(streetNameView);
        validate(buildNumView);
        validate(aptNumView);
        validate(zipCodeView);
        validate(passwordView);
        validate(confirmPasswordView);
    }
    public void validate(EditText editText){
        String text = editText.getText().toString();
        boolean textIsEmpty = TextUtils.isEmpty(text);
        if(textIsEmpty){
            if(editText.getId()!=R.id.apt_num_form){
                editText.setError(getString(R.string.error_field_required));
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
                case R.id.email_form:
                    boolean correctEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
                    if(!correctEmail){
                        editText.setError(getString(R.string.error_invalid_email));
                        cancel = true;
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
                        String firstSign = text.substring(0,1);
                        int firstNumer = Integer.valueOf(firstSign);
                        if(firstNumer<=0){
                            editText.setError("Numer nie może być mniejszy od zera");
                            cancel = true;
                        }
                    }
                    break;
                case R.id.apt_num_form:
                    if(text.length()>5 ){
                        editText.setError("Wprowadź do 5 znaków");
                        cancel = true;
                    }else {
                        String firstSign = text.substring(0,1);
                        int firstNumer = Integer.valueOf(firstSign);
                        if(firstNumer<=0){
                            editText.setError("Numer nie może być mniejszy od zera");
                            cancel = true;
                        }
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
                case R.id.password:
                    if(text.length()<6 || text.length()>20){
                        editText.setError("Wprowadź od 6 do 20 znaków");
                        cancel = true;
                    }else{
                        patternForPassword(editText,text);
                    }
                    break;
                case R.id.conf_password_form:
                    String password = passwordView.getText().toString();
                    if(!TextUtils.isEmpty(password) && !text.equals(password)){
                        editText.setError("Wpisane hasła nie są identyczne");
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
    public void patternForPassword(EditText editText, String text){
        Pattern pattern = Pattern.compile("[A-Za-z0-9ĄŻŹÓŁĘążźćńłóę!@#%*^]{6,20}");
        Matcher matcher = pattern.matcher(text);
        boolean correctText = matcher.matches();
        if(!correctText ){
            editText.setError("Wprowadź inne hasło");
            cancel = true;
        }
    }

//------------------------------------------------------------------------------------------------work in background
    public class CheckEmailTask extends AsyncTask<Void, Void, Integer> {

        private final String firstName;
        private final String lastName;
        private final String email;
        private final String phoneNumber;
        private final String cityName;
        private final String streetName;
        private final String buildNum;
        private final String aptNum;
        private final String zipCode;
        private final String password;
        private final String confirmPassword;

        CheckEmailTask(String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword){
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
            this.confirmPassword = confirmPassword;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST checkEmail= new REST();
            response = checkEmail.request("http://"+ip+":8080/warsztatZlomek/rest/authorization/checkEmail",new CheckEmailModel(email));
            if(!(response==null)){
                serverIsActive =true;
                int status=response.getResponseStatus();
                return status;
            }else{
                serverIsActive =false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mCheckEmailTask = null;
            switch (status) {
                case 200:
                    showProgress(false);
                    emailView.setError(getString(R.string.error_email_EXIST));
                    emailView.requestFocus();
                    break;
                case 400:
                    mRegistrationTask = new RegistrationTask(firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode,password,confirmPassword);
                    mRegistrationTask.execute((Void) null);
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
            mCheckEmailTask = null;
            showProgress(false);
        }
    }
    public class RegistrationTask extends AsyncTask<Void, Void, Integer> {

        private final String firstName;
        private final String lastName;
        private final String email;
        private final String phoneNumber;
        private final String cityName;
        private final String streetName;
        private final String buildNum;
        private final String aptNum;
        private final String zipCode;
        private final String password;
        private final String confirmPassword;

        RegistrationTask(String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword){
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
            this.confirmPassword = confirmPassword;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST login = new REST();
            response = login.request("http://" + ip + ":8080/warsztatZlomek/rest/authorization/register",new RegistrationModel(firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode,password,confirmPassword));
            if(!(response==null)) {
                serverIsActive =true;
                int status=response.getResponseStatus();
                Log.d("SignIn response status:", String.valueOf(status));
                return status;
            }else{
                serverIsActive =false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mRegistrationTask = null;
            switch (status) {
                case 200:
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                   // intent.putExtra("RESPONSE", (Parcelable) response);
                    startActivity(intent);
                    break;
                case 401:
                    showProgress(false);
                   // mPasswordView.setError(getString(R.string.error_incorrect_password));
                   // mPasswordView.requestFocus();
                    break;
                case 400:
                    showProgress(false);
                    String toastTextDane = "Błędne dane!";
                    Toast toastDane = Toast.makeText(mContext, toastTextDane, Toast.LENGTH_LONG);
                    toastDane.show();
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
                    showProgress(false);
                    break;
            }
        }
        @Override
        protected void onCancelled() {
            mRegistrationTask = null;
            showProgress(false);
        }
    }
}
