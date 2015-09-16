package com.rbompiani.srglowcarboncommute;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.addTripButton)Button mAddTripButton;
    @Bind(R.id.thisYearMiles)TextView mTotalMiles;
    @Bind(R.id.todayMiles)TextView mTodayMiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser == null){
            navigateToLogin();
        }

        getTotalMiles();
        getTodayMiles();



        mAddTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject trip = createTrip();
                addTrip(trip);

            }
        });


    }


    protected ParseObject createTrip() {

        ParseObject trip = new ParseObject(ParseConstants.CLASS_TRIPS);

        trip.put(ParseConstants.KEY_COMMUTER_ID, ParseUser.getCurrentUser().getObjectId());
        trip.put("date", Calendar.getInstance().getTime() );
        trip.put(ParseConstants.KEY_TRIP_MILES, ParseUser.getCurrentUser().get(ParseConstants.KEY_COMMUTE_LENGTH));
        return trip;
    }

    protected void addTrip(ParseObject trip) {
        trip.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //success!
                    Toast.makeText(MainActivity.this, "Trip Added!", Toast.LENGTH_LONG).show();
                    getTotalMiles();
                    getTodayMiles();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.signup_error_title);
                    builder.setMessage(R.string.signup_error_message);
                    builder.setPositiveButton(android.R.string.ok, null);
                }
            }
        });
    }


    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void getTotalMiles() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereEqualTo(ParseConstants.KEY_COMMUTER_ID, ParseUser.getCurrentUser().getObjectId());
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

    private void getTodayMiles() {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String thisDay = outputFormatter.format(Calendar.getInstance().getTime());
        Date newDate = Calendar.getInstance().getTime();

        try {
            newDate = outputFormatter.parse(thisDay);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
        query.whereEqualTo(ParseConstants.KEY_COMMUTER_ID, ParseUser.getCurrentUser().getObjectId());
        query.whereGreaterThanOrEqualTo(ParseConstants.KEY_DATE, newDate);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commuteList, ParseException e) {
                Double total = 0.0;
                for (ParseObject tripsObject : commuteList) {
                    total += tripsObject.getDouble("miles");
                }
                mTodayMiles.setText(total + " mi");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
   //     if (id == R.id.action_logout) {
  //          ParseUser.logOut();
  //          navigateToLogin();
  //      }

        switch(id){
            case R.id.home:
                return true;
            case R.id.daily_commutes:
                Intent dailyIntent = new Intent(this, DailyCommutesActivity.class);
                startActivity(dailyIntent);
                return true;
            case R.id.office_stats:
                Intent officeIntent = new Intent(this, OfficeStatsActivity.class);
                startActivity(officeIntent);
                return true;
            case R.id.leaderboard:
                Intent leaderIntent = new Intent(this, LeaderboardActivityTest2.class);
                startActivity(leaderIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
