package com.rbompiani.srglowcarboncommute.data;

import java.util.Date;

/**
 * Created by bompi_000 on 9/7/2015.
 */
public class Trips {
    private int mRiderId;
    private double mDistance;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getRiderId() {
        return mRiderId;
    }

    public void setRiderId(int riderId) {
        mRiderId = riderId;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    private Date mDate;
}
