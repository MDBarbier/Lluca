package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SignInActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_sign_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

    //MDB: logs the user in and loads the profile screen
    public void logInUser(View view)
    {
        EditText userName = (EditText) findViewById(R.id.signin_username_entry);
        EditText userPassword = (EditText) findViewById(R.id.signin_password_entry);
        String userNameString = userName.getText().toString();
        String userPwString = userPassword.getText().toString();

        if (userPwString.equals("") | userNameString.equals("")) {

            makeMeToast("You must enter your username and password",1);

        }
        else
        {
            if (!db_helper.isAnyUserLoggedIn())
            {
                if (!db_helper.checkForUser(userNameString))
                {
                    userAccountClass user = db_helper.getUser(userNameString);
                    if (user.getPassword().equals(userPwString)) {
                        db_helper.updateUser(user.getUsername(), "", "", 1);
                        Intent intent = new Intent(this, UserProfileActivity.class);
                        intent.putExtra("username", user.getUsername());
                        startActivity(intent);
                    } else {
                        makeMeToast("Incorrect password.", 1);
                    }
                }
                else
                {
                    makeMeToast("Username not found.",0);
                }
            }
            else
            {
                makeMeToast("There is already a user logged in. Log out and try again.", 1);
            }
        }
    }

    //MDB: loads the create account screen
    public void loadCreateAccountActivity (View view)
    {
        if (!db_helper.isAnyUserLoggedIn()) {
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }
        else {
            makeMeToast("There is already a user logged in. Log out and try again.", 1);
        }
    }


    //helper method to make toast, takes a String input for the message and an integer
    //input for the duration (0 is short, 1 is long, default long)
    public void makeMeToast(String message, int length)
    {

        int howBrownDoYouWantIt;

        switch (length) {

            case 0: howBrownDoYouWantIt = Toast.LENGTH_SHORT;
                break;
            case 1: howBrownDoYouWantIt = Toast.LENGTH_LONG;
                break;
            default: howBrownDoYouWantIt = Toast.LENGTH_LONG;
                break;
        }

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, howBrownDoYouWantIt);
        toast.show();
    }
}
