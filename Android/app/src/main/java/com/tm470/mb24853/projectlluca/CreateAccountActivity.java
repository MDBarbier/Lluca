package com.tm470.mb24853.projectlluca;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CreateAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        setFonts();
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
            makeMeToast("Please enter a username",1,"TOP",0,300,25);
        }
        else
        {
            if (result) {
               makeMeToast("Username \"" + userInputString + "\" is available!",1, "TOP",0,300,25);
            }
            else {
                makeMeToast("Username \"" + userInputString + "\" is not available!",1, "TOP",0,300,25);

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

        if (userNameString.equals("") || userPwString.equals("")) {

            //tell the user all fields must be completed
            makeMeToast("You must complete the User name & Password fields before submitting.",1,"TOP",0,300,25);


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

                    makeMeToast("There was an error creating the account.",1,"TOP",0,300,25);

                }
            }
            else {
                makeMeToast("Please log out the existing user before creating new user." + userName.getText().toString(),1,"TOP",0,300,25);

            }
        }
    }

    //helper method to make toast, takes a String input for the message and an integer
    //input for the duration (0 is short, 1 is long, default long)
    public void makeMeToast(String message, int length, String position, int xOffset, int yOffset, int fontSize)
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
        if (position.equals("TOP")) {
            toast.setGravity(Gravity.TOP, xOffset, yOffset);
        }
        else if (position.equals("BOTTOM"))
        {
            toast.setGravity(Gravity.BOTTOM, xOffset, yOffset);
        }
        if (fontSize == 0)
        {
            fontSize = 15;
        }

        //makes the toast text size bigger
        LinearLayout layout = (LinearLayout) toast.getView();
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setTextSize(fontSize);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");
        tv.setTypeface(font2);
        toast.show();
    }

    public void setFonts()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");

        TextView a = (TextView) findViewById(R.id.createAcc1);
        TextView b = (TextView) findViewById(R.id.createAcc2);
        TextView c = (TextView) findViewById(R.id.createAcc3);
        TextView d = (TextView) findViewById(R.id.createAcc4);
        TextView e = (TextView) findViewById(R.id.createAcc5);
        EditText f = (EditText) findViewById(R.id.create_account_username_entry);
        EditText g = (EditText) findViewById(R.id.create_account_password_entry);
        EditText h = (EditText) findViewById(R.id.create_account_email_entry);
        Button i = (Button) findViewById(R.id.signin_submit_button);
        Button j = (Button) findViewById(R.id.create_account_check);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font2);
        d.setTypeface(font2);
        e.setTypeface(font2);
        f.setTypeface(font);
        f.setTextSize(14);
        g.setTypeface(font);
        g.setTextSize(14);
        h.setTypeface(font);
        h.setTextSize(14);
        i.setTypeface(font2);
        j.setTypeface(font2);

    }

}
