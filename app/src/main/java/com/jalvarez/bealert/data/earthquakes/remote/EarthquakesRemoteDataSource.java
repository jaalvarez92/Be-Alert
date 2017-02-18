package com.jalvarez.bealert.data.earthquakes.remote;


import android.content.Context;

import com.jalvarez.bealert.data.earthquakes.Earthquake;
import com.jalvarez.bealert.data.earthquakes.EarthquakesDataSource;

import java.util.Date;
import java.util.List;


/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class EarthquakesRemoteDataSource implements EarthquakesDataSource {

    private static EarthquakesRemoteDataSource INSTANCE;
    private EarthquakesApiHelper mEarthquakesApiHelper;

    public static EarthquakesRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new EarthquakesRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private EarthquakesRemoteDataSource(Context context) {
        mEarthquakesApiHelper = new EarthquakesApiHelper(context);
    }

    @Override
    public void getEarthquakes(Date startDate, Date endDate, final LoadEarthquakesCallback callback) {
        mEarthquakesApiHelper.getEarthquakes(startDate, endDate, new LoadEarthquakesCallback() {
            @Override
            public void onEarthquakesLoaded(List<Earthquake> earthquakes) {
                callback.onEarthquakesLoaded(earthquakes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllEarthquakes() { }

    @Override
    public void saveEarthquake(Earthquake earthquake) { }

    @Override
    public void refreshEarthquakes() { }


}
