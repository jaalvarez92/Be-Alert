package com.jalvarez.bealert.functionalities.showearthquakes;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jalvarez.bealert.R;
import com.jalvarez.bealert.data.earthquakes.Earthquake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class ShowEarthquakesFragment extends Fragment implements ShowEarthquakesContract.View {


    // region Constants
    private static final String ARG_PARAM_START_DATE = "start_date";
    private static final String ARG_PARAM_END_DATE = "end_date";
    // endregion

    // region Fields
    private Date mParamStartDate;
    private Date mParamEndDate;
    private ShowEarthquakesContract.Presenter mPresenter;
    private EarthquakesAdapter mListAdapter;
    private View noEarthquakesView;
    private EarthquakeItemListener mListener;
    // endregion

    // region Constructors
    public ShowEarthquakesFragment() {
    }

    public static ShowEarthquakesFragment newInstance(Date startDate, Date endDate) {
        ShowEarthquakesFragment fragment = new ShowEarthquakesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM_START_DATE, startDate.getTime());
        args.putLong(ARG_PARAM_END_DATE, endDate.getTime());
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Fragment Lifecycle Events
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamStartDate = new Date(getArguments().getLong(ARG_PARAM_START_DATE));
            mParamEndDate = new Date(getArguments().getLong(ARG_PARAM_END_DATE));
        }
        mListAdapter = new EarthquakesAdapter(getContext(), new ArrayList<Earthquake>(0), mItemListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.loadEarthquakes(mParamStartDate, mParamEndDate, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);

        RecyclerView listView = (RecyclerView) root.findViewById(R.id.earthquake_list);
        listView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(mListAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new android.support.v7.widget.DividerItemDecoration(getActivity(), android.support.v7.widget.DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        noEarthquakesView = root.findViewById(R.id.no_earthquakes);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EarthquakeItemListener) {
            mListener = (EarthquakeItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EarthquakeItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // endregion


    // region Contract View Methods

    @Override
    public void setPresenter(ShowEarthquakesContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        final ProgressBar progressBar =
                (ProgressBar) getView().findViewById(R.id.progress_bar);

        if (active)
            progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        else
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
    }

    @Override
    public void showEarthquakes(List<Earthquake> earthquakes) {
        mListAdapter.replaceData(earthquakes);
        noEarthquakesView.setVisibility(View.GONE);
    }

    @Override
    public void showEarthquakeDetailsUi(Earthquake earthquake) {
        mListener.onEarthquakeClick(earthquake);
    }

    @Override
    public void showNoEarthquakes() {
        noEarthquakesView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    // endregion



    private static class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakesAdapter.ViewHolder> {

        private  Context mContext;
        private List<Earthquake> mEarthquakes;
        private EarthquakeItemListener mItemListener;

        EarthquakesAdapter(Context context, List<Earthquake> earthquakes, EarthquakeItemListener itemListener) {
            mContext = context;
            setList(earthquakes);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.earthquake_item, parent, false);
            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mMagnitudeTextView.setVisibility(View.INVISIBLE);
            holder.mMagnitudeImageView.setVisibility(View.INVISIBLE);
            holder.mLocationTextView.setVisibility(View.INVISIBLE);
            animateItem(holder);

            final Earthquake earthquake = mEarthquakes.get(position);
            holder.mLocationTextView.setText(earthquake.getLocation());
            holder.mMagnitudeTextView.setText(String.format("%s", earthquake.getMagnitude()));

            if (earthquake.getMagnitude() > 4.5) {
                holder.mMagnitudeImageView.setImageResource(R.drawable.circle_red);
            }
            else {
                holder.mMagnitudeImageView.setImageResource(R.drawable.circle_green);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onEarthquakeClick(earthquake);
                }
            });
        }

        private void animateItem(final ViewHolder holder) {
            holder.itemView.setTranslationX(mContext.getResources().getDimension(R.dimen.item_width)*-5);
            holder.itemView.animate()
                    .translationX(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.mMagnitudeTextView.setVisibility(View.VISIBLE);
                            holder.mMagnitudeImageView.setVisibility(View.VISIBLE);
                            holder.mLocationTextView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }

        @Override
        public int getItemCount() {
            return mEarthquakes.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView mLocationTextView;
            TextView mMagnitudeTextView;
            ImageView mMagnitudeImageView;


            ViewHolder(View v) {
                super(v);
                mLocationTextView = (TextView) v.findViewById(R.id.location);
                mMagnitudeTextView = (TextView) v.findViewById(R.id.magnitudeText);
                mMagnitudeImageView = (ImageView) v.findViewById(R.id.magnitudeImage);
            }

        }



        void replaceData(List<Earthquake> earthquakes) {
            setList(earthquakes);
            notifyDataSetChanged();
        }

        private void setList(List<Earthquake> movies) {
            mEarthquakes = checkNotNull(movies);
        }
    }

    public interface EarthquakeItemListener {

        void onEarthquakeClick(Earthquake clickedEarthquake);
    }

    /**
     * Listener for clicks on earthquakes in the ListView.
     */
    EarthquakeItemListener mItemListener = new EarthquakeItemListener(){
        @Override
        public void onEarthquakeClick(Earthquake clickedEarthquake) {
            mPresenter.openEarthquakeDetails(clickedEarthquake);
        }
    };

}
