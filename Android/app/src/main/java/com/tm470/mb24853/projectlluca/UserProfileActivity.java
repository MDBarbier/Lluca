package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class UserProfileActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        setContentView(R.layout.activity_user_profile);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView userIdTextView = (TextView) findViewById(R.id.userprofile_username);
        TextView tv = (TextView) findViewById(R.id.please_wait);
        tv.setVisibility(View.GONE);
        TextView tv2 = (TextView) findViewById(R.id.userprofile_lastsync);
        tv2.setText(db_helper.getLastSync(username));

        LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
        userAccountClass user = db_helper.getUser(username);

        if (user != null) {
            userIdTextView.setText(user.getUsername());

        } else {
            userIdTextView.setText("No Match Found");
        }

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
                return true;
            case R.id.action_home:
                Intent goHome = new Intent(this,MainMenuActivity.class);
                startActivity(goHome);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //MDB: loads the main menu screen
    public void loadMainMenu (View view)
    {
        Intent intent= new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    //MDB: loads the edit deck screen
    public void loadEditOwnedPacksActivity (View view)
    {
        Intent intent= new Intent(this, EditOwnedPacksActivity.class);
        startActivity(intent);
    }

    //MDB: synchronises account with server
    public void syncWithServer (View view)
    {
        //makeMeToast("Server synchronisation not implemented in this release.", 1, "BOTTOM",0,0,18);


        final userAccountClass user = db_helper.getCurrentUser();
        final String localSync = db_helper.getLastSync(db_helper.getCurrentUser().getUsername());


        final TextView tv = (TextView) findViewById(R.id.please_wait);
        tv.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.11/getsynctime.php";

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setVisibility(View.GONE);
                //makeMeToast("Server Response: " + response, 1, "TOP", 0, 300, 25);
                String serverSync = response;
                compareSyncTimes(localSync,serverSync,user.getUsername());
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
                map.put("username", user.getUsername());
                return map;
            }};

        queue.add(stringRequest);
    }

    public void compareSyncTimes(String localSync, String serverSync, String username)
    {

        //makeMeToast("Server time: " + serverSync + "\n Local time: " + localSync, 1, "TOP", 0, 300, 25);
        if (localSync.equals(serverSync))
        {
            makeMeToast("Synced with LLuca server", 1, "TOP", 0, 300, 25);
        }
        else
        {
            makeMeToast("Synchronisation required", 1, "TOP", 0, 300, 25);
        }

        TextView tv2 = (TextView) findViewById(R.id.userprofile_lastsync);
        tv2.setText(db_helper.getLastSync(username));

    }

    //Logs out the current user
    public void logoutCurrentUser(View view)
    {
        if (db_helper.isAnyUserLoggedIn()){
            userAccountClass user = db_helper.getCurrentUser();
            String userName = user.getUsername();
            db_helper.updateUser(userName, "","",0);

            makeMeToast("Current user has been logged out", 1, "BOTTOM",0,0,18);

            Intent intent= new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }


    public void setFonts()
    {
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");

        TextView a = (TextView) findViewById(R.id.a);
        TextView b = (TextView) findViewById(R.id.b);
        TextView c = (TextView) findViewById(R.id.userprofile_lastsync);
        TextView d = (TextView) findViewById(R.id.userprofile_username);
        TextView e = (TextView) findViewById(R.id.e);
        TextView f = (TextView) findViewById(R.id.f);
        TextView g = (TextView) findViewById(R.id.g);
        TextView h = (TextView) findViewById(R.id.sync_button);
        TextView i = (TextView) findViewById(R.id.edit_deck_button);
        TextView j = (TextView) findViewById(R.id.logout_button);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font);
        d.setTypeface(font);
        e.setTypeface(font2);
        f.setTypeface(font2);
        g.setTypeface(font2);
        h.setTypeface(font);
        i.setTypeface(font);
        j.setTypeface(font);

    }

    //helper method to make toast, takes a String input for the message and an integer
    //input for the duration (0 is short, 1 is long, default long)
    //also you can specify the position of the toast and the font size
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


}
