package com.example.sylwi.servicecarzlomekmobileaplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.ListCarsAdapter;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.DeleteCarModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.List;

public class CarsActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {
    private GetCarsTask mGetCarsTask = null;
    private DeleteCarTask mDeleteCarTask = null;
    private String ip;
    private boolean activeSerwer = true;
    private Response response = null;
    private ListView carListView;
    private List <Car>carsList;
    private Context mContext;
    private Button buttonAddCar;
    private String token;
    private int carsToRemoved = 0;
    private int removedCars = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getApplicationContext();
        ip=getString(R.string.ip);
        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        token = internalStorageDirMnager.getToken(mContext);
        mGetCarsTask = new GetCarsTask(token);
        mGetCarsTask.execute((Void) null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_cars);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        carListView = (ListView)findViewById(R.id.car_list);
        carListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        carListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater pompka = actionMode.getMenuInflater();
                pompka.inflate(R.menu.menu_remove_car,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_delete:
                        SparseBooleanArray sparseBooleanArray = carListView.getCheckedItemPositions();
                        carsToRemoved = sparseBooleanArray.size();
                        for (int i = 0; i < sparseBooleanArray.size(); ++i){
                            if(sparseBooleanArray.valueAt(i)){

                                Car car = (Car)carListView.getAdapter().getItem(
                                        sparseBooleanArray.keyAt(i));
                                mDeleteCarTask = new DeleteCarTask(token,car.getId());
                                mDeleteCarTask.execute((Void) null);
                            }
                        }
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Car car = (Car)carListView.getAdapter().getItem(i);
                Intent intent = new Intent(getApplicationContext(),CarActivity.class);
                intent.putExtra("CAR",car);
                startActivity(intent);
            }
        });
        buttonAddCar = (Button) findViewById(R.id.button_add_car);
        buttonAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(AddCarActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_cars);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_cars);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class GetCarsTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;

        GetCarsTask(String token) {
            mToken = token;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST getCars = new REST();
            response = getCars.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/car/getAllClientsCars",new TokenModel(mToken));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                carsList = response.getListCar();
                return status;
            }else{
                activeSerwer=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetCarsTask= null;
            switch (status) {
                case 200:
                    ListCarsAdapter adapter = new ListCarsAdapter(mContext,R.layout.row_list_cars,carsList);
                    carListView.setAdapter(adapter);
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
            mGetCarsTask = null;
            //showProgress(false);
        }
    }
    public class DeleteCarTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;
        private final String carId;

        public DeleteCarTask(String mToken, String carId) {
            this.mToken = mToken;
            this.carId = carId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST deleteCar = new REST();

            response = deleteCar.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/updateClient/removeCar",new DeleteCarModel(token,carId));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                //InternalStorageDirMnager internalStorageDirMnager =new InternalStorageDirMnager();
                //internalStorageDirMnager.setToken(response.get,mContext);
                return status;
            }else{
                activeSerwer=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetCarsTask= null;
            removedCars ++;
            switch (status) {
                case 200:
                   if(carsToRemoved== removedCars){
                       goToActivity(CarsActivity.class);
                   }
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
            mGetCarsTask = null;
            //showProgress(false);
        }
    }

}
