package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by sylwi on 29.10.2018.
 */

public class NetworkConnection {
    Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public boolean checkIfNetworkIsReachable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean networkIsReachable = networkInfo !=null && networkInfo.isConnectedOrConnecting();
        if(networkIsReachable)return true;
        else {
            String toastText = "Network is unreachable!";
            Toast toast = Toast.makeText(this.context, toastText, Toast.LENGTH_LONG);
            toast.show();
            toastText = "Check your network connection.";
            toast = Toast.makeText(this.context, toastText, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }

}
