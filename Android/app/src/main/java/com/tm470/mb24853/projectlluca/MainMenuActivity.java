package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainMenuActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //populate the tables if not done already
        new InitialPopulation().execute();

        if (db_helper.isAnyUserLoggedIn())
        {
            String username = db_helper.getCurrentUser().getUsername() + "'s Profile";
            String username2 = "Logout " + db_helper.getCurrentUser().getUsername();
            TextView userIdTextView = (TextView) findViewById(R.id.button);
            TextView userIdTextView2 = (TextView) findViewById(R.id.sign_out_button);
            userIdTextView.setText(username);
            userIdTextView2.setText(username2);
        }
        else{
            TextView userIdTextView2 = (TextView) findViewById(R.id.sign_out_button);
            userIdTextView2.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    //MDB: loads the sign in screen
    public void loadSignInActivity (View view)
    {
        if (!db_helper.isAnyUserLoggedIn())
        {
        Intent intentSignInScreen = new Intent(this, SignInActivity.class);
        startActivity(intentSignInScreen);}
        else {
            userAccountClass user = db_helper.getCurrentUser();
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
        }


    }

    //MDB: shows legal info
    public void showLegalInfo(View view)
    {
        makeMeToast("Lord of the Rings LCG is Copyright Fantasy Flight Games, I own no legal rights on any Lord of the Rings names, characters or places.", 1);
    }

    //MDB: loads quest log if there is a user logged in
    public void questLog(View view)
    {
        makeMeToast("Not implemented in this release.", 1);

    }

    //MDB: loads llucapaedia
    public void llucapaedia(View view)
    {
        makeMeToast("Not implemented in this release.", 1);

    }

    //MDB: loads the deck list screen IF there is a user logged in
    public void loadDeckListActivity (View view)
    {
        if(db_helper.isAnyUserLoggedIn()) {
            Intent intent = new Intent(this, DeckListActivity.class);
            startActivity(intent);
        }
        else {makeMeToast("Please log in or create an account first.",1);}
    }

    //MDB: loads the card browser
    public void loadCardBrowserActivity(View view)
    {
        Intent intent= new Intent(this, CardBrowserActivity.class);
        startActivity(intent);
    }

    //Logs out the current user
    public void logoutCurrentUser(View view)
    {
        if (db_helper.isAnyUserLoggedIn()){
            userAccountClass user = db_helper.getCurrentUser();
            String userName = user.getUsername();
            db_helper.updateUser(userName, "","",0);
            makeMeToast("Current user has been logged out.",1);
            Intent intent= new Intent(this, MainMenuActivity.class);
            startActivity(intent);
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

    public class InitialPopulation extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

           SQLiteDatabase db = db_helper.getWritableDatabase();
           LLuca_Local_DB_schema schema = new LLuca_Local_DB_schema();
            Log.w("Async", "Inside Async Task");
            if(!db_helper.getPopulationStatus()) {
                //Populate the tables
                db.execSQL(schema.getDeckpartPopulate());
                db.execSQL(schema.getPlayerCardPopulation1());
                db.execSQL(schema.getPlayerCardPopulation2());
                db.execSQL(schema.getencountercardPopulation1());
                db.execSQL(schema.getencountercardPopulation2());
                db.execSQL(schema.getencountercardPopulation3());
                db.execSQL(schema.getherocardPopulation1());
                db.execSQL(schema.getquestcardPopulation1());
                db.execSQL(schema.getquestcardPopulation2());
                db.execSQL(schema.getSqlCreateControlDataRecord());
                db_helper.setPopulationStatus(1);
                return null;
            }
            return null;
        }
    }


}
