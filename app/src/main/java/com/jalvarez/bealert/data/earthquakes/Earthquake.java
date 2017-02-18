package com.jalvarez.bealert.data.earthquakes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project BeAlert
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class Earthquake implements Parcelable {

    private String id;
    private double latitude;
    private double longitude;
    private double magnitude;
    private String location;


    public Earthquake() {
    }

    public Earthquake(String id, double latitude, double longitude, double magnitude, String location){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.location = location;
    }

    public Earthquake(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            JSONArray coordinates = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            JSONObject properties = jsonObject.getJSONObject("properties");
            this.latitude = coordinates.getDouble(1);
            this.longitude = coordinates.getDouble(0);
            this.magnitude = properties.getDouble("mag");
            this.location = properties.getString("place");
        }
        catch (JSONException ignored) { }
    }


    public String getId() {
        return id;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getMagnitude(){
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Earthquake earthquake = (Earthquake) object;
        return Objects.equal(id, earthquake.id) &&
                Objects.equal(latitude, earthquake.latitude) &&
                Objects.equal(longitude, earthquake.longitude) &&
                Objects.equal(magnitude, earthquake.magnitude);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Earthquake at location " + location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(magnitude);
        dest.writeString(location);
    }

    public static final Parcelable.Creator<Earthquake> CREATOR
            = new Parcelable.Creator<Earthquake>() {
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    private Earthquake(Parcel in) {
        this.id = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.magnitude = in.readDouble();
        this.location = in.readString();
    }
}
