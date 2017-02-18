package com.jalvarez.bealert.functionalities.showearthquakes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalvarez.bealert.R;
import com.jalvarez.bealert.data.earthquakes.Earthquake;
import com.jalvarez.bealert.data.earthquakes.EarthquakesRepository;
import com.jalvarez.bealert.data.earthquakes.local.EarthquakesLocalDataSource;
import com.jalvarez.bealert.data.earthquakes.remote.EarthquakesRemoteDataSource;
import com.jalvarez.bealert.util.ActivityUtils;

import java.util.Date;

public class ShowEarthquakesActivity extends AppCompatActivity implements ShowEarthquakesFragment.EarthquakeItemListener, OnMapReadyCallback {

    // region Constants
    public static final String ARG_PARAM_START_DATE = "start_date";
    public static final String ARG_PARAM_END_DATE = "end_date";
    //endregion

    // region Fields
    private Date mParamStartDate;
    private Date mParamEndDate;
    private boolean mTwoPane;
    private GoogleMap mMap;
    private ViewPager mViewPager;
    private ShowEarthquakesPresenter mPresenter;
    private ShowEarthquakesFragment mView;
    // endregion

    // region Android Lifecycle Events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mParamStartDate = new Date(getIntent().getLongExtra(ARG_PARAM_START_DATE, 0));
        mParamEndDate = new Date(getIntent().getLongExtra(ARG_PARAM_END_DATE, 0));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_earthquakes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.tab_layout) == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mTwoPane = true;
            mView = ShowEarthquakesFragment.newInstance(mParamStartDate, mParamEndDate);
            mPresenter = new ShowEarthquakesPresenter(EarthquakesRepository.getInstance(EarthquakesRemoteDataSource.getInstance(ShowEarthquakesActivity.this), EarthquakesLocalDataSource.getInstance(ShowEarthquakesActivity.this)), mView);
            mView.setPresenter(mPresenter);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mView, R.id.earthquake_list_fragment_container);
        }
        else{

            mViewPager = (ViewPager) findViewById(R.id.view_pager);
            CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);
        }
    }

    // endregion

    // region Interface Implementations

    @Override
    public void onEarthquakeClick(Earthquake earthquake) {
        LatLng earthquakeLatLon = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(earthquakeLatLon).title(earthquake.getLocation()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(earthquakeLatLon));
        if (!mTwoPane){
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    // endregion

    public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private final int PAGE_COUNT = 2;
        private final String tabTitles[] = new String[] { "List", "Map"};

        CustomFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mView = ShowEarthquakesFragment.newInstance(mParamStartDate, mParamEndDate);
                    mPresenter = new ShowEarthquakesPresenter(EarthquakesRepository.getInstance(EarthquakesRemoteDataSource.getInstance(ShowEarthquakesActivity.this), EarthquakesLocalDataSource.getInstance(ShowEarthquakesActivity.this)), mView);
                    mView.setPresenter(mPresenter);
                    return mView;
                case 1:
                    SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                    mapFragment.getMapAsync(ShowEarthquakesActivity.this);
                    return mapFragment;
                default:
                    return ShowEarthquakesFragment.newInstance(mParamStartDate, mParamEndDate);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
