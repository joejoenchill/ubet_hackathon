package com.videtechs.mobile.utick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class LoginActivity  extends AppCompatActivity {

    private ProgressDialog pDialog;
    String vide, regid, name, email, phone, mobile, account, pin;
    EditText txtName, txtPhone, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);

        txtName = (EditText) findViewById(R.id.new_name);
        txtPhone = (EditText) findViewById(R.id.new_phone);
        txtEmail = (EditText) findViewById(R.id.new_email);

        Button btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = txtName.getText().toString();
                phone = txtPhone.getText().toString();
                email = txtEmail.getText().toString();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                vide = "0";
                regid = "0";
                Random rand = new Random();
                int numAcc = rand.nextInt(9000000) + 1000000;
                account = numAcc + "";
                int numPIN = rand.nextInt(90000) + 10000;
                pin = numPIN + "";
                try {
                    if(name.length() >= 3){
                        if(email.length() >= 10 && email.contains("@") && email.contains(".")){
                            if(phone.startsWith("+") && phone.length() == 13){
                                new SyncData().execute();
                            }else {
                                int intNum = Integer.parseInt(phone);
                                mobile = String.valueOf(intNum);
                                if(mobile.length() == 9) {
                                    phone = "+233" + mobile;
                                    new SyncData().execute();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Please enter a valid phone number.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Please enter your name.", Toast.LENGTH_LONG).show();
                        txtName.setFocusable(true);
                    }

                }catch (Throwable e){
                    Toast.makeText(LoginActivity.this, "Please enter valid details to continue.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public int genPIN() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    private class SyncData extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
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
                DBAdapter db = new DBAdapter(LoginActivity.this);
                db.open();

                db.resetUsers();
                db.insertUser(vide, regid, name, email, phone, account, pin);

                db.resetRegions();
                db.insertRegion("1", "Ashanti Region");
                db.insertRegion("2", "Brong Ahafo Region");
                db.insertRegion("3", "Central Region");
                db.insertRegion("4", "Eastern Region");
                db.insertRegion("5", "Greater Accra Region");
                db.insertRegion("6", "Northern Region");
                db.insertRegion("7", "Upper East Region");
                db.insertRegion("8", "Upper West Region");
                db.insertRegion("9", "Volta Region");
                db.insertRegion("10", "Western Region");

                db.resetNavi();
                db.insertNavi("1", "1", "1", "0.00");

                db.close();
            }
            catch (Exception ex){
                Toast.makeText(LoginActivity.this, "Error creating user account. Please try again later.", Toast.LENGTH_LONG).show();
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
