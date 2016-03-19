package com.videtechs.mobile.utick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Year
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        String copi = "\u00a9";

        TextView appDev = (TextView) findViewById(R.id.lbl_app_ryt);
        appDev.setText(copi + year + " - All Rights Reserved" );

        //Version
        String versionName = BuildConfig.VERSION_NAME;

        TextView appVer = (TextView) findViewById(R.id.lbl_app_ver);
        appVer.setText("Version " + versionName);
    }
}
