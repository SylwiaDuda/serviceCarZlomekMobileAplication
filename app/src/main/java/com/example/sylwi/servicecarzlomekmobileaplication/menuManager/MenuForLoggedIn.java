package com.example.sylwi.servicecarzlomekmobileaplication.menuManager;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.LoginActivity;
import com.example.sylwi.servicecarzlomekmobileaplication.activity.MainActivity;

/**
 * Created by sylwi on 11.11.2018.
 */

public class MenuForLoggedIn extends MenuManager {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log_out) {
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
        return true;
    }

}
