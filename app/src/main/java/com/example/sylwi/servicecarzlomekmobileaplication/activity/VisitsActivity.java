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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sylwi.servicecarzlomekmobileaplication.R;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.InternalStorageDirMnager;
import com.example.sylwi.servicecarzlomekmobileaplication.Service.ListVisitsAdapter;
import com.example.sylwi.servicecarzlomekmobileaplication.activityManager.ActivityForLoggedIn;
import com.example.sylwi.servicecarzlomekmobileaplication.model.TokenModel;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.REST;
import com.example.sylwi.servicecarzlomekmobileaplication.rest.Response;

import java.util.List;

public class VisitsActivity extends ActivityForLoggedIn  implements NavigationView.OnNavigationItemSelectedListener {

    private String ip;
    private boolean activeSerwer = true;
    private GetVisitTask mGetVisitTask=null;
    private Response response = null;
    private Context mContext;
    private ListView visitListView;
    private List visitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getApplicationContext();
        ip = getString(R.string.ip);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_visits);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        visitListView = (ListView)findViewById(R.id.visit_list);
        InternalStorageDirMnager internalStorageDirMnager = new InternalStorageDirMnager();
        final String token = internalStorageDirMnager.getToken(mContext);
        mGetVisitTask = new GetVisitTask(token);
        mGetVisitTask.execute((Void)null);

        ((Button)findViewById(R.id.button_add_visit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddVisitActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
    }
        //carListView = (ListView)findViewById(R.id.car_list);
       // carListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
       /*carListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
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
                        long checked[] = carListView.getCheckedItemIds();

                        for (int i = 0; i < checked.length; ++i){
                            long idLong = checked[i];
                            int idInt = (int) idLong;
                            Car car = carsList.get(idInt);
                            Log.d("ccccccccccccccc",car.toString());

                        }
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });*

    }*/

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

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
            REST login = new REST();
            response = login.requestWithMethodPOST("http://" + ip + ":8080/warsztatZlomek/rest/authorization/getFutureVisits",new TokenModel(mToken));
            if(!(response==null)) {
                activeSerwer=true;
                int status=response.getResponseStatus();
                Log.d("gggggggggggg:", String.valueOf(status));
                return status;
            }else{
                activeSerwer=false;
                return -1;
            }
        }
        @Override
        protected void onPostExecute(final Integer status) {
            mGetVisitTask= null;
            switch (status) {
                case 200:
                    visitList = response.getNewVisit();
                    Log.d("vvvvvvvvvvvv: ",visitList.toString());

                    ListVisitsAdapter adapter = new ListVisitsAdapter(mContext,R.layout.row_list_visits,visitList);
                    visitListView.setAdapter(adapter);
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
            mGetVisitTask = null;
            //showProgress(false);
        }
    }
}
