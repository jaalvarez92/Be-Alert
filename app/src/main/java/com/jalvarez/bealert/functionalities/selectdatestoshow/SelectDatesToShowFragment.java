package com.jalvarez.bealert.functionalities.selectdatestoshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.jalvarez.bealert.R;
import com.jalvarez.bealert.functionalities.showearthquakes.ShowEarthquakesActivity;
import com.jalvarez.bealert.util.DateUtils;

import java.util.Date;


public class SelectDatesToShowFragment extends Fragment implements SelectDatesToShowContract.View {

    private SelectDatesToShowContract.Presenter mPresenter;
    private DatePicker mStartDatePicker;
    private DatePicker mEndDatePicker;

    // region Constructors
    public SelectDatesToShowFragment() {

    }

    public static SelectDatesToShowFragment newInstance() {
        SelectDatesToShowFragment fragment = new SelectDatesToShowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // endregion

    // region Fragment Lifecycle Events

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_dates_to_show, container, false);
        mStartDatePicker = (DatePicker) view.findViewById(R.id.start_date);
        mEndDatePicker = (DatePicker) view.findViewById(R.id.end_date);
        Button searchButton = (Button) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateUtils dateUtils = new DateUtils();
                mPresenter.openShowEarthquakes(dateUtils.getDateFromDatePicker(mStartDatePicker), dateUtils.getDateFromDatePicker(mEndDatePicker));
            }
        });
        return view;
    }

    // endregion


    // region View Contract Methods

    @Override
    public void setPresenter(SelectDatesToShowContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void openShowEarthquakes(Date startDate, Date endDate) {
        Intent intent = new Intent(getActivity(), ShowEarthquakesActivity.class);
        intent.putExtra(ShowEarthquakesActivity.ARG_PARAM_START_DATE, startDate.getTime());
        intent.putExtra(ShowEarthquakesActivity.ARG_PARAM_END_DATE, endDate.getTime());
        startActivity(intent);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),"You have entered dates with an error", Toast.LENGTH_LONG).show();
    }


    // endregion


}
