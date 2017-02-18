package com.jalvarez.bealert.functionalities.selectdatestoshow;

import java.util.Date;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project BeAlert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

class SelectDatesToShowPresenter implements SelectDatesToShowContract.Presenter {

    private final SelectDatesToShowContract.View mView;

    SelectDatesToShowPresenter(SelectDatesToShowContract.View view){
        mView = view;
    }

    @Override
    public void openShowEarthquakes(Date startDate, Date endDate) {

        if (startDate.after(endDate)){
            mView.showError();
        }
        else{
            mView.openShowEarthquakes(startDate, endDate);
        }

    }

    @Override
    public void start() {

    }
}
