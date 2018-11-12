package com.example.sylwi.servicecarzlomekmobileaplication.menuManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sylwi.servicecarzlomekmobileaplication.R;

/**
 * Created by sylwi on 11.11.2018.
 */

public class MenuManager extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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


