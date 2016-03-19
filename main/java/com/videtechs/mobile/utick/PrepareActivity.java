package com.videtechs.mobile.utick;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PrepareActivity  extends AppCompatActivity {

    private static final String TAG_SUCCESS = "utSuccess";
    private static final String TAG_MESSAGE = "utMessage";
    private static final String TAG_CLOUD = "utCloud";
    private static final String TAG_NAME = "utName";
    private static final String TAG_PHONE = "utPhone";
    private static final String TAG_EMAIL = "utEmail";
    private static final String TAG_ACCOUNT = "utAccount";
    private static final String TAG_PIN = "utPIN";

    String userId, userCloud, userName, userEmail, userPhone, userAccount, userPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        DBAdapter db = new DBAdapter(PrepareActivity.this);
        db.open();
        int countNavi = db.countUsers();
        if(countNavi == 0){
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            userCloud = "";
            userName = "";
            userEmail = "";
            userPhone = "";
            userAccount = "";
            userPIN = "";
            new userAccount().execute();
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

                DBAdapter db = new DBAdapter(PrepareActivity.this);
                db.open();

                userId = "1";
                Cursor c = db.getUser(userId);
                if (c.moveToFirst()) {
                    userName = c.getString(2);
                    userEmail =  c.getString(3);
                    userPhone =  c.getString(4);
                }

                HiveRequest jRequest = new HiveRequest();
                JSONObject json = jRequest.putUser(userName, userEmail, userPhone);
                Log.d("JSON Next", "JSON is starting here...");
                int success = json.getInt(TAG_SUCCESS);
                String msg = json.getString(TAG_MESSAGE).toString();
                Log.d("Put User", msg);
                if(success == 1){
                    userCloud = json.getString(TAG_CLOUD).toString();
                    userName = json.getString(TAG_NAME).toString();
                    userPhone = json.getString(TAG_PHONE).toString();
                    userEmail = json.getString(TAG_EMAIL).toString();
                    userAccount = json.getString(TAG_ACCOUNT).toString();
                    userPIN = json.getString(TAG_PIN).toString();

                    db.editUserCloud(1, userCloud);
                    db.editUserName(1, userName);
                    db.editUserPhone(1, userPhone);
                    db.editUserEmail(1, userEmail);
                    db.editUserAccount(1, userAccount);
                    db.editUserPin(1, userPIN);
                }


                db.close();
            }
            catch (JSONException e) {
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
