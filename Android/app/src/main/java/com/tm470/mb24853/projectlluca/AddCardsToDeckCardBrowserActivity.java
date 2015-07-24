package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class AddCardsToDeckCardBrowserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_browser);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169,186,182));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        new FilteringOperations().execute("");
        setFonts();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addcard_to_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            //makeMeToast(deckname,1);
        }

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        switch (item.getItemId()) {
            case R.id.action_search:
                //makeMeToast("search",1);
                searchDialog();
                return true;
            case R.id.action_settings:
                //makeMeToast("settings",1);
                settingsDialog();
                return true;
            case R.id.action_filter:
                //makeMeToast("filters",1);
                filterDialog();
                return true;
            case R.id.action_back:
                Bundle bundle = getIntent().getExtras();
                String deckname = bundle.getString("deckname");
                Intent intent = new Intent(this,EditDeckActivity.class);
                intent.putExtra("deckname", deckname);
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

    //loads tableadapter and cursor
    public void updateListView(Cursor cardlist_cursor)
    {

            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            tableadapter_playercardlist_helper adapter = new tableadapter_playercardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);

            //tap to view
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_card_name);
                    String text = currentCard.getText().toString();
                    displayCardDialog(text);

                }
            });

        //long press to add to deck
        cardList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView currentCard = (TextView) view.findViewById(R.id.template_card_name);
                String text = currentCard.getText().toString();
                Bundle bundle = getIntent().getExtras();
                String deckname = bundle.getString("deckname");

                //check if copies in deck exceed total copies available
                if (db_helper.areThereSpares(text, "Player", deckname)) {
                    db_helper.putCardInDeck(deckname, text);
                    makeMeToast("Card name: " + text + " added to deck.",1,"BOTTOM",0,0,15);
                }
                else
                {
                    makeMeToast("You already have the maximum number of this particular card.",1,"BOTTOM",0,0,15);
                }


                return true;
            }
        });
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

    //handles the clicking of the action bar filter icon
    public void filterDialog()
    {

        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");
        Intent intent = new Intent(this, DeckCardListFiltersActivity.class);
        intent.putExtra("deckname", deckname);
        startActivity(intent);

    }

    public void searchDialog()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        final Dialog searchDialogBox = new Dialog(this);
        searchDialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialogBox.setContentView(R.layout.custom_dialogue_searchfilters);
        final Button okButton = (Button) searchDialogBox.findViewById(R.id.okButtonSearch);
        final EditText searchQueryField = (EditText) searchDialogBox.findViewById(R.id.searchQueryField);
        final TextView label = (TextView) searchDialogBox.findViewById(R.id.textViewSearchLabel);

        okButton.setTypeface(font);
        label.setTypeface(font);

        searchQueryField.setTypeface(font);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchQuery = searchQueryField.getText().toString();
                new FilteringOperations().execute(searchQuery);
                searchDialogBox.dismiss();
            }
        });

        searchDialogBox.show();
    }

    //handles the clicking of the action bar settings icon
    public void settingsDialog()
    {

        userAccountClass user = db_helper.getCurrentUser();
        if (!user.getUsername().equals("local")) {


            final Dialog settingsDialogBox = new Dialog(this);
            Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");
            settingsDialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
            settingsDialogBox.setContentView(R.layout.custom_dialogue_settingsfilters);

            final Button okButton = (Button) settingsDialogBox.findViewById(R.id.okButtonSettings);
            final Switch onlyOwnedSwitch = (Switch) settingsDialogBox.findViewById(R.id.ownedCardsSwitch);
            final TextView tv = (TextView) settingsDialogBox.findViewById(R.id.textView5);
            tv.setTypeface(font);
            okButton.setTypeface(font);
            onlyOwnedSwitch.setTypeface(font);

            if (db_helper.getOnlyOwnedStatus()) {
                onlyOwnedSwitch.setChecked(true);
            }


            onlyOwnedSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onlyOwnedSwitch.isChecked()) {
                        db_helper.setOnlyOwnedStatus(1);
                    } else {
                        db_helper.setOnlyOwnedStatus(0);
                    }
                }
            });


            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new FilteringOperations().execute("");
                    settingsDialogBox.dismiss();
                }
            });

            settingsDialogBox.show();

        }
        else
        {
            makeMeToast("Cannot change settings when using temporary profile!",1,"BOTTOM",0,0,18);
        }
    }

    public class FilteringOperations extends AsyncTask<String, Void, Cursor>
    {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //locks the orientation sensor whilst the async task is happening to prevent interruption
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

            dialog = new ProgressDialog(AddCardsToDeckCardBrowserActivity.this);
            dialog.setMessage("Please wait... shuffling the cards");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }
        @Override
        protected Cursor doInBackground(String... params) {

            SQLiteDatabase db = db_helper.getWritableDatabase();
            LLuca_Local_DB_schema schema = new LLuca_Local_DB_schema();
            Log.w("Async", "Inside Filter Async Task");
            Bundle bundle = getIntent().getExtras();

            // NOTE when adding another thing here add it to the public void add_cards_to_deck(View view) method in EditDeckActivity.java
            // also need to update the onclick handler for the OK button in the settings dialog
            String deckname = bundle.getString("deckname");
            String typeFilter = bundle.getString("typeFilter");
            String sphere = bundle.getString("sphere");
            String cost = bundle.getString("cost");
            String query = params[0];

            if (query.equals("")) {
                Cursor cursor = db_helper.getFilteredPlayerCardListCursor(typeFilter, sphere, cost);
                return cursor;
            }
            else
            {
                Cursor cursor = db_helper.searchQueryCursor(query);
                return cursor;
            }
        }

        protected void onPostExecute(Cursor result) {
            Log.w("Async", "in onPostExecute");

            dialog.setIndeterminate(false);
            dialog.dismiss();
            updateListView(result);
            //unlocks the sensor so it will detect orientation changes again
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    public void displayCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);
        cardDataView.setTypeface(font);
        okButton.setTypeface(font);
        cardDataView.setTextSize(9);
        okButton.setTextSize(9);

        playercardClass card = db_helper.findACard(text);
        String keywords;
        String traits;

        if (!card.getPlayercard_keyword1().equals("")) {
            keywords = card.getPlayercard_keyword1();
            if (!card.getPlayercard_keyword2().equals(""))
            {
                keywords = keywords + ", " + card.getPlayercard_keyword2();
                if (!card.getPlayercard_keyword3().equals(""))
                {
                    keywords = keywords + ", " + card.getPlayercard_keyword3();
                    if (!card.getPlayercard_keyword4().equals(""))
                    {
                        keywords = keywords + card.getPlayercard_keyword4();
                    }
                }
            }
        }
        else { keywords = "None"; }
        if (!card.getPlayercard_trait1().equals("")){

            traits = card.getPlayercard_trait1();
            if (!card.getPlayercard_trait2().equals(""))
            {
                traits = traits + ", " + card.getPlayercard_trait2();
                if (!card.getPlayercard_trait3().equals(""))
                {
                    traits = traits + ", " + card.getPlayercard_trait3();
                    if (!card.getPlayercard_trait4().equals(""))
                    {
                        traits = traits + ", " + card.getPlayercard_trait4();
                    }
                }
            }
        }
        else {traits = "None";}

        final String textForDisplay = "Name: " + card.getPlayercard_name() + "\nNumber: " + card.getPlayercard_no() + "\nCost: " + card.getPlayercard_cost() + "\nQuest: " + card.getPlayercard_ally_quest() + "\nAttack: " + card.getPlayercard_ally_attack() + "\nDefence: " + card.getPlayercard_ally_hp() + "\nHP: " + card.getPlayercard_ally_hp() + "\nKeywords: " + keywords + "\nTraits: " + traits + "\nSpecial Text: " + card.getPlayercard_special_rules();
        cardDataView.setText(textForDisplay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardDetailsDialogue.dismiss();
            }
        });

        String imgPath = db_helper.getImagePath(card.getPlayercard_name(),"player");
        final WebView frame = (WebView) cardDetailsDialogue.findViewById(R.id.cardImageView);
        frame.setBackgroundColor(Color.rgb(151, 199, 188));
        frame.loadDataWithBaseURL(null, imgPath, "text/html", "utf-8", null);

        cardDetailsDialogue.show();
        Window window = cardDetailsDialogue.getWindow();
        window.setLayout(550, 900);
    }

    public void setFonts()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");

        TextView a = (TextView) findViewById(R.id.textView);
        TextView b = (TextView) findViewById(R.id.textView1);


        a.setTypeface(font2);
        b.setTypeface(font2);

    }

}
