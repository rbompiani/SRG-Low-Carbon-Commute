package com.rbompiani.srglowcarboncommute;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OfficeStatsActivity extends ActionBarActivity {

    @Bind(R.id.thisYearMiles)TextView mTotalMiles;
    @Bind(R.id.portland_total_miles)TextView mPortlandMiles;
    @Bind(R.id.seattle_total_miles)TextView mSeattleMiles;
    public int mYear = Calendar.getInstance().get(Calendar.YEAR);
    public String mThisYear ="01/01/"+mYear;
    public Date mNewDate = Calendar.getInstance().getTime();
    public Double mPortlandTotal;
    public Double mSeattleTotal;
    public DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_stats);

        ButterKnife.bind(this);

        getTotalMiles();
        getPortlandMiles();
        getSeattleMiles();



    }

    private void getYear(){
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");

        try {
            mNewDate = outputFormatter.parse(mThisYear);
        } catch (java.text.ParseException e1) {
            e1.printStackTrace();
        }
        return;
    }

    private void getTotalMiles() {
        getYear();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereGreaterThanOrEqualTo(ParseConstants.KEY_DATE, mNewDate);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commuteList, ParseException e) {
                Double total = 0.0;
                for (ParseObject tripsObject : commuteList) {
                    total += tripsObject.getDouble("miles");
                }
                mTotalMiles.setText(total + " mi");
            }
        });

    }

    private void getPortlandMiles(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("office", "Portland");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    mPortlandTotal = 0.0;
                    String testInt;
                    for (ParseObject tripsObject : objects) {
                        testInt = tripsObject.getObjectId();
                        Log.i("These Are Portlanders", testInt);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
                        query.whereEqualTo(ParseConstants.KEY_COMMUTER_ID, testInt);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> commuteList, ParseException e) {
                                for (ParseObject tripsObject : commuteList) {
                                    mPortlandTotal += tripsObject.getDouble("miles");
                                    Log.i("These Are Total Miles", mPortlandTotal.toString());
                                }
                                mPortlandMiles.setText(df.format(mPortlandTotal) + " mi");
                            }
                        });

                    }
                    // The query was successful.
                } else {
                    // Something went wrong.
                    Log.i("nothing worked at all", "nothing worked");
                }
            }
        });

    }

    private void getSeattleMiles(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("office", "Seattle");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    mSeattleTotal = 0.0;
                    String testInt;
                    for (ParseObject tripsObject : objects) {
                        testInt = tripsObject.getObjectId();
                        Log.i("These Are Seattlites", testInt);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
                        query.whereEqualTo(ParseConstants.KEY_COMMUTER_ID, testInt);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> commuteList, ParseException e) {
                                for (ParseObject tripsObject : commuteList) {
                                    mSeattleTotal += tripsObject.getDouble("miles");
                                    Log.i("These Are Total Miles", mSeattleTotal.toString());
                                }
                                mSeattleMiles.setText(df.format(mSeattleTotal) + " mi");
                            }
                        });

                    }
                    // The query was successful.
                } else {
                    // Something went wrong.
                    Log.i("nothing worked at all", "nothing worked");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_office_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
