package com.example.sylwi.servicecarzlomekmobileaplication.activityManager;

import android.view.MenuItem;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.LoginActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.RegistrationActivity;

/**
 * Created by sylwi on 11.11.2018.
 */

public class ActivityForNotLoggedIn extends ActivityManager {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            goToActivity(LoginActivity.class);
            return true;
        }
        if (id == R.id.action_register) {
            goToActivity(RegistrationActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu)
    {
        MenuItem signIn = menu.findItem(R.id.action_sign_in);
        signIn.setVisible(true);
        MenuItem register = menu.findItem(R.id.action_register);
        register.setVisible(true);
        MenuItem logOut = menu.findItem(R.id.action_log_out);
        logOut.setVisible(false);
        MenuItem clientProfile =  menu.findItem(R.id.action_check_your_profil);
        clientProfile.setVisible(false);
        MenuItem clientCar =  menu.findItem(R.id.action_car);
        clientCar.setVisible(false);
        MenuItem clientVisits =  menu.findItem(R.id.action_visits);
        clientVisits.setVisible(false);
        return true;
    }
}
