package com.videtechs.mobile.utick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity  extends AppCompatActivity {

    private ProgressDialog pDialog;
    String name, email, phone, mobile, userName, userEmail, userPhone;
    EditText txtName, txtPhone, txtEmail;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);

        DBAdapter db = new DBAdapter(EditActivity.this);
        db.open();

        txtName = (EditText) findViewById(R.id.new_name);
        txtPhone = (EditText) findViewById(R.id.new_phone);
        txtEmail = (EditText) findViewById(R.id.new_email);

        Cursor coUser = db.getNaviUser("1");
        if (coUser.moveToFirst()) {
            userid = Integer.parseInt(coUser.getString(0));
        }
        Cursor c = db.getUser(userid + "");
        if (c.moveToFirst()) {
            userName = c.getString(2);
            userEmail =  c.getString(3);
            userPhone =  c.getString(4);
        }
        db.close();

        txtName.setText(userName);
        txtPhone.setText(userPhone);
        txtEmail.setText(userEmail);

        Button btnEdit= (Button)findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = txtName.getText().toString();
                phone = txtPhone.getText().toString();
                email = txtEmail.getText().toString();

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
                                    Toast.makeText(EditActivity.this, "Please enter a valid phone number.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }else{
                            Toast.makeText(EditActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(EditActivity.this, "Please enter your name.", Toast.LENGTH_LONG).show();
                        txtName.setFocusable(true);
                    }

                }catch (Throwable e){
                    Toast.makeText(EditActivity.this, "Please enter valid details to continue." + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class SyncData extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditActivity.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Editing a user account on your device.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            try {
                DBAdapter db = new DBAdapter(EditActivity.this);
                db.open();
                db.editUserName(userid, name);
                db.editUserPhone(userid, phone);
                db.editUserEmail(userid, email);
                db.close();
            }
            catch (Exception ex){
                Toast.makeText(EditActivity.this, "Error editing user account. Please try again later.", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String string) {
            pDialog.dismiss();
            Intent intent = new Intent(getApplication(), SettingsActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
