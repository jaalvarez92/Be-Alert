package com.jalvarez.bealert.functionalities.selectdatestoshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jalvarez.bealert.R;
import com.jalvarez.bealert.data.earthquakes.EarthquakesRepository;
import com.jalvarez.bealert.data.earthquakes.local.EarthquakesLocalDataSource;
import com.jalvarez.bealert.data.earthquakes.remote.EarthquakesRemoteDataSource;
import com.jalvarez.bealert.functionalities.showearthquakes.ShowEarthquakesActivity;
import com.jalvarez.bealert.functionalities.showearthquakes.ShowEarthquakesFragment;
import com.jalvarez.bealert.functionalities.showearthquakes.ShowEarthquakesPresenter;
import com.jalvarez.bealert.util.ActivityUtils;

public class SelectDatesToShowActivity extends AppCompatActivity {

    // region Activity Lifecycle Events
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dates_to_show);
        SelectDatesToShowFragment view = SelectDatesToShowFragment.newInstance();
        SelectDatesToShowPresenter presenter = new SelectDatesToShowPresenter(view);
        view.setPresenter(presenter);
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), view, R.id.container);
    }
    // endregion
}
