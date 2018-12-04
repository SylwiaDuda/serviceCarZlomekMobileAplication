package com.example.sylwi.servicecarzlomekmobileaplication.activityManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.MainActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

/**
 * Created by sylwi on 11.11.2018.
 */

public abstract class ActivityManager extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu)
    {
        return true;
    }

    public void goToActivity(Class<?> newActivity){
        Intent intent = new Intent(this,newActivity);
        startActivity(intent);
    };
}


