package com.jalvarez.bealert.functionalities.showearthquakes;

import com.jalvarez.bealert.base.BasePresenter;
import com.jalvarez.bealert.base.BaseView;
import com.jalvarez.bealert.data.earthquakes.Earthquake;

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

public class ShowEarthquakesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showEarthquakes(List<Earthquake> earthquakes);

        void showEarthquakeDetailsUi(Earthquake earthquake);

        void showNoEarthquakes();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadEarthquakes(Date startDate, Date endDate, boolean forceUpdate);

        void openEarthquakeDetails(Earthquake requestedEarthquake);

    }
}
