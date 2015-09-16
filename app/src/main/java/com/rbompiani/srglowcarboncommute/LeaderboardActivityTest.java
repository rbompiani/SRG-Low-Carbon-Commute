package com.rbompiani.srglowcarboncommute;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rbompiani.srglowcarboncommute.adapters.LeaderboardAdapter;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivityTest extends ListActivity {

    public List<String> mUsers = new ArrayList<String>();

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
                if( e==null){
                    //great success
                    ParseUser[] userList = new ParseUser[users.size()];
                    users.toArray(userList);
                    LeaderboardAdapter adapter = new LeaderboardAdapter(LeaderboardActivityTest.this, userList);
                    setListAdapter(adapter);
                }else{
                    //error
                    Log.e("this didn't work", e.getMessage());
                }
            }
        });
    }

    public void getUsers(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (ParseUser userObject : objects) {
                        mUsers.add(userObject.getObjectId());
                    }
                    for (String listItem : mUsers) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Trips");
                        query.whereEqualTo(ParseConstants.KEY_COMMUTER_ID, listItem);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> commuteList, ParseException e) {

                                Double total = 0.0;
                                for (ParseObject tripsObject : commuteList) {
                                    total += tripsObject.getDouble("miles");

                                }
                                Log.i("it worked!", total + " mi");
                            }
                        });
                    }
                } else {
                    // Something went wrong.
                    Log.i("Oh NO", "something no good");
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
