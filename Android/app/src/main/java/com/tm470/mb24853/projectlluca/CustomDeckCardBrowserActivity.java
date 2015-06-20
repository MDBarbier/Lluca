package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class CustomDeckCardBrowserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_browser);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));
        Cursor c = db_helper.getPlayerCardListCursor();
        updateListView(1);
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
                makeMeToast("settings",1);
                return true;
            case R.id.action_filter:
                //makeMeToast("filters",1);
                filterDialog();
                return true;
            case R.id.action_home:
                Intent goHome = new Intent(this,MainMenuActivity.class);
                startActivity(goHome);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //loads tableadapter and cursor depending on the parameter sent 1=playercard,2=encountercard,3=herocard,4=questcard
    public void updateListView(int cardType)
    {
        if (cardType == 1) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.getPlayerCardListCursor();
            tableadapter_playercardlist_helper adapter = new tableadapter_playercardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_card_name);
                    String text = currentCard.getText().toString();
                    //Boolean owned = db_helper.doesPlayerOwnPack(text);
                    String textToToast = "Card name: " + text;
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

                }
            });

        }
        if (cardType == 2) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.getEncounterCardListCursor();
            tableadapter_encountercard_helper adapter = new tableadapter_encountercard_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_encountercard_name);
                    String text = currentCard.getText().toString();
                    //Boolean owned = db_helper.doesPlayerOwnPack(text);
                    String textToToast = "Card name: " + text;
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

                }
            });

        }
        if (cardType == 3) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.getHeroCardListCursor();
            tableadapter_herocardlist_helper adapter = new tableadapter_herocardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_herocard_name);
                    String text = currentCard.getText().toString();
                    //Boolean owned = db_helper.doesPlayerOwnPack(text);
                    String textToToast = "Card name: " + text;
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

                }
            });

        }
        if (cardType == 4) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.getQuestCardListCursor();
            tableadapter_questcardlist_helper adapter = new tableadapter_questcardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_questcard_name);
                    String text = currentCard.getText().toString();
                    //Boolean owned = db_helper.doesPlayerOwnPack(text);
                    String textToToast = "Card name: " + text;
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

                }
            });

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

    public void filterDialog()
    {

        final Dialog filterDialogBox = new Dialog(this);
        filterDialogBox.setContentView(R.layout.custom_dialogue_cardfilters);
        filterDialogBox.setTitle("Choose card type to view: ");

        RadioButton rb1 = (RadioButton) filterDialogBox.findViewById(R.id.playercardRadio);
        RadioButton rb2 = (RadioButton) filterDialogBox.findViewById(R.id.encountercardRadio);
        RadioButton rb3 = (RadioButton) filterDialogBox.findViewById(R.id.herocardRadio);
        RadioButton rb4 = (RadioButton) filterDialogBox.findViewById(R.id.questcardRadio);

        rb1.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                makeMeToast("playercard",1);
                Cursor c = db_helper.getPlayerCardListCursor();
                updateListView(1);
                filterDialogBox.dismiss();
            }
        });
        rb2.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                makeMeToast("encountercard",1);
                Cursor c = db_helper.getEncounterCardListCursor();
                updateListView(2);
                filterDialogBox.dismiss();
            }
        });
        rb3.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                makeMeToast("herocard",1);
                Cursor c = db_helper.getHeroCardListCursor();
               updateListView(3);
                filterDialogBox.dismiss();
            }
        });
        rb4.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                makeMeToast("questcard",1);
                Cursor c = db_helper.getQuestCardListCursor();
                updateListView(4);
                filterDialogBox.dismiss();
            }
        });

        filterDialogBox.show();
    }
}
