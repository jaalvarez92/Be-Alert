package com.jalvarez.bealert.functionalities.showearthquakes;

import com.jalvarez.bealert.data.earthquakes.Earthquake;
import com.jalvarez.bealert.data.earthquakes.EarthquakesRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class ShowEarthquakesPresenter implements ShowEarthquakesContract.Presenter {

    // region Fields
    private final EarthquakesRepository mEarthquakesRepository;
    private final ShowEarthquakesContract.View mShowEarthquakesView;
    private boolean mFirstLoad = true;
    // endregion


    // region Constructor
    public ShowEarthquakesPresenter(EarthquakesRepository earthquakesRepository, ShowEarthquakesContract.View showEarthquakesView) {
        mEarthquakesRepository = earthquakesRepository;
        mShowEarthquakesView = showEarthquakesView;
        mShowEarthquakesView.setPresenter(this);
    }
    // endregion


    // region Contract Presenter Methods
    @Override
    public void start() {

    }

    @Override
    public void loadEarthquakes(Date startDate, Date endDate, boolean forceUpdate) {
        loadEarthquakes(startDate, endDate, forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void openEarthquakeDetails(Earthquake requestedEarthquake) {
        mShowEarthquakesView.showEarthquakeDetailsUi(requestedEarthquake);
    }

    // endregion

    // region Private Methods
    private void loadEarthquakes(Date startDate, Date endDate,boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mShowEarthquakesView.setLoadingIndicator(true);
        }

        if (forceUpdate) {
            mEarthquakesRepository.refreshEarthquakes();
        }

        mEarthquakesRepository.getEarthquakes(startDate, endDate, new EarthquakesRepository.LoadEarthquakesCallback() {
            @Override
            public void onEarthquakesLoaded(List<Earthquake> earthquakes) {
                if (mShowEarthquakesView.isActive()){
                    processEarthquakes(earthquakes);
                    if (showLoadingUI) {
                        mShowEarthquakesView.setLoadingIndicator(false);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mShowEarthquakesView.isActive()) {
                    processEmptyEarthquakes();
                    if (showLoadingUI) {
                        mShowEarthquakesView.setLoadingIndicator(false);
                    }
                }
            }
        });

    }


    private void processEarthquakes(List<Earthquake> earthquakes) {
        if (earthquakes.isEmpty()) {
            processEmptyEarthquakes();
        } else {
            mShowEarthquakesView.showEarthquakes(earthquakes);
        }
    }



    private void processEmptyEarthquakes() {
        mShowEarthquakesView.showNoEarthquakes();
    }

    // endregion



}
