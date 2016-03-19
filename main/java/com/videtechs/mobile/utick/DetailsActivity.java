package com.videtechs.mobile.utick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity  extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static String urlPay = "http://testpay.vodafonecash.com.gh";
    private static final String TAG_STATUS = "status";
    private static final String TAG_REQUESTA = "requester";
    private static final String TAG_TRANSID = "transactionID";

    String UTK_AMOUNT;
    static final String UTK_USERNAME = "711507";
    static final String UTK_PASS = "hackathon2";
    static final String UTK_TOKEN = "abc1234";
    HttpResponse UTK_RESPONSE;

    String shareText;
    String eventId, eventCloud, eventTitle, eventPrice, eventVenue, eventDate, eventDetails, eventImage, locImage;
    TextView txtTitle, txtPrice, txtVenue, txtDate, txtDetails, txtImage;
    ImageView imgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
        txtTitle = (TextView) findViewById(R.id.det_title);
        txtPrice = (TextView) findViewById(R.id.det_price);
        txtVenue = (TextView) findViewById(R.id.det_venue);
        txtDate = (TextView) findViewById(R.id.det_date);
        txtDetails = (TextView) findViewById(R.id.det_details);
        imgImage = (ImageView) findViewById(R.id.det_img);

        DBAdapter db = new DBAdapter(DetailsActivity.this);
        db.open();

        Cursor coUser = db.getNaviUser("1");
        if (coUser.moveToFirst()) {
            eventId = coUser.getString(0);
        }
        Cursor det = db.getEvent(eventId);
        if (det.moveToFirst()) {
            eventCloud = det.getString(0);
            eventTitle =  det.getString(1);
            eventPrice =  det.getString(2);
            eventDate =  det.getString(3);
            eventVenue =  det.getString(4);
            eventDetails =  det.getString(5);
            eventImage =  det.getString(6);
            txtTitle.setText(eventTitle);
            txtPrice.setText("GH¢" + eventPrice);
            UTK_AMOUNT = eventPrice;
            txtVenue.setText(eventVenue);
            txtDate.setText(eventDate);
            txtDetails.setText(eventDetails);
            shareText = "New UTick Event\n***************************\n\rTitle: " + eventTitle + "\n\rVenue:eventVenue\n\nFor full details and quick ticket, get UTick App today!";
            Picasso.with(DetailsActivity.this)
                    .load(eventImage)
                    .placeholder(R.drawable.event)
                    .error(R.drawable.event)
                    .into(imgImage);
        }

        db.close();

        Button btnShare = (Button)findViewById(R.id.det_btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String shareBody = shareText;
                String shareSubject = getResources().getString(R.string.app_name);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSubject);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_event)));
            }
        });

        Button btnBuy = (Button)findViewById(R.id.det_btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               new utickPay().execute();

            }
        });


    }

    private class utickPay extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailsActivity.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Processing your transaction...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            try {
                DBAdapter db = new DBAdapter(DetailsActivity.this);
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
                    /*
                    InputStream content = UTK_RESPONSE.getEntity().getContent();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(content), 4096);
                    String line;
                    StringBuilder sb =  new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                    String contentStream = sb.toString();

                    Log.d("VFCash", contentStream);
                    */


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }

                db.close();
            }
            catch (Exception ex){
                Toast.makeText(DetailsActivity.this, "Error creating user account. Please try again later.", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String string) {
            // dismiss the dialog
            pDialog.dismiss();
            getResult();
        }

    }

    public void getResult(){

        try {
            Uri uriUrl = Uri.parse("http://testpay.vodafonecash.com.gh");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
