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
import android.widget.Toast;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.ListVisitsAdapter;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Car;
import com.example.sylwi.servicecarzlomekmobileaplication.model.DeleteCarModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.DeleteVisitModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.model.Visit;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.List;

public class VisitsActivity extends ActivityForLoggedIn implements NavigationView.OnNavigationItemSelectedListener {

    private String ip;
    private boolean activeSerwer = true;
    private GetVisitTask mGetVisitTask = null;
    private DeleteVisitTask mDeleteVisitTask = null;
    private String token;
    private Response response = null;
    private Context mContext;
    private ListView visitListView;
    private List visitList;
    private int visitsToRemoved = 0;
    private int removedVisits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getApplicationContext();
        ip = getString(R.string.ip);
        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        token = internalStorageDirMnager.getToken(mContext);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_visits);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        visitListView = (ListView) findViewById(R.id.visit_list);
        visitListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        visitListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater pompka = actionMode.getMenuInflater();
                pompka.inflate(R.menu.menu_remove_visit,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                String toastText = "Tylko wizytu o statusie nowa moą zostac odwołane";
                Toast toast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG);
                switch (menuItem.getItemId()) {
                    case R.id.action_delete:
                        SparseBooleanArray sparseBooleanArray = visitListView.getCheckedItemPositions();
                        visitsToRemoved = sparseBooleanArray.size();
                        for (int i = 0; i < sparseBooleanArray.size(); ++i){
                            if(sparseBooleanArray.valueAt(i)){
                                Visit visit = (Visit) visitListView.getAdapter().getItem(
                                        sparseBooleanArray.keyAt(i));
                                if (visit.getVisitStatus().equals("NEW")) {
                                    mDeleteVisitTask = new DeleteVisitTask(token,visit.getId());
                                    mDeleteVisitTask.execute((Void) null);
                                }else{
                                    toast.show();
                                }

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
        visitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Visit visit = (Visit) visitListView.getAdapter().getItem(i);
                //Intent intent = new Intent(getApplicationContext(),VisitActivity.class);
               // intent.putExtra("VISIT",visit);
               // startActivity(intent);
            }
        });

        mGetVisitTask = new GetVisitTask(token);
        mGetVisitTask.execute((Void) null);

        ((Button) findViewById(R.id.button_add_visit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goToActivity(AddVisitActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_visits);
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

    /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_visits);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class GetVisitTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;

        GetVisitTask(String token) {
            mToken = token;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST getVisit = new REST();
            response = getVisit.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/authorization/getFutureVisits",new TokenModel(mToken));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                visitList = response.getNewVisitList();
                return status;
            } else {
                activeSerwer = false;
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer status) {
            mGetVisitTask = null;
            switch (status) {
                case 200:
                    ListVisitsAdapter adapter = new ListVisitsAdapter(mContext,R.layout.row_list_visits,visitList);
                    visitListView.setAdapter(adapter);
                    break;
                case 401:
                    //showProgress(false);
                    break;
                case -1:
                    if (!activeSerwer) {
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
            mGetVisitTask = null;
            //showProgress(false);
        }
    }
    public class DeleteVisitTask extends AsyncTask<Void, Void, Integer> {

        private final String mToken;
        private final String visitId;

        public DeleteVisitTask(String mToken, String visitId) {
            this.mToken = mToken;
            this.visitId = visitId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            REST deleteVisit = new REST();

            response = deleteVisit.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/visits/removeVisit",new DeleteVisitModel(mToken,visitId));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                Log.d("ssssssssssssssss: ", String.valueOf(status));
                return status;
            }else{
                activeSerwer=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mDeleteVisitTask= null;
            removedVisits ++;
            switch (status) {
                case 200:
                    if(visitsToRemoved== removedVisits){
                        goToActivity(VisitsActivity.class);
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
            mDeleteVisitTask = null;
            //showProgress(false);
        }
    }
}
