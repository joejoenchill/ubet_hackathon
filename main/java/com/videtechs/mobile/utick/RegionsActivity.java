package com.videtechs.mobile.utick;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RegionsActivity  extends AppCompatActivity {

    private static final String regId = "Id";
    private static final String regTitle = "Title";

    private ListView lvRegions;

    ArrayList<HashMap<String, String>> regList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        regList = new ArrayList<HashMap<String, String>>();

        DBAdapter db = new DBAdapter(RegionsActivity.this);

        db.open();

        Cursor c = db.getRegions();
        if (c.moveToFirst())
        {
            do {
                String disId = c.getString(0);
                String disName = c.getString(2);

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(regId, disId);
                map.put(regTitle, disName);

                regList.add(map);

            } while (c.moveToNext());
        }
        db.close();
        lvRegions = (ListView) findViewById(R.id.lvRegions);

        ListAdapter adapter = new SimpleAdapter(RegionsActivity.this, regList,
                R.layout.list_region, new String[] {regId, regTitle}, new int[] {R.id.regId, R.id.regTitle });

        lvRegions.setAdapter(adapter);

        lvRegions.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                Intent i = new Intent(RegionsActivity.this, SettingsActivity.class);
                String regiId = ((TextView) view.findViewById(R.id.regId)).getText().toString();
                DBAdapter db = new DBAdapter(RegionsActivity.this);
                db.open();
                db.editNaviRegion(1, regiId);
                db.editUserRegion(1,regiId);
                startActivity(i);
            }
        });
    }
}
