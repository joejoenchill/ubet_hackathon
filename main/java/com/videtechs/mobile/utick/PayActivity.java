package com.videtechs.mobile.utick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayActivity  extends AppCompatActivity {

    private ProgressDialog pDialog;
    WebView webPay;

    private static String urlPay = "http://testpay.vodafonecash.com.gh";
    String UTK_AMOUNT;
    static final String UTK_USERNAME = "711507";
    static final String UTK_PASS = "hackathon2";
    static final String UTK_TOKEN = "abc1234";
    HttpResponse UTK_RESPONSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        webPay = (WebView) findViewById(R.id.webPay);
        new utickPay().execute();

    }

    private class utickPay extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PayActivity.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Creating a user account for your device.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            try {
                DBAdapter db = new DBAdapter(PayActivity.this);
                db.open();

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlPay);

                try {
                    //add data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("username", UTK_USERNAME));
                    nameValuePairs.add(new BasicNameValuePair("password", UTK_PASS));
                    nameValuePairs.add(new BasicNameValuePair("token", UTK_TOKEN));
                    nameValuePairs.add(new BasicNameValuePair("amount", UTK_AMOUNT));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    //execute http post
                    UTK_RESPONSE = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }

                db.close();
            }
            catch (Exception ex){
                Toast.makeText(PayActivity.this, "Error creating user account. Please try again later.", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String string) {
            // dismiss the dialog
            pDialog.dismiss();
            Intent intent = new Intent(getApplication(), HomeActivity.class);
            startActivity(intent);
            finish();

        }

    }

}