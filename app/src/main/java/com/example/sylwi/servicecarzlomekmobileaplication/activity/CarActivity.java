package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.content.Intent;
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
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

public class CarActivity  extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private Response response;
    private Car car;
    private TextView brandTV;
    private TextView modelTV;
    private TextView registrationNumberTV;
    private TextView productionYearTV;
    private TextView vinNumberTV;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        car = bundle.getParcelable("CAR");
        brandTV = (TextView)findViewById(R.id.brand_form);
        modelTV = (TextView)findViewById(R.id.model_form);
        registrationNumberTV = (TextView)findViewById(R.id.registration_number_form);
        productionYearTV = (TextView)findViewById(R.id.production_year_form);
        vinNumberTV = (TextView)findViewById(R.id.vin_number_form);
        setCarData();
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

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
}