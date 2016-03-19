package com.videtechs.mobile.utick;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class NewActivity  extends AppCompatActivity {

    private ProgressDialog pDialog;

    EditText newDate;
    Button btnPika, btnSubmt;
    private static int RESULT_LOAD_IMAGE = 1;

    String eventCloud, eventTitle,eventPrice, eventVenue, eventDate, eventDetails, eventImage, locImage;
    EditText evTitle, evVenue, evDate, evPrice, evDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);

        Random rand = new Random();
        int numAcc = rand.nextInt(9000000) + 1000000;
        eventImage = numAcc + ".png";
        locImage = "utick";
        newDate = (EditText) findViewById(R.id.new_date);
        btnPika = (Button) findViewById(R.id.btnPika);
        btnSubmt = (Button) findViewById(R.id.btnSubmit);
        btnPika.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Calendar calender = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(NewActivity.this,
                        mDatesetListener, calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH), calender
                        .get(Calendar.DAY_OF_MONTH));

                mDialog.show();
            }
        });

        btnSubmt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                evTitle = (EditText) findViewById(R.id.new_title);
                evVenue = (EditText) findViewById(R.id.new_venue);
                evDate = (EditText) findViewById(R.id.new_date);
                evPrice = (EditText) findViewById(R.id.new_price);
                evDetails = (EditText) findViewById(R.id.new_details);
                eventCloud = "0";
                eventTitle = evTitle.getText().toString();
                eventVenue = evVenue.getText().toString();
                eventDate = evDate.getText().toString();
                eventPrice = evPrice.getText().toString();
                eventDetails = evDetails.getText().toString();

                if(eventTitle.length() >= 3){
                    if(eventVenue.length() >= 3){
                        if(eventDate.length() >= 3){
                            if(eventPrice.length() >= 1){
                                new saveData().execute();
                            }else{
                                Toast.makeText(NewActivity.this, "Please enter valid event price.", Toast.LENGTH_LONG).show();
                                evPrice.setFocusable(true);
                            }
                        }else{
                            Toast.makeText(NewActivity.this, "Please enter valid event date.", Toast.LENGTH_LONG).show();
                            evDate.setFocusable(true);
                        }
                    }else{
                        Toast.makeText(NewActivity.this, "Please enter valid event venue.", Toast.LENGTH_LONG).show();
                        evVenue.setFocusable(true);
                    }
                }else{
                    Toast.makeText(NewActivity.this, "Please enter valid event title.", Toast.LENGTH_LONG).show();
                    evTitle.setFocusable(true);
                }
            }
        });

        ImageView imgFavorite = (ImageView) findViewById(R.id.new_image);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
    private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            arg2 = arg2 + 1;
            String my_date = String.valueOf(arg1).toString()  + "-" + String.valueOf(arg2).toString()  + "-" + String.valueOf(arg3).toString() ;
            newDate.setText(my_date);
        }
    };


    public void loadImage(){
        Bitmap bitmap = new ImageSaver(NewActivity.this).
                setFileName(eventImage).
                setDirectoryName(locImage).
                load();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.new_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            new ImageSaver(NewActivity.this).
                    setFileName(eventImage).
                    setDirectoryName(locImage).
                    save(BitmapFactory.decodeFile(picturePath));
            loadImage();
        }


    }


    private class saveData extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewActivity.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Creating new event...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            try {
                DBAdapter db = new DBAdapter(NewActivity.this);
                db.open();
                db.insertEvent(eventCloud,eventTitle,eventPrice,eventVenue,eventDate,eventDetails,eventImage);
                db.close();
            }
            catch (Exception ex){
                Toast.makeText(NewActivity.this, "Error creating event. Please try again later.", Toast.LENGTH_LONG).show();
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
