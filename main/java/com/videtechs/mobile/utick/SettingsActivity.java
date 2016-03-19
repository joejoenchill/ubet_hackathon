package com.videtechs.mobile.utick;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    String userId, userVide, userReg, userRegion, userName, userEmail, userPhone, userAccount, userExpired;
    TextView txtName, txtEmail, txtPhone, txtRegion, txtAccount, txtExpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);

        txtName = (TextView) findViewById(R.id.user_name);
        txtEmail = (TextView) findViewById(R.id.user_email);
        txtPhone = (TextView) findViewById(R.id.user_phone);
        txtRegion = (TextView) findViewById(R.id.user_region);
        txtAccount = (TextView) findViewById(R.id.user_account);
        txtExpired = (TextView) findViewById(R.id.user_expired);

        userId = "0"; userName = ""; userEmail = ""; userPhone = ""; userAccount = ""; userExpired = "";

        displaySettings();

        Button btnProfile = (Button)findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent edit = new Intent(getApplication(), EditActivity.class);
                startActivity(edit);
            }
        });

        Button btnRegion = (Button)findViewById(R.id.btnRegion);
        btnRegion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent regions = new Intent(getApplication(), RegionsActivity.class);
                startActivity(regions);
            }
        });

        Button btnRefresh = (Button)findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settings = new Intent(getApplication(), PrepareActivity.class);
                startActivity(settings);
            }
        });
    }

    private void displaySettings(){
        try {

            DBAdapter db = new DBAdapter(SettingsActivity.this);
            db.open();

            Cursor coUser = db.getNaviUser("1");
            if (coUser.moveToFirst()) {
                userId = coUser.getString(0);
            }

            Cursor c = db.getUser(userId);
            if (c.moveToFirst()) {
                userVide = c.getString(0);
                userReg = c.getString(1);
                userName = c.getString(2);
                userEmail =  c.getString(3);
                userPhone =  c.getString(4);
                userAccount =  c.getString(5);
                userExpired =  c.getString(6);
            }

            Cursor r = db.getRegion(userReg);
            if (r.moveToFirst()) {
                userRegion = r.getString(1);
            }

            db.close();

            txtName.setText(userName);
            txtEmail.setText(userEmail);
            txtPhone.setText(userPhone);
            txtAccount.setText(userAccount);
            txtExpired.setText(userExpired);
            txtRegion.setText(userRegion);

        }catch (Exception ex){
            Toast.makeText(SettingsActivity.this, "", Toast.LENGTH_SHORT).show();
        }
    }
}

