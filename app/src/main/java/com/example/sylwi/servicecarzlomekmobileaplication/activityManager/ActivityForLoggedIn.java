package com.example.sylwi.servicecarzlomekmobileaplication.activityManager;

import android.view.MenuItem;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.CarsActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.ClientDataActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.LoginActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.MainActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.VisitsActivity;

/**
 * Created by sylwi on 11.11.2018.
 */

public class ActivityForLoggedIn extends ActivityManager {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_home){
            goToActivity(MainActivity.class);
            return true;
        }
        if(id == R.id.action_check_your_profil){
            goToActivity(ClientDataActivity.class);
            return true;
        }
        if(id == R.id.action_car){
            goToActivity(CarsActivity.class);
            return true;
        }
        if(id == R.id.action_visits){
            goToActivity(VisitsActivity.class);
            return true;
        }
        if (id == R.id.action_log_out) {
            InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
            internalStorageDirMnager.deleteToken(getApplicationContext());
            goToActivity(LoginActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu)
    {
        MenuItem signIn = menu.findItem(R.id.action_sign_in);
        signIn.setVisible(false);
        MenuItem register = menu.findItem(R.id.action_register);
        register.setVisible(false);
        MenuItem logOut = menu.findItem(R.id.action_log_out);
        logOut.setVisible(true);
        MenuItem clientProfile =  menu.findItem(R.id.action_check_your_profil);
        clientProfile.setVisible(true);
        MenuItem home =  menu.findItem(R.id.action_home);
        home.setVisible(true);
        return true;
    }

}
