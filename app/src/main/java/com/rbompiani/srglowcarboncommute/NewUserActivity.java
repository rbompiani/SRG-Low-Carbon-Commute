package com.rbompiani.srglowcarboncommute;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class NewUserActivity extends ActionBarActivity {

    private RadioGroup mCommuteType;
    private EditText mMiles;
    private RadioGroup mOffice;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mCommuteType = (RadioGroup)findViewById(R.id.commuteType);
        mMiles = (EditText)findViewById(R.id.milesInput);
        mOffice = (RadioGroup)findViewById(R.id.officeInput);
        mSubmitButton = (Button)findViewById(R.id.submitButton);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String milesCheck = mMiles.getText().toString().trim();

                if(mCommuteType.getCheckedRadioButtonId() == -1 || milesCheck.isEmpty() || mOffice.getCheckedRadioButtonId() ==-1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this);
                    builder.setMessage(R.string.signup_error_message);
                    builder.setTitle(R.string.signup_error_title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else {
                    //update the user info
                    String commuteValue = ((RadioButton)findViewById(mCommuteType.getCheckedRadioButtonId())).getText().toString();
                    String officeValue = ((RadioButton)findViewById(mOffice.getCheckedRadioButtonId())).getText().toString();
                    Double milesValue = Double.parseDouble(mMiles.getText().toString());
                    currentUser.put(ParseConstants.KEY_COMMUTE_TYPE, commuteValue);
                    currentUser.put(ParseConstants.KEY_COMMUTE_LENGTH, milesValue);
                    currentUser.put(ParseConstants.KEY_OFFICE, officeValue);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                // Succes!
                                Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle(R.string.signup_error_title);
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });


                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
