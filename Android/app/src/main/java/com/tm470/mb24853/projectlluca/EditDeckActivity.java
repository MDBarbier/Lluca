package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class EditDeckActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        Bundle bundle = getIntent().getExtras();
        final String deckname = bundle.getString("deckname");
        final String deckname2 = bundle.getString("deckname") + " current cards";

        TextView deckName = (TextView) findViewById(R.id.editDeckTextName);
        deckName.setText(deckname2);

        //loads the available deckparts into list view
        Cursor cursor = db_helper.getCardsInDeck(deckname);
        //Cursor cursor = db_helper.getPlayerCardListCursor();
        final ListView cards = (ListView) findViewById(R.id.cardsInDecklistView);
        final tableadapter_customdeckcards_helper adapter = new tableadapter_customdeckcards_helper(this, cursor, false);
        cards.setAdapter(adapter);

        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView currentCard = (TextView) view.findViewById(R.id.custom_deck_template_card_name);
                String text = currentCard.getText().toString();
                displayCardDialog(text);

            }
        });

        cards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentCard = (TextView) view.findViewById(R.id.custom_deck_template_card_name);
                String text = currentCard.getText().toString();
                db_helper.deleteCardFromDeck(deckname, text);
                makeMeToast("Card removed", 1);
                adapter.notifyDataSetInvalidated();
                Cursor cursor2 = db_helper.getCardsInDeck(deckname);
                final tableadapter_customdeckcards_helper adapter2 = new tableadapter_customdeckcards_helper(EditDeckActivity.this, cursor2, false);
                cards.setAdapter(adapter2);
                return true;
            }
        });
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
        //loads the card browser screen and passes through the deckname
        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");
        Intent intent = new Intent(this, AddCardsToDeckCardBrowserActivity.class);
        intent.putExtra("deckname", deckname);
        intent.putExtra("typeFilter", "All");
        intent.putExtra("sphere", "All");
        intent.putExtra("cost", "Any");

        startActivity(intent);

    }

    public void viewHeroes(View view)
    {
        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");
        Intent intent = new Intent(this, EditHeroesInDeckActivity.class);
        intent.putExtra("deckname", deckname);
        intent.putExtra("sphere", "All");
        intent.putExtra("threat", "Any");
        startActivity(intent);
    }

    public void viewDemographics(View view)
    {
        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");
        final Dialog deckDemosDialogue = new Dialog(this);
        deckDemosDialogue.setContentView(R.layout.custom_dialogue_demographics);
        deckDemosDialogue.setTitle("Deck demographics");

        final Button okButton = (Button) deckDemosDialogue.findViewById(R.id.okButtonSearch);
        final TextView deckDataView = (TextView) deckDemosDialogue.findViewById(R.id.cardInfo);
        final TextView deckWarningsView = (TextView) deckDemosDialogue.findViewById(R.id.cardInfoWarnings);
        final TextView deckWarningsView2 = (TextView) deckDemosDialogue.findViewById(R.id.cardInfoWarnings2);

        int[] demographics = db_helper.demographics(deckname);
        float avgCost = (float) demographics[8]/demographics[0];
        String warningText;

        String demographicsText = "Total cards in deck: " + demographics[0] + "\nTotal Events:" + demographics[2] + "\nTotal Allies:" + demographics[1] + "\nTotal Attachments:" + demographics[3] + "\nLeadership cards:" + demographics[4] + "\nTactics cards:" + demographics[5] + "\nSpirit cards:" + demographics[6] + "\nLore cards:" + demographics[7] + "\nAverage card cost: " + avgCost;

        if (demographics[0]<50)
        {
            warningText = "WARNING: you have less than 50 cards in your deck!";
            deckWarningsView.setText(warningText);

        }
        if (avgCost>3)
        {
            warningText = "WARNING: Your average cost is high!";
            deckWarningsView2.setText(warningText);
        }

        deckDataView.setText(demographicsText);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deckDemosDialogue.dismiss();
            }
        });

        deckDemosDialogue.show();

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

    public void displayCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Card details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

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

        cardDetailsDialogue.show();
        //makeMeToast(textToToast,1);
    }
}
