package com.rbompiani.srglowcarboncommute;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rbompiani.srglowcarboncommute.adapters.LeaderboardAdapter;
import com.rbompiani.srglowcarboncommute.adapters.LeaderboardAdapter2;
import com.rbompiani.srglowcarboncommute.data.Rider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LeaderboardActivityTest2 extends ListActivity {
    public int mYear = Calendar.getInstance().get(Calendar.YEAR);
    public String mThisYear ="01/01/"+mYear;
    public Date mNewDate = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //getUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending("username");
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    //great success

                    ParseUser[] userList = new ParseUser[users.size()];
                    users.toArray(userList);

                    Rider[] riderList = new Rider[users.size()];

                    for (int i = 0; i < userList.length; i++) {
                        riderList[i] = new Rider();
                        riderList[i].setRiderId(userList[i].getObjectId());
                        riderList[i].setFirstName(userList[i].getString("first"));
                        riderList[i].setLastName(userList[i].getString("last"));
                        riderList[i].setCommute(userList[i].getString("commuteType"));
                        riderList[i].setOffice(userList[i].getString("office"));
                        calcRiderTotal(riderList[i].getRiderId(),riderList[i]);
                        //riderList[i].setMiles(calcRiderTotal(riderList[i].getRiderId()));

                    }


                    LeaderboardAdapter2 adapter = new LeaderboardAdapter2(LeaderboardActivityTest2.this, riderList);
                    setListAdapter(adapter);
                } else {
                    //error
                    Log.e("this didn't work", e.getMessage());
                }
            }
        });
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

    public void calcRiderTotal (String riderId, final Rider thisRider) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("riderId", riderId);
        ParseCloud.callFunctionInBackground("updateRiderTotal", params, new FunctionCallback<Double>() {
            public void done(Double miles, ParseException e) {
                if (e == null) {

                    Log.i("miles: ", miles.toString());
                    thisRider.setMiles(miles);
                    String stringMiles = Double.toString(thisRider.getMiles());
                    Toast.makeText(LeaderboardActivityTest2.this, thisRider.getFirstName()+" "+stringMiles, Toast.LENGTH_LONG).show();

                } else {
                    Log.i("OOPS: ", "the request isnt working");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
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
