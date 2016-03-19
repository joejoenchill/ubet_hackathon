package com.videtechs.mobile.utick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "ubetUTick";
    static final int DATABASE_VERSION = 1;
    static final String TAG_ID = "1";

    static final String NAVI_TABLE = "Naviz";
    static final String NAVI_ID = "Id";
    static final String NAVI_REGION = "Region";
    static final String NAVI_USER = "User";
    static final String NAVI_EVENT = "Event";
    static final String NAVI_PAY = "Pay";

    static final String REGIONS_TABLE = "Regions";
    static final String REGIONS_ID = "Id";
    static final String REGIONS_CLOUD = "cloud";
    static final String REGIONS_TITLE = "Title";

    static final String USERS_TABLE = "Users";
    static final String USERS_ID = "Id";
    static final String USERS_CLOUD = "cloud";
    static final String USERS_REGID = "RegId";
    static final String USERS_NAME = "Name";
    static final String USERS_EMAIL = "Email";
    static final String USERS_PHONE = "Phone";
    static final String USERS_ACCOUNT = "Account";
    static final String USERS_PIN = "Pin";

    static final String EVENTS_TABLE = "Events";
    static final String EVENTS_ID = "Id";
    static final String EVENTS_CLOUD = "Cloud";
    static final String EVENTS_TITLE = "Title";
    static final String EVENTS_PRICE = "Price";
    static final String EVENTS_DATE = "Date";
    static final String EVENTS_VENUE = "Venue";
    static final String EVENTS_DETAILS = "Details";
    static final String EVENTS_IMAGE = "Image";

    static final String NAVI_CREATE =
            "CREATE TABLE " + NAVI_TABLE + " (" +
                    NAVI_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    NAVI_REGION + " INTEGER NOT NULL, " +
                    NAVI_USER + " INTEGER NOT NULL, " +
                    NAVI_EVENT + " INTEGER NOT NULL, " +
                    NAVI_PAY + " VARCHAR(10) NOT NULL);";

    static final String REGIONS_CREATE =
            "CREATE TABLE " + REGIONS_TABLE + " (" +
                    REGIONS_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    REGIONS_CLOUD + " INTEGER NOT NULL, " +
                    REGIONS_TITLE + " VARCHAR(80) NOT NULL);";

    static final String USERS_CREATE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    USERS_CLOUD + " INTEGER NOT NULL, " +
                    USERS_REGID + " INTEGER NOT NULL, " +
                    USERS_NAME + " VARCHAR(50) NOT NULL, " +
                    USERS_EMAIL + " VARCHAR(80) NOT NULL, " +
                    USERS_PHONE + " VARCHAR(32) NOT NULL, " +
                    USERS_ACCOUNT + " VARCHAR(25) NOT NULL, " +
                    USERS_PIN + " VARCHAR(10) NOT NULL);";

    static final String EVENTS_CREATE =
            "CREATE TABLE " + EVENTS_TABLE + " (" +
                    EVENTS_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    EVENTS_CLOUD + " INTEGER NOT NULL, " +
                    EVENTS_TITLE + " VARCHAR(256) NOT NULL, " +
                    EVENTS_PRICE + " VARCHAR(15) NOT NULL, " +
                    EVENTS_DATE + " VARCHAR(25) NOT NULL, " +
                    EVENTS_VENUE + " VARCHAR(256) NOT NULL, " +
                    EVENTS_DETAILS + " TEXT NOT NULL,  "+
                    EVENTS_IMAGE + " TEXT NOT NULL);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(NAVI_CREATE);
                db.execSQL(REGIONS_CREATE);
                db.execSQL(USERS_CREATE);
                db.execSQL(EVENTS_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    /*************************************************************************************/
    public void resetNavi(){
        db.execSQL("DROP TABLE IF EXISTS " + NAVI_TABLE);
        db.execSQL(NAVI_CREATE);
    }

    public int countNavi() {
        String countQuery = "SELECT  * FROM " + NAVI_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public long insertNavi(String region, String user, String event, String pay)
    {
        ContentValues initialValues = new  ContentValues();
        initialValues.put(NAVI_REGION, region);
        initialValues.put(NAVI_USER, user);
        initialValues.put(NAVI_EVENT, event);
        initialValues.put(NAVI_PAY, pay);
        return db.insert(NAVI_TABLE, null, initialValues);
    }

    public Cursor getNaviRegion(String naviId) throws SQLException
    {
        Cursor mCursor = db.query(true, NAVI_TABLE, new String[] {NAVI_REGION}, NAVI_ID + "=" + naviId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean editNaviRegion(int naviId, String regId)
    {
        ContentValues args = new ContentValues();
        args.put(NAVI_REGION, regId);
        return db.update(NAVI_TABLE, args, NAVI_ID + "=" + naviId, null) > 0;

    }

    public Cursor getNaviUser(String naviId) throws SQLException
    {
        Cursor mCursor = db.query(true, NAVI_TABLE, new String[] {NAVI_USER}, NAVI_ID + "=" + naviId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean editNaviUser(int naviId, String useId)
    {
        ContentValues args = new ContentValues();
        args.put(NAVI_USER, useId);
        return db.update(NAVI_TABLE, args, NAVI_ID + "=" + naviId, null) > 0;

    }

    public Cursor getNaviEvent(String naviId) throws SQLException
    {
        Cursor mCursor = db.query(true, NAVI_TABLE, new String[] {NAVI_EVENT}, NAVI_ID + "=" + naviId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean editNaviEvent(int naviId, String eventId)
    {
        ContentValues args = new ContentValues();
        args.put(NAVI_EVENT, eventId);
        return db.update(NAVI_TABLE, args, NAVI_ID + "=" + naviId, null) > 0;

    }

    public Cursor getNaviPay(String naviId) throws SQLException
    {
        Cursor mCursor = db.query(true, NAVI_TABLE, new String[] {NAVI_PAY}, NAVI_ID + "=" + naviId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean editNaviPay(int naviId, String pay)
    {
        ContentValues args = new ContentValues();
        args.put(NAVI_PAY, pay);
        return db.update(NAVI_TABLE, args, NAVI_ID + "=" + naviId, null) > 0;

    }


    /*************************************************************************************/
    public void resetRegions(){
        db.execSQL("DROP TABLE IF EXISTS " + REGIONS_TABLE);
        db.execSQL(REGIONS_CREATE);
    }

    public int countRegions() {
        String countQuery = "SELECT  * FROM " + REGIONS_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public long insertRegion(String cloud, String title)
    {
        ContentValues initialValues = new  ContentValues();
        initialValues.put(REGIONS_CLOUD, cloud);
        initialValues.put(REGIONS_TITLE, title);

        return db.insert(REGIONS_TABLE, null, initialValues);
    }

    public Cursor getRegions()
    {
        return db.query(REGIONS_TABLE, new String[]{REGIONS_ID, REGIONS_CLOUD, REGIONS_TITLE}, null, null, null, null, REGIONS_TITLE);
    }

    public Cursor getRegion(String rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, REGIONS_TABLE, new String[] {REGIONS_CLOUD, REGIONS_TITLE},
                REGIONS_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /*************************************************************************************/
    public void resetUsers(){
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL(USERS_CREATE);
    }

    public int countUsers() {
        String countQuery = "SELECT  * FROM " + USERS_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public long insertUser(String cloud, String regid, String name, String email, String phone, String account, String pin)
    {
        ContentValues initialValues = new  ContentValues();
        initialValues.put(USERS_CLOUD, cloud);
        initialValues.put(USERS_REGID, regid);
        initialValues.put(USERS_NAME, name);
        initialValues.put(USERS_EMAIL, email);
        initialValues.put(USERS_PHONE, phone);
        initialValues.put(USERS_ACCOUNT, account);
        initialValues.put(USERS_PIN, pin);

        return db.insert(USERS_TABLE, null, initialValues);
    }

    public Cursor getUsers()
    {
        return db.query(USERS_TABLE, new String[]{USERS_ID, USERS_CLOUD, USERS_REGID, USERS_NAME, USERS_EMAIL, USERS_PHONE,
                USERS_ACCOUNT, USERS_PIN}, null, null, null, null, USERS_NAME);
    }

    public Cursor getUser(String rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, USERS_TABLE, new String[] {USERS_CLOUD, USERS_REGID, USERS_NAME, USERS_EMAIL, USERS_PHONE,
                USERS_ACCOUNT, USERS_PIN}, USERS_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public int countUserByPhone(String phone) throws SQLException
    {
        Cursor mCursor = db.query(true, USERS_TABLE, new String[]{USERS_CLOUD, USERS_REGID, USERS_NAME, USERS_EMAIL, USERS_PHONE},
                USERS_PHONE + "=" + phone, null, null, null, null, null);
        int userCount = mCursor.getCount();
        mCursor.close();
        return userCount;
    }

    public boolean editUserCloud(int userId, String cloud)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_CLOUD, cloud);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;

    }

    public boolean editUserName(int userId, String name)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_NAME, name);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;

    }

    public boolean editUserPhone(int userId, String phone)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_PHONE, phone);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;

    }

    public boolean editUserEmail(int userId, String email)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_EMAIL, email);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;

    }

    public boolean editUserRegion(int userId, String regid)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_REGID, regid);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;
    }

    public boolean editUserAccount(int userId, String account)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_ACCOUNT, account);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;
    }

    public boolean editUserPin(int userId, String pin)
    {
        ContentValues args = new ContentValues();
        args.put(USERS_PIN, pin);
        return db.update(USERS_TABLE, args, USERS_ID + "=" + userId, null) > 0;
    }

    /*************************************************************************************/
    public void resetEvents(){
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE);
        db.execSQL(EVENTS_CREATE);
    }

    public int countEvents() {
        String countQuery = "SELECT  * FROM " + EVENTS_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public long insertEvent(String cloud, String title, String price, String venue, String date, String details, String image)
    {
        ContentValues initialValues = new  ContentValues();
        initialValues.put(EVENTS_CLOUD, cloud);
        initialValues.put(EVENTS_TITLE, title);
        initialValues.put(EVENTS_PRICE, price);
        initialValues.put(EVENTS_DATE, date);
        initialValues.put(EVENTS_VENUE, venue);
        initialValues.put(EVENTS_DETAILS, details);
        initialValues.put(EVENTS_IMAGE, image);

        return db.insert(EVENTS_TABLE, null, initialValues);
    }

    public Cursor getEvents()
    {
        return db.query(EVENTS_TABLE, new String[]{EVENTS_ID, EVENTS_CLOUD, EVENTS_TITLE, EVENTS_PRICE,
                EVENTS_DATE, EVENTS_VENUE, EVENTS_DETAILS, EVENTS_IMAGE}, null, null, null, null, EVENTS_ID + " DESC");
    }

    public Cursor getEvent(String rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, EVENTS_TABLE, new String[] {EVENTS_CLOUD, EVENTS_TITLE, EVENTS_PRICE,
                        EVENTS_DATE, EVENTS_VENUE, EVENTS_DETAILS, EVENTS_IMAGE}, EVENTS_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean editEventCloud(int eventId, String cloud)
    {
        ContentValues args = new ContentValues();
        args.put(EVENTS_CLOUD, cloud);
        return db.update(EVENTS_TABLE, args, EVENTS_ID + "=" + eventId, null) > 0;

    }

}
