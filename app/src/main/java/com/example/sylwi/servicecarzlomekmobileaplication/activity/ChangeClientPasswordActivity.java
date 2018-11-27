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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.sql.BatchUpdateException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeClientPasswordActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private Client client=null;
    private Context mContext;
    private String ip;
    boolean cancel;
    private ChangePasswordTask mChangePasswordTask = null;
    private boolean serverIsActive = true;
    private Response response;

    private View mChangePasswordFormView;
    private View mProgressView;

    private EditText lastPasswordET;
    private EditText newPasswordET;
    private EditText confirmPasswordET;

    private Button buttonCancel;
    private Button buttonChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_client_password_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_change_client_password);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        client = bundle.getParcelable("CLIENT");
        mContext = getApplicationContext();
        ip = getString(R.string.ip);

        mChangePasswordFormView = (View)findViewById(R.id.change_password_form);
        mProgressView = (View)findViewById(R.id.change_password_progress);

        lastPasswordET= (EditText)findViewById(R.id.last_password_form);
        lastPasswordET.addTextChangedListener(new TextWatcherValidateForm(lastPasswordET,mContext));
        newPasswordET = (EditText)findViewById(R.id.password_form);
        newPasswordET.addTextChangedListener(new TextWatcherValidateForm(newPasswordET,mContext));
        confirmPasswordET = (EditText)findViewById(R.id.conf_password_form);
        confirmPasswordET.addTextChangedListener(new TextWatcherValidateForm(confirmPasswordET,mContext));

        buttonCancel = (Button)findViewById(R.id.cancel_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ClientDataActivity.class);
            }
        });
        buttonChangePassword = (Button)findViewById(R.id.change_password_button);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChangePassword();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_change_client_password);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_change_client_password);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //------------------------------------------------------------------------------------------------------progressBar
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mChangePasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mChangePasswordFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mChangePasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mChangePasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    //------------------------------------------------------------------------------------------------------tryCreareAccount
    public void attemptChangePassword() {
        NetworkConnection networkConnection = new NetworkConnection(mContext);
        boolean networkIsReachable = networkConnection.checkIfNetworkIsReachable();
        if(networkIsReachable) {
            if (mChangePasswordTask != null) {
                return;
            }
            cancel = false;
            View focusView = null;
            validateChangePasswordForm();
            if (cancel) {
                //focusView.requestFocus();
            } else {
                String lastPassword = lastPasswordET.getText().toString();
                String newPassword = newPasswordET.getText().toString();
                String confirmPassword = confirmPasswordET.getText().toString();
                String firstName = client.getFirstName();
                String lastName =  client.getLastName();
                String email =  client.getEmail();
                String phoneNumber = client.getPhoneNumber();
                String cityName = client.getCityName();
                String streetName = client.getStreetName();
                String buildNum =  client.getBuildNum();
                String aptNum =  client.getAptNum();
                String zipCode =  client.getZipCode();

                showProgress(true);
                InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
                String accessToken = internalStorageDirMnager.getToken(mContext);

                mChangePasswordTask = new ChangePasswordTask(accessToken,firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode, newPassword, confirmPassword);
                mChangePasswordTask.execute((Void) null);
            }

        }
    }
    //------------------------------------------------------------------------------------------------------Validation
    public void validateChangePasswordForm(){
        validate(lastPasswordET);
        validate(newPasswordET);
        validate(confirmPasswordET);
    }
    public void validate(EditText editText){
        String text = editText.getText().toString();
        boolean textIsEmpty = TextUtils.isEmpty(text);
        if(textIsEmpty){
            editText.setError(getString(R.string.error_field_required));
            cancel = true;
        }else {
            switch (editText.getId()) {
                case R.id.last_password_form:
                    InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
                    String lastPassword = internalStorageDirMnager.getKey(mContext);
                    if (!lastPassword.equals(text)) {
                        editText.setError("Wprowadź poprawne obecne hasło!");
                        cancel = true;
                    }
                    break;
                case R.id.password_form:
                    if (text.length() < 6 || text.length() > 20) {
                        editText.setError("Wprowadź od 6 do 20 znaków");
                        cancel = true;
                    } else {
                        Pattern pattern = Pattern.compile("[A-Za-z0-9ĄŻŹÓŁĘążźćńłóę!@#%*^]{6,20}");
                        Matcher matcher = pattern.matcher(text);
                        boolean correctText = matcher.matches();
                        if(!correctText ){
                            editText.setError("Wprowadź inne hasło");
                            cancel = true;
                        }
                    }
                    break;
                case R.id.conf_password_form:
                    String password = newPasswordET.getText().toString();
                    if (!TextUtils.isEmpty(password) && !text.equals(password)) {
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
    //------------------------------------------------------------------------------------------------work in background
    public class ChangePasswordTask extends AsyncTask<Void, Void, Integer> {

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

        ChangePasswordTask(String accessToken, String firstName, String lastName, String email, String phoneNumber, String cityName, String streetName, String buildNum, String aptNum, String zipCode, String password, String confirmPassword){
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
            response = saveNewClientData.request("http://"+ip+":8080/warsztatZlomek/rest/updateClient/editClientData",new EditDataClientModel(accessToken,firstName,lastName,email,phoneNumber,cityName,streetName,buildNum,aptNum,zipCode,password, confirmPassword));
            if(!(response==null)){
                serverIsActive =true;
                int status=response.getResponseStatus();
                Log.d("STATUS CHANGE PASS: ", String.valueOf(status));
                return status;
            }else{
                serverIsActive =false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mChangePasswordTask = null;
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
            mChangePasswordTask = null;
            showProgress(false);
        }
    }
    public void showToast(){
        String toastText = "Zmiana hasła powiodłą się!";
        Toast toast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}

