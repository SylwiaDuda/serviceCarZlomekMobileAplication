package com.example.sylwi.servicecarzlomekmobileaplication.activity;

        import android.content.Context;
        import android.content.Intent;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.MenuItem;

        import com.example.sylwi.servicecarzlomekmobileaplication.R;
        import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
        import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

        import java.util.Objects;

public class MainActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private static Response response;

    public static Response getResponse() {
        return response;
    }

    public static void setResponse(Response response) {
        MainActivity.response = response;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent= getIntent();
        String previousActivityName = intent.getStringExtra("CLASSNAME");
        String token = intent.getStringExtra("TOKEN");
        setGlobalToken(token);
        switch (previousActivityName){
            case "LoginActivity":
                //response=LoginActivity.getResponse();
                break;
            default:
                break;
        }
        //response=LoginActivity.getResponse();
        //Log.d("OOOOOOOOOOOOOOOOOOOOOO", response.getStringValue("accessToken"));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
