package com.jalvarez.bealert.data.earthquakes;

import java.util.Date;
import java.util.List;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public interface EarthquakesDataSource {

    interface LoadEarthquakesCallback {

        void onEarthquakesLoaded(List<Earthquake> earthquakes);

        void onDataNotAvailable();
    }

    void getEarthquakes(Date startDate, Date endDate, LoadEarthquakesCallback callback);

    void deleteAllEarthquakes();

    void saveEarthquake(Earthquake movie);

    void refreshEarthquakes();


}
