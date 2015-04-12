package com.tm470.mb24853.projectlluca;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class CreateAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

    //MDB: checks to see if the username is in the db already
    public void checkUsername(View view)
    {
        EditText userInput = (EditText) findViewById(R.id.create_account_username_entry);
        String userInputString = userInput.getText().toString();
        LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
        boolean result = db_helper.checkForUser(userInputString);
        //boolean result = true;
        if (userInputString.equals(""))
        {
            makeMeToast("Please enter a username",1);
        }
        else
        {
            if (result) {
               makeMeToast("Username " + userInputString + " is available!",1);
            }
            else {
                makeMeToast("Username " + userInputString + " is not available :(",1);

            }

        }
    }

    //MDB: Tries to create a new account based on the data entered - success loads the profile screen
    //there cannot be another user logged in for this to proceed
    public void loadUserProfileActivity (View view) {
        EditText userName = (EditText) findViewById(R.id.create_account_username_entry);
        EditText userPassword = (EditText) findViewById(R.id.create_account_password_entry);
        EditText userEmail = (EditText) findViewById(R.id.create_account_email_entry);
        String userNameString = userName.getText().toString();
        String userPwString = userPassword.getText().toString();
        String userEmailString = userEmail.getText().toString();

        if (userNameString.equals("") && userEmailString.equals("") && userPwString.equals("")) {

            //tell the user all fields must be completed
            makeMeToast("You must complete the User name, Password, and Email field before submitting.",1);


        }
        else {

            LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
            userAccountClass user = new userAccountClass(userNameString, userPwString, userEmailString);

            if (!db_helper.isAnyUserLoggedIn()) {
                boolean result = db_helper.createUser(user);

                if (result) {
                    Intent intent = new Intent(this, UserProfileActivity.class);
                    intent.putExtra("username", user.getUsername());
                    startActivity(intent);
                }
                else {

                    makeMeToast("There was an error creating the account.",1);

                }
            }
            else {
                makeMeToast("Please log out the existing user before creating new user." + userName.getText().toString(),1);

            }
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
