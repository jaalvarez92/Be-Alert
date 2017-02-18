package com.jalvarez.bealert.data.earthquakes.local;

import android.content.Context;

import com.jalvarez.bealert.data.earthquakes.Earthquake;
import com.jalvarez.bealert.data.earthquakes.EarthquakesDataSource;

import java.util.Date;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class EarthquakesLocalDataSource implements EarthquakesDataSource {

    private static EarthquakesLocalDataSource INSTANCE;
    private EarthquakesDatabaseHelper mHelper;

    private EarthquakesLocalDataSource(Context context){
        mHelper = EarthquakesDatabaseHelper.getInstance(context);
    }

    public static EarthquakesLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new EarthquakesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getEarthquakes(Date startDate, Date endDate, LoadEarthquakesCallback callback) {
        try {
            callback.onEarthquakesLoaded(mHelper.getAllEarthquakes());
        }
        catch (Exception ex){
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void deleteAllEarthquakes() {
        mHelper.deleteAllEarthquakes();
    }

    @Override
    public void saveEarthquake(final Earthquake earthquake) {
        mHelper.addEarthquake(earthquake);
    }

    @Override
    public void refreshEarthquakes() { }

}
