package com.jalvarez.bealert.functionalities.selectdatestoshow;

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

class SelectDatesToShowContract {

    interface View extends BaseView<Presenter> {

        void openShowEarthquakes(Date startDate, Date endDate);

        void showError();

    }

    interface Presenter extends BasePresenter {

        void openShowEarthquakes(Date startDate, Date endDate);

    }
}
