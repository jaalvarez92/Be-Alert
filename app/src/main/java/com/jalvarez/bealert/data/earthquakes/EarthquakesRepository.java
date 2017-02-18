package com.jalvarez.bealert.data.earthquakes;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class EarthquakesRepository implements EarthquakesDataSource {

    private static EarthquakesRepository INSTANCE = null;

    private final EarthquakesDataSource mMoviesRemoteDataSource;
    private final EarthquakesDataSource mMoviesLocalDataSource;
    private Map<String, Earthquake> mCachedEarthquakes;
    private boolean mCacheIsDirty = false;

    private EarthquakesRepository(EarthquakesDataSource moviesRemoteDataSource, EarthquakesDataSource moviesLocalDataSource) {
        mMoviesRemoteDataSource = moviesRemoteDataSource;
        mMoviesLocalDataSource = moviesLocalDataSource;
    }

    public static EarthquakesRepository getInstance(EarthquakesDataSource moviesRemoteDataSource, EarthquakesDataSource moviesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EarthquakesRepository(moviesRemoteDataSource, moviesLocalDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getEarthquakes(final Date startDate, final Date endDate, final LoadEarthquakesCallback callback) {
        if (mCachedEarthquakes != null && !mCacheIsDirty) {
            callback.onEarthquakesLoaded(new ArrayList<>(mCachedEarthquakes.values()));
            return;
        }

        if (mCacheIsDirty) {
            getEarthquakesFromRemoteDataSource(startDate, endDate, new LoadEarthquakesCallback() {
                @Override
                public void onEarthquakesLoaded(List<Earthquake> earthquakes) {
                    callback.onEarthquakesLoaded(earthquakes);
                }

                @Override
                public void onDataNotAvailable() {
                    mMoviesLocalDataSource.getEarthquakes(startDate, endDate, callback);
                }
            });
        } else {
            mMoviesLocalDataSource.getEarthquakes(startDate, endDate, new LoadEarthquakesCallback() {
                @Override
                public void onEarthquakesLoaded(List<Earthquake> earthquakes) {
                    if (earthquakes.size() > 0) {
                        refreshCache(earthquakes);
                        callback.onEarthquakesLoaded(new ArrayList<>(mCachedEarthquakes.values()));
                    }
                    else{
                        onDataNotAvailable();
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    getEarthquakesFromRemoteDataSource(startDate, endDate, callback);
                }
            });
        }
    }


    @Override
    public void deleteAllEarthquakes() {

    }

    @Override
    public void saveEarthquake(Earthquake earthquake) {

    }

    @Override
    public void refreshEarthquakes() {
        mCacheIsDirty = true;
    }

    private void getEarthquakesFromRemoteDataSource(Date startDate, Date endDate, final LoadEarthquakesCallback callback) {
        mMoviesRemoteDataSource.getEarthquakes(startDate, endDate, new LoadEarthquakesCallback() {
            @Override
            public void onEarthquakesLoaded(List<Earthquake> earthquakes) {
                refreshCache(earthquakes);
                refreshLocalDataSource(earthquakes);
                callback.onEarthquakesLoaded(new ArrayList<>(mCachedEarthquakes.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Earthquake> earthquakes) {
        if (mCachedEarthquakes == null) {
            mCachedEarthquakes = new LinkedHashMap<>();
        }
        mCachedEarthquakes.clear();
        for (Earthquake earthquake : earthquakes) {
            mCachedEarthquakes.put(earthquake.getId(), earthquake);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Earthquake> earthquakes) {
        mMoviesLocalDataSource.deleteAllEarthquakes();
        for (Earthquake earthquake : earthquakes) {
            mMoviesLocalDataSource.saveEarthquake(earthquake);
        }
    }

}
