package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
    String ip = "192.168.0.9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_sign_in);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView servermessage = (TextView) findViewById(R.id.please_wait);
        servermessage.setVisibility(customTextViewForListView.GONE);

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

            makeMeToast("You must enter your username and password", 1, "TOP", 0, 300, 25);

        }
        else
        {
            if (!db_helper.isAnyUserLoggedIn())
            {
                if (!db_helper.checkForUser(userNameString))
                {
                    if (!userNameString.equals("local")) {
                        userAccountClass user = db_helper.getUser(userNameString);
                        if (user.getPassword().equals(userPwString)) {
                            db_helper.updateUser(user.getUsername(), "", "", 1);
                            Intent intent = new Intent(this, UserProfileActivity.class);
                            intent.putExtra("username", user.getUsername());
                            startActivity(intent);
                        } else {
                            makeMeToast("Incorrect password.", 1, "TOP", 0, 300, 25);
                        }
                    }
                    else
                    {
                        makeMeToast("Cannot log in as Local account.", 1, "TOP", 0, 300, 25);
                    }
                }
                else
                {
                    //check network access if none then see if username/pw combo exists on server
                    if (isNetworkAvailable()) {

                        checkUsername(view, userNameString, userPwString);
                        }
                    else
                    {
                        makeMeToast("Profile does not exist locally and you have no network access to check on the LLuca server.", 1,"TOP",0,300,25);
                    }
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
        TextView i = (TextView) findViewById(R.id.signIn6);
        TextView tv = (TextView) findViewById(R.id.please_wait);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font2);
        d.setTypeface(font2);
        e.setTypeface(font2);
        f.setTypeface(font2);
        g.setTypeface(font);
        h.setTypeface(font);
        i.setTypeface(font2);
        tv.setTypeface(font2);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void checkUsername(final View view, final String username, final String password)
    {

        final TextView tv = (TextView) findViewById(R.id.please_wait);
        tv.setVisibility(View.VISIBLE);
        tv.setText("Please wait...contacting server");

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://" + ip + "/checkusernameandpassword.php";

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    tv.setVisibility(View.GONE);
                    if (response.equals("ACCOUNT EXISTS")) {
                        answerYes(view, username, password);
                    }
                    else
                    {
                        answerNo(view);
                    }

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
                    map.put("username", username);
                    map.put("password", password);
                    return map;
                }};


            queue.add(stringRequest);

        }

    public void answerYes(View view, String username, String password)
    {
        //makeMeToast("Account does not exist locally but does exist on the LLuca server.", 1, "TOP", 0, 300, 25);
        downloadProfileDialogue(view, username, password);
    }

    public void answerNo(View view)
    {
        makeMeToast("There is no account matching those credentials either locally or on the LLuca server.", 1, "TOP", 0, 300, 25);
    }

    public void downloadProfileDialogue(View view, final String username, final String password)
    {
        final Dialog downloadDialogue = new Dialog(this);
        downloadDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        downloadDialogue.setContentView(R.layout.custom_dialogue_deckbuilder_warning);

        final TextView helpTextTitle = (TextView) downloadDialogue.findViewById(R.id.helpTextTitle);
        final Button okButton = (Button) downloadDialogue.findViewById(R.id.okButton);
        final TextView helpTextView = (TextView) downloadDialogue.findViewById(R.id.helpTextWarning);
        final Button cancelButton = (Button) downloadDialogue.findViewById(R.id.cancelButton);
        String helpText = "Account does not exist locally but does exist on the LLuca server. Do you wish to download the account to the local device?";
        String helpTitle = "Attention!";
        helpTextTitle.setText(helpTitle);
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

                downloadDialogue.dismiss();
                //makeMeToast("Profile downloaded.", 1, "TOP", 0, 300, 25);
                downloadUser(username, password);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadDialogue.dismiss();
            }
        });
        downloadDialogue.show();
        //Window window = downloadDialogue.getWindow();
        //window.setLayout(500,600);
    }

    public void downloadUser(final String username, final String password)
    {
        final TextView tv = (TextView) findViewById(R.id.please_wait);
        tv.setVisibility(View.VISIBLE);
        tv.setText("Please wait...contacting server");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + ip + "/downloaduseraccount.php";

        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password",password);
        }
        catch (JSONException e)
        {};

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setVisibility(View.GONE);
                insertUserLocally(response);
            }};

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setVisibility(View.GONE);
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                {
                    makeMeToast("Cannot contact LLuca server!", 1, "TOP", 0, 300, 25);
                }
                else {
                    makeMeToast("Server error:" + error, 1, "TOP", 0, 300, 25);
                    Log.w("volley", "Downloadusermethod" + error);
                }

            }};

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, listener, errorListener) {
        @Override
        public Map<String,String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("charset", "utf-8");
            return headers;
        }
        };
                queue.add(jsonRequest);
    }

    public void insertUserLocally(JSONObject user)
    {
        userAccountClass userObject = new userAccountClass();
        String username;
        String password;
        String email;

        try {

            username = user.getString("username");
            password = user.getString("password");
            email = user.getString("email");

            userObject.setUsername(username);
            userObject.setPassword(password);
            userObject.setEmailAddress(email);

            db_helper.createUser(userObject);
            makeMeToast("User account \'" + username + "\' created in local database. Press sync with server to download data.", 1, "TOP", 0, 300, 25);
            Intent intent = new Intent(SignInActivity.this, UserProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);

        }
        catch (JSONException e)
        {
            makeMeToast("Error parsing JSON data received from server.", 1,"TOP",0,300,25);
            Log.w("json",e);
        }
    }
}
