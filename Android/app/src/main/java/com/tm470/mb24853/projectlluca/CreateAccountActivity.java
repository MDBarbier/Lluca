package com.tm470.mb24853.projectlluca;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreateAccountActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        setFonts();
        TextView tv = (TextView) findViewById(R.id.please_wait);
        tv.setVisibility(View.GONE);
        TextView tv2 = (TextView) findViewById(R.id.please_wait2);
        tv2.setVisibility(View.GONE);
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

        switch (item.getItemId()) {

            case R.id.action_back:

                Intent intent = new Intent(this,SignInActivity.class);
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

    //MDB: checks the server to see if the username is in the db already
    //if no server access available
    public void checkUsername(View view)
    {
        EditText userInput = (EditText) findViewById(R.id.create_account_username_entry);
        final String userInputString = userInput.getText().toString();
        LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
        boolean result = db_helper.checkForUser(userInputString);

        if (userInputString.equals(""))
        {
            makeMeToast("Please enter a username",1,"TOP",0,300,25);
        }
        else if (!isNetworkAvailable())
        {
            makeMeToast("Network not available",1,"TOP",0,300,25);
        }
        else
        {
            final TextView tv = (TextView) findViewById(R.id.please_wait);
            tv.setVisibility(View.VISIBLE);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.0.11/checkusername.php";

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    tv.setVisibility(View.GONE);
                    makeMeToast("Server Response: " + response, 1, "TOP", 0, 300, 25);

                }};

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tv.setVisibility(View.GONE);
                    if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    {
                        makeMeToast("Cannot contact LLuca server!", 1, "TOP", 0, 300, 25);
                    }
                    else {makeMeToast("Server error:" + error, 1, "TOP", 0, 300, 25);}

                }};

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username", userInputString);
                    return map;
                }};

            queue.add(stringRequest);

        }
    }

    //MDB: Tries to create a new account based on the data entered - success loads the profile screen
    //there cannot be another user logged in for this to proceed
    public void loadUserProfileActivity (final View view) {

        Boolean isNetworkAvailable = isNetworkAvailable();

        if (isNetworkAvailable)
        {
            EditText userName = (EditText) findViewById(R.id.create_account_username_entry);
            EditText userPassword = (EditText) findViewById(R.id.create_account_password_entry);
            EditText userEmail = (EditText) findViewById(R.id.create_account_email_entry);
            final String userNameString = userName.getText().toString();
            final String userPwString = userPassword.getText().toString();
            final String userEmailString = userEmail.getText().toString();
            //final String lastSync = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date now = new Date();
            final String lastSync = dateFormat.format(now);

            if (userNameString.equals("") || userPwString.equals("")) {

                //tell the user all fields must be completed
                makeMeToast("You must complete the User name & Password fields before submitting.", 1, "TOP", 0, 300, 25);
            } else {

                final LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
                final userAccountClass user = new userAccountClass(userNameString, userPwString, userEmailString);

                if (!db_helper.isAnyUserLoggedIn()) {

                   //check username free on server
                    final TextView tv2 = (TextView) findViewById(R.id.please_wait2);
                    tv2.setVisibility(View.VISIBLE);
                    EditText userInput = (EditText) findViewById(R.id.create_account_username_entry);
                    final String userInputString = userInput.getText().toString();
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url = "http://192.168.0.11/createUser.php";

                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            tv2.setVisibility(View.GONE);
                            if (response.equals("USERNAME TAKEN"))
                            {
                                makeMeToast("Server Response: " + response, 1, "TOP", 0, 300, 25);
                            }
                            if (response.equals("ACCOUNT CREATED"))
                            {

                                makeMeToast("Account created", 1, "TOP", 0, 300, 25);
                                //create local profile
                                boolean result = db_helper.createUser(user);
                                //if was successful, load profile screen
                                if (result) {
                                    Intent intent = new Intent(CreateAccountActivity.this, UserProfileActivity.class);
                                    intent.putExtra("username", user.getUsername());
                                    startActivity(intent);
                                } else {

                                    makeMeToast("There was an error creating the account.", 1, "TOP", 0, 300, 25);

                                }
                            }

                        }};

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tv2.setVisibility(View.GONE);

                            if (error instanceof TimeoutError || error instanceof NoConnectionError)
                            {
                                makeMeToast("Cannot contact LLuca server!", 1, "TOP", 0, 300, 25);

                                //pop up which says you can use temporary profile until server available
                                showLocalDeckbuilderWarning(view);
                            }
                            else {makeMeToast("Server error:" + error, 1, "TOP", 0, 300, 25);}

                        }};

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<String, String>();
                            map.put("username", userNameString);
                            map.put("password", userPwString);
                            map.put("email", userEmailString);
                            map.put("lastsync", lastSync);
                            return map;
                        }};

                    queue.add(stringRequest);


                } else {
                    makeMeToast("Please log out the existing user before creating new user." + userName.getText().toString(), 1, "TOP", 0, 300, 25);

                }
            }
        }
        else
        {
            makeMeToast("Network access unavailable", 1,"TOP",0,300,25);
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
        TextView k = (TextView) findViewById(R.id.please_wait);

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
        k.setTypeface(font2);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void showLocalDeckbuilderWarning(View view)
    {
        final Dialog helpDialogue = new Dialog(this);
        helpDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpDialogue.setContentView(R.layout.custom_dialogue_deckbuilder_warning);

        final TextView helpTextTitle = (TextView) helpDialogue.findViewById(R.id.helpTextTitle);
        final Button okButton = (Button) helpDialogue.findViewById(R.id.okButton);
        final TextView helpTextView = (TextView) helpDialogue.findViewById(R.id.helpTextWarning);
        final Button cancelButton = (Button) helpDialogue.findViewById(R.id.cancelButton);
        String helpText = "You have not created an account, any decks you create before creating an account will only be saved locally and cannot be transferred to the server. Should only be used in the event you cannot access the internet to create an account.";
        helpTextView.setText(helpText);
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        helpTextView.setTypeface(font);
        helpTextView.setTextSize(10);
        helpTextTitle.setTypeface(font);
        okButton.setTypeface(font);
        okButton.setTextSize(9);
        cancelButton.setTypeface(font);
        cancelButton.setTextSize(9);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpDialogue.dismiss();
                db_helper.updateUser("local", "pw","",1);
                Intent intent = new Intent(CreateAccountActivity.this, DeckListActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpDialogue.dismiss();
            }
        });
        helpDialogue.show();
        Window window = helpDialogue.getWindow();
        //window.setLayout(500,600);
    }

}

