package com.tm470.mb24853.projectlluca;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainMenuActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169,186,182));

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

        setFonts();

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
        final Dialog helpDialogue = new Dialog(this);
        helpDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpDialogue.setContentView(R.layout.custom_dialogue_help);
        helpDialogue.setTitle("Legal disclaimer");
        final Button okButton = (Button) helpDialogue.findViewById(R.id.okButton);
        final TextView helpTextView = (TextView) helpDialogue.findViewById(R.id.helpText);
        String helpText = "Lord of the Rings LCG is Copyright Fantasy Flight Games, I own no legal rights on any Lord of the Rings names, characters, images or places. This has been developed for purely personal academic use and is not intended to be sold or rented.";
        helpTextView.setText(helpText);
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        helpTextView.setTypeface(font);
        helpTextView.setTextSize(8);
        okButton.setTypeface(font);
        okButton.setTextSize(8);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpDialogue.dismiss();
            }
        });

        helpDialogue.show();
    }

    //MDB: loads quest log if there is a user logged in
    public void questLog(View view)
    {
        //makeMeToast("Not implemented in this release.", 1,"BOTTOM",0,0,25);
        Intent intent = new Intent(this,QuestBrowserActivity.class);
        startActivity(intent);
    }

    //MDB: loads the deck list screen IF there is a user logged in
    public void loadDeckListActivity (View view)
    {
        if(db_helper.isAnyUserLoggedIn()) {
            Intent intent = new Intent(this, DeckListActivity.class);
            startActivity(intent);
        }
        else {makeMeToast("Please log in or create an account first.",1,"BOTTOM",0,0,18);}
    }

    //MDB: loads the card browser
    public void loadCardBrowserActivity(View view)
    {
        Intent intent = new Intent(this, CardBrowserActivity.class);
        intent.putExtra("type", "encounter");
        startActivity(intent);
    }

    //Logs out the current user
    public void logoutCurrentUser(View view)
    {
        if (db_helper.isAnyUserLoggedIn()){
            userAccountClass user = db_helper.getCurrentUser();
            String userName = user.getUsername();
            db_helper.updateUser(userName, "","",0);
            makeMeToast("Current user has been logged out.",1,"BOTTOM",0,0,18);
            Intent intent= new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }


    public void showHelp(View view)
    {
        final Dialog helpDialogue = new Dialog(this);
        helpDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpDialogue.setContentView(R.layout.custom_dialogue_help);

        helpDialogue.setTitle("How to use the app");
        final Button okButton = (Button) helpDialogue.findViewById(R.id.okButton);
        final TextView helpTextView = (TextView) helpDialogue.findViewById(R.id.helpText);
        String helpText = "First steps\n" +
                "\n" +
                "- Create a user account by clicking \"user profile\" then \"create account\" and following the prompts on screen\n" +
                "- Once your account is created, edit the packs you own by going to your profile screen (top left button on main menu then \"Owned packs\" then \"Edit your packs\")\n" +
                "\n" +
                "Using the Deckbuilder\n" +
                "\n" +
                "- Click the Deckbuilder button from the main menu\n" +
                "- Click \"create new deck\"\n" +
                "- Name your deck\n" +
                "- Tap the deck name to select it\n" +
                "- From the Edit Deck screen you can a) add cards b) add heroes c) view demographics about your deck d) delete the deck\n" +
                "\n" +
                "Using the Card Browser\n" +
                "\n" +
                "- The card browser can be used to view any cards in the game\n" +
                "- Tap the Filter icon in the action bar to change the card type you are looking at\n" +
                "- Tap a card to view details";
        helpTextView.setText(helpText);
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        helpTextView.setTypeface(font);
        helpTextView.setTextSize(8);
        okButton.setTypeface(font);
        okButton.setTextSize(8);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpDialogue.dismiss();
            }
        });

        helpDialogue.show();
    }

    public class InitialPopulation extends AsyncTask<Void, Void, Void>
    {
        protected void onPreExecute(){
            //locks the orientation sensor whilst the async task is happening to prevent interruption
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }


        @Override
        protected Void doInBackground(Void... params) {

           SQLiteDatabase db = db_helper.getWritableDatabase();
           LLuca_Local_DB_schema schema = new LLuca_Local_DB_schema();
            Log.w("Async", "Inside Population Async Task");
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

        @Override
        protected void onPostExecute(Void aVoid) {
            //unlocks the sensor so it will detect orientation changes again
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
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

    public void setFonts()
    {
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");

        TextView header1 = (TextView) findViewById(R.id.project_lluca);
        TextView header2 = (TextView) findViewById(R.id.main_menu_textview);
        TextView login = (TextView) findViewById(R.id.button);
        TextView logout = (TextView) findViewById(R.id.sign_out_button);
        TextView deckbuilder = (TextView) findViewById(R.id.deckbuilder_button);
        TextView questbrowser = (TextView) findViewById(R.id.questlog_button);
        TextView cardbrowser = (TextView) findViewById(R.id.cardlist_button);
        TextView howto = (TextView) findViewById(R.id.llucapaedia_button);
        TextView legal = (TextView) findViewById(R.id.legal_button);

        header1.setTypeface(font2);
        header2.setTypeface(font2);
        login.setTypeface(font);
        logout.setTypeface(font);
        deckbuilder.setTypeface(font);
        questbrowser.setTypeface(font);
        cardbrowser.setTypeface(font);
        howto.setTypeface(font);
        legal.setTypeface(font);
    }

}
