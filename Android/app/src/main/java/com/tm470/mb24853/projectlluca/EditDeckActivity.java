package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EditDeckActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");

        TextView deckName = (TextView) findViewById(R.id.editDeckTextName);
        deckName.setText(deckname);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_deck, menu);
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

    //MDB: saves the deck and returns to deck list
    public void saveCreatedDeck(View view)
    {
        //save the deck here
        Intent intent= new Intent(this, DeckListActivity.class);
        startActivity(intent);
    }

    //MDB: applies the configured filters to the list view
    public void applyFilter(View view)
    {
        //get selected filters
        //apply selected filters to card list
        //refresh listview
        int howBrownDoYouWantIt = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        String textToToast = "Selected filters applied and card list refreshed";
        Toast toast = Toast.makeText(context, textToToast, howBrownDoYouWantIt);
        toast.show();
    }

    public void delete_deck(View view)
    {
        final Dialog deleteDeckDialogBox = new Dialog(this);
        deleteDeckDialogBox.setContentView(R.layout.custom_dialogue_deletedeck);
        deleteDeckDialogBox.setTitle("Delete deck?");

            Button dialogYesButton = (Button) deleteDeckDialogBox.findViewById(R.id.deleteDeckYES);
            Button dialogNoButton = (Button) deleteDeckDialogBox.findViewById(R.id.deleteDeckNO);

            dialogYesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = getIntent().getExtras();
                    String deckname = bundle.getString("deckname");
                    makeMeToast("Deck '" + deckname + "' Deleted.",1);
                    deleteDeckFromDB(deckname);
                    Intent intent = new Intent(EditDeckActivity.this, DeckListActivity.class);
                    startActivity(intent);
                    deleteDeckDialogBox.dismiss();
                }
            });

            dialogNoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeMeToast("Operation cancelled",1);
                    deleteDeckDialogBox.dismiss();
                }
            });

        deleteDeckDialogBox.show();
    }

    public void deleteDeckFromDB(String deckname)
    {
        db_helper.deleteCustomDeck(deckname);
    }

    public void add_cards_to_deck(View view)
    {

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
