package com.jalvarez.bealert.data.earthquakes.remote;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.jalvarez.bealert.data.earthquakes.Earthquake;
import com.jalvarez.bealert.data.earthquakes.EarthquakesDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project Be Alert
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

class EarthquakesApiHelper {

    private final String API_GET_EARTHQUAKES_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s";

    private RequestQueue queue;

    EarthquakesApiHelper(Context context){
        queue = Volley.newRequestQueue(context);
    }

    void getEarthquakes(Date startDate, Date endDate, final EarthquakesDataSource.LoadEarthquakesCallback callback) {
        final Date today = new Date();
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, String.format(API_GET_EARTHQUAKES_URL, format.format(startDate), format.format(endDate)),
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Earthquake> earthquakesResult = new ArrayList<>();

                        try {
                            JSONArray earthquakes = response.getJSONArray("features");
                            int numEarthquakes = earthquakes.length();
                            for (int numEarthquake = 0; numEarthquake < numEarthquakes; numEarthquake++) {
                                earthquakesResult.add(new Earthquake(earthquakes.getJSONObject(numEarthquake)));
                            }
                            callback.onEarthquakesLoaded(earthquakesResult);
                        } catch (JSONException e) {
                            callback.onDataNotAvailable();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onDataNotAvailable();
            }
        });

        queue.add(jsonRequest);
    }

}
