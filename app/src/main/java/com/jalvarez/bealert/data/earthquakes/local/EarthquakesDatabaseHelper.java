package com.jalvarez.bealert.data.earthquakes.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jalvarez.bealert.data.earthquakes.Earthquake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project BeAlert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class EarthquakesDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "earthquakesDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_EARTHQUAKES = "earthquakes";


    // Earthquake Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_MAGNITUDE = "magnitude";
    private static final String KEY_LOCATION = "location";


    private static EarthquakesDatabaseHelper mInstance;


    public static synchronized EarthquakesDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EarthquakesDatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private EarthquakesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
//        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_EARTHQUAKES +
                "(" +
                KEY_ID + " TEXT," +
                KEY_LATITUDE + " DOUBLE ," +
                KEY_LONGITUDE + " DOUBLE ," +
                KEY_MAGNITUDE + " DOUBLE ," +
                KEY_LOCATION + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EARTHQUAKES);
            onCreate(db);
        }
    }




    // Insert a earthquake into the database
    public void addEarthquake(Earthquake earthquake) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, earthquake.getId());
            values.put(KEY_LATITUDE, earthquake.getLatitude());
            values.put(KEY_LONGITUDE, earthquake.getLongitude());
            values.put(KEY_MAGNITUDE, earthquake.getMagnitude());
            values.put(KEY_LOCATION, earthquake.getLocation());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_EARTHQUAKES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }


    public List<Earthquake> getAllEarthquakes() {
        List<Earthquake> earthquakes = new ArrayList<>();

        String EARTHQUAKES_SELECT_QUERY = String.format("SELECT * FROM %s ", TABLE_EARTHQUAKES);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(EARTHQUAKES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Earthquake newEarthquake = new Earthquake();
                    newEarthquake.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                    newEarthquake.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)));
                    newEarthquake.setLongitude(cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)));
                    newEarthquake.setMagnitude(cursor.getDouble(cursor.getColumnIndex(KEY_MAGNITUDE)));
                    newEarthquake.setLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
                    earthquakes.add(newEarthquake);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return earthquakes;
    }


    public void deleteAllEarthquakes() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_EARTHQUAKES, null, null);
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
    }
}