package com.rbompiani.srglowcarboncommute.data;

/**
 * Created by bompi_000 on 9/7/2015.
 */
public class Rider {
    private String mRiderId;
    private String mFirstName;
    private String mLastName;
    private double mMiles;
    private int mTrips;
    private String mCommute;
    private String mOffice;


    public String getRiderId() {
        return mRiderId;
    }

    public void setRiderId(String riderId) {
        mRiderId = riderId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        String capName = firstName.substring(0, 1).toUpperCase()+firstName.substring(1);
        mFirstName = capName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        String capName = lastName.substring(0, 1).toUpperCase()+lastName.substring(1);
        mLastName = capName;
    }

    public double getMiles() {
        return mMiles;
    }

    public void setMiles(double miles) {
        mMiles = miles;
    }

    public int getTrips() {
        return mTrips;
    }

    public void setTrips(int trips) {
        mTrips = trips;
    }

    public String getCommute() {
        return mCommute;
    }

    public void setCommute(String commute) {
        mCommute = commute;
    }

    public String getOffice() {
        return mOffice;
    }

    public void setOffice(String office) {
        mOffice = office;
    }
}
