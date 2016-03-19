package com.videtechs.mobile.utick;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private ListView lvEvents;
    String eventCloud, eventTitle,eventPrice, eventVenue, eventDate, eventDetails, eventImage, locImage;

    ListAdapter adapterEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBAdapter db = new DBAdapter(HomeActivity.this);
        db.open();
        int countNavi = db.countUsers();
        if(countNavi == 0){
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            lvEvents = (ListView) findViewById(R.id.lvEvents);
            loadEvents();
            lvEvents.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                    Intent details = new Intent(HomeActivity.this, DetailsActivity.class);
                    String eventId = ((TextView) view.findViewById(R.id.event_id)).getText().toString();
                    DBAdapter db = new DBAdapter(HomeActivity.this);
                    db.open();
                    db.editNaviEvent(1, eventId);
                    db.close();
                    startActivity(details);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnu_about:
                Intent about = new Intent(getApplication(), AboutActivity.class);
                startActivity(about);
                return true;
            case R.id.mnu_settings:
                Intent profile = new Intent(getApplication(), SettingsActivity.class);
                startActivity(profile);
                return true;
            case R.id.mnu_create:
                Intent newe = new Intent(getApplication(), NewActivity.class);
                startActivity(newe);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadEvents(){
        try {

            DBAdapter db = new DBAdapter(HomeActivity.this);
            db.open();

            int evtCount = db.countEvents();
            Events[] eventsData = new Events[evtCount];
            int evtCon = 0;

            Cursor evnt = db.getEvents();
            if (evnt.moveToFirst())
            {
                do {
                    String evId = evnt.getString(0);
                    String evTitle = evnt.getString(2);
                    String evPrice = "GH¢" + evnt.getString(3);
                    String evDate = evnt.getString(4);
                    String evVenue = evnt.getString(5);
                    String evImage = evnt.getString(7);
                    eventsData[evtCon] = new Events(evId, evTitle, evDate, evPrice, evVenue, evImage);
                    evtCon++;
                } while (evnt.moveToNext());
            }
            db.close();

            adapterEvents = new EventsAdapter(this, R.layout.list_event, eventsData);
            lvEvents.setAdapter(adapterEvents);

        }catch (Exception ex){
            Toast.makeText(HomeActivity.this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private class userAccount extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            try{

                DBAdapter db = new DBAdapter(HomeActivity.this);
                db.open();

                HiveRequest jRequest = new HiveRequest();
                //JSONObject json = jRequest.putUser(userName, userEmail, userPhone);
                Log.d("JSON Next", "JSON is starting here...");


                db.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String string) {
            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
