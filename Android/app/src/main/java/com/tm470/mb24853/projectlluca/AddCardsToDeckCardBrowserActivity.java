package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        Bundle bundle = getIntent().getExtras();

        // NOTE when adding another thing here add it to the public void add_cards_to_deck(View view) method in EditDeckActivity.java
        // also need to update the onclick handler for the OK button in the settings dialog
        String deckname = bundle.getString("deckname");
        String typeFilter = bundle.getString("typeFilter");
        String sphere = bundle.getString("sphere");
        String cost = bundle.getString("cost");
        

        Cursor cursor = db_helper.getFilteredPlayerCardListCursor(typeFilter, sphere, cost);
        updateListView(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        switch (item.getItemId()) {
            case R.id.action_search:
                makeMeToast("search",1);

                return true;
            case R.id.action_settings:
                //makeMeToast("settings",1);
                settingsDialog();
                return true;
            case R.id.action_filter:
                //makeMeToast("filters",1);
                filterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //loads tableadapter and cursor depending on the parameter sent 1=playercard,2=encountercard,3=herocard,4=questcard
    public void updateListView(Cursor cardlist_cursor)
    {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            //Cursor cardlist_cursor = db_helper.getPlayerCardListCursor();
            tableadapter_playercardlist_helper adapter = new tableadapter_playercardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);

            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_card_name);
                    String text = currentCard.getText().toString();
                    Bundle bundle = getIntent().getExtras();
                    String deckname = bundle.getString("deckname");
                    db_helper.putCardInDeck(deckname, text);
                    if (db_helper.isCardInDeck(deckname, text)) {
                        String textToToast = "Card name: " + text + " added to deck.";
                        Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();
                    }

                }
            });
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

    //handles the clicking of the action bar filter icon
    public void filterDialog()
    {

        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");
        Intent intent = new Intent(this, DeckCardListFiltersActivity.class);
        intent.putExtra("deckname", deckname);
        startActivity(intent);

    }

    //handles the clicking of the action bar settings icon
    public void settingsDialog()
    {
        final Dialog settingsDialogBox = new Dialog(this);
        settingsDialogBox.setContentView(R.layout.custom_dialogue_settingsfilters);
        settingsDialogBox.setTitle("Adjust settings: ");
        final Button okButton = (Button) settingsDialogBox.findViewById(R.id.okButtonSettings);
        final Switch onlyOwnedSwitch = (Switch) settingsDialogBox.findViewById(R.id.ownedCardsSwitch);

        if (db_helper.getOnlyOwnedStatus())
        {
           onlyOwnedSwitch.setChecked(true);
        }

        onlyOwnedSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                if (onlyOwnedSwitch.isChecked()) {
                    db_helper.setOnlyOwnedStatus(1);
                }
                else
                {
                    db_helper.setOnlyOwnedStatus(0);
                }
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String deckname = bundle.getString("deckname");
                String typeFilter = bundle.getString("typeFilter");
                String sphere = bundle.getString("sphere");
                String cost = bundle.getString("cost");
                Cursor cursor = db_helper.getFilteredPlayerCardListCursor(typeFilter, sphere, cost);
                updateListView(cursor);
                settingsDialogBox.dismiss();
            }
        });

        settingsDialogBox.show();
    }


}
