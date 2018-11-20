package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.ListCarsAdapter;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Client;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

public class DataClientActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener{

    Response response = null;
    String ip;
    Boolean activeSerwer;
    GetDataClientTask mGetDataClientTask = null;
    String token;

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView phoneNumber;
    private TextView cityName;
    private TextView streetName;
    private TextView buildNum;
    private TextView aptNum;
    private TextView zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_client_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_data_client);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ip=getString(R.string.ip);
        activeSerwer=true;
        Intent intent = getIntent();
        token = intent.getExtras().get("TOKEN").toString();
        setGlobalToken(token);
        firstName = (TextView)findViewById(R.id.first_name_form);
        lastName = (TextView)findViewById(R.id.last_name_form);
        email = (TextView)findViewById(R.id.email_form);
        phoneNumber = (TextView)findViewById(R.id.phone_number_form);
        cityName = (TextView)findViewById(R.id.city_name_form);
        streetName = (TextView)findViewById(R.id.street_name_form);
        buildNum = (TextView)findViewById(R.id.build_num_form);
        aptNum = (TextView)findViewById(R.id.apt_num_form);
        zipCode = (TextView)findViewById(R.id.zip_code_form);



        mGetDataClientTask = new GetDataClientTask(token);
        mGetDataClientTask.execute((Void)null);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_data_client);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_data_client);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class GetDataClientTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;

        GetDataClientTask(String token) {
            mToken = token;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST login = new REST();
            response = login.request("http://" + ip + ":8080/warsztatZlomek/rest/authorization/getFullClientData",new TokenModel(mToken));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                Log.d("status:", String.valueOf(status));
                return status;
            }else{
                activeSerwer=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetDataClientTask= null;
            switch (status) {
                case 200:
                    Client client = response.getDataClient();
                    setDataCient(client);
                    break;
                case 401:
                    //showProgress(false);
                    break;
                case -1:
                    if(!activeSerwer){
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
            mGetDataClientTask = null;
            //showProgress(false);
        }
    }
    public void setDataCient(Client client){
        firstName.setText(client.getFirstName());
        lastName.setText(client.getLastName());
        email.setText(client.getEmail());
        phoneNumber.setText(client.getPhoneNumber());
        cityName.setText(client.getCityName());
        streetName.setText(client.getStreetName());
        buildNum.setText(client.getBuildNum());
        aptNum.setText(client.getAptNum());
        zipCode.setText(client.getZipCode());
    }
}
