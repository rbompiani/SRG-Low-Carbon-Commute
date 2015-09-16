package com.rbompiani.srglowcarboncommute;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by bompi_000 on 8/23/2015.
 */
public class SRGLowCarbonCommuteApplication extends Application{
    @Override
    public void onCreate(){
        // Enable Local Datastore.
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZvtJdXDNTbc24vrGGXsSgbtJGC7ZNy4mlVhLdz93", "STJAfxeb85Qkygn1WNsHCuXYxCNQUQgmWyklxgtA");
    }
}
