package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SignInActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_sign_in);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        setFonts();
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
        switch (item.getItemId()) {

            case R.id.action_back:

                Intent intent = new Intent(this,MainMenuActivity.class);
                startActivity(intent);
                //makeMeToast("back",1);

                return true;
            case R.id.action_home:
                Intent goHome = new Intent(this,MainMenuActivity.class);
                startActivity(goHome);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //MDB: logs the user in and loads the profile screen
    public void logInUser(View view)
    {
        EditText userName = (EditText) findViewById(R.id.signin_username_entry);
        EditText userPassword = (EditText) findViewById(R.id.signin_password_entry);
        String userNameString = userName.getText().toString();
        String userPwString = userPassword.getText().toString();

        if (userPwString.equals("") | userNameString.equals("")) {

            makeMeToast("You must enter your username and password",1,"TOP",0,300,25);

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
                        makeMeToast("Incorrect password.", 1,"TOP",0,300,25);
                    }
                }
                else
                {
                    makeMeToast("Username not found.",0,"TOP",0,300,25);
                }
            }
            else
            {
                makeMeToast("There is already a user logged in. Log out and try again.", 1,"TOP",0,300,25);
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
            makeMeToast("There is already a user logged in. Log out and try again.",1,"TOP",0,300,25);
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

        TextView a = (TextView) findViewById(R.id.signIn1);
        TextView b = (TextView) findViewById(R.id.signIn2);
        TextView c = (TextView) findViewById(R.id.signIn3);
        TextView d = (TextView) findViewById(R.id.signIn4);
        TextView e = (TextView) findViewById(R.id.signin_create_acc);
        TextView f = (TextView) findViewById(R.id.signin_submit_button);
        EditText g = (EditText) findViewById(R.id.signin_password_entry);
        EditText h = (EditText) findViewById(R.id.signin_username_entry);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font2);
        d.setTypeface(font2);
        e.setTypeface(font2);
        f.setTypeface(font2);
        g.setTypeface(font);
        h.setTypeface(font);

    }
}
