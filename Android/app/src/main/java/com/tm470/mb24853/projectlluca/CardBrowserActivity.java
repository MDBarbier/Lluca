package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class CardBrowserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_browser_basic);
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
                //makeMeToast("search",1);
                searchDialog();
                return true;
            case R.id.action_settings:
                makeMeToast("No settings for this page",1);
                //settingsDialog();
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
                    displayCardDialog(text);
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
                    displayEncounterCardDialog(text);
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
                    displayHeroCardDialog(text);
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
                    displayQuestCardDialog(text);
                }
            });

        }
    }

    //loads tableadapter and cursor depending on the parameter sent 1=playercard,2=encountercard,3=herocard,4=questcard
    public void updateListViewWithSearch(int cardType,String searchQuery)
    {
        if (cardType == 1) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.searchQueryCursor(searchQuery);
            tableadapter_playercardlist_helper adapter = new tableadapter_playercardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_card_name);
                    String text = currentCard.getText().toString();
                    displayCardDialog(text);
                }
            });

        }
        if (cardType == 2) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.searchEncounterQueryCursor(searchQuery);
            tableadapter_encountercard_helper adapter = new tableadapter_encountercard_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_encountercard_name);
                    String text = currentCard.getText().toString();
                    displayEncounterCardDialog(text);

                }
            });

        }
        if (cardType == 3) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.searchHeroQueryCursor(searchQuery);
            tableadapter_herocardlist_helper adapter = new tableadapter_herocardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_herocard_name);
                    String text = currentCard.getText().toString();
                    displayHeroCardDialog(text);
                }
            });

        }
        if (cardType == 4) {
            //loads the available cards into list view
            ListView cardList = (ListView) findViewById(R.id.cardListListView);
            Cursor cardlist_cursor = db_helper.searchQuestQueryCursor(searchQuery);
            tableadapter_questcardlist_helper adapter = new tableadapter_questcardlist_helper(this, cardlist_cursor, false);
            cardList.setAdapter(adapter);
            cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentCard = (TextView) view.findViewById(R.id.template_questcard_name);
                    String text = currentCard.getText().toString();
                    displayQuestCardDialog(text);
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
                //makeMeToast("playercard",1);
                Cursor c = db_helper.getPlayerCardListCursor();
                updateListView(1);
                filterDialogBox.dismiss();
            }
        });
        rb2.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                //makeMeToast("encountercard",1);
                Cursor c = db_helper.getEncounterCardListCursor();
                updateListView(2);
                filterDialogBox.dismiss();
            }
        });
        rb3.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                //makeMeToast("herocard",1);
                Cursor c = db_helper.getHeroCardListCursor();
               updateListView(3);
                filterDialogBox.dismiss();
            }
        });
        rb4.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                //makeMeToast("questcard",1);
                Cursor c = db_helper.getQuestCardListCursor();
                updateListView(4);
                filterDialogBox.dismiss();
            }
        });

        filterDialogBox.show();
    }

    public void searchDialog()
    {
        final Dialog searchDialogBox = new Dialog(this);
        searchDialogBox.setContentView(R.layout.custom_dialogue_cardbrowser_searchfilters);
        searchDialogBox.setTitle("Search");
        final Button okButton = (Button) searchDialogBox.findViewById(R.id.okButtonSearch);
        final EditText searchQueryField = (EditText) searchDialogBox.findViewById(R.id.searchQueryField);
        final Spinner spinner = (Spinner) searchDialogBox.findViewById(R.id.typeSpinner);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchQuery = searchQueryField.getText().toString();
                String type = spinner.getSelectedItem().toString();
                //makeMeToast(searchQuery + "," + type,1);

                switch (type){
                    case "Player card":
                        //makeMeToast("p",1);
                        updateListViewWithSearch(1, searchQuery);
                        break;
                    case "Encounter card":
                        //makeMeToast("e",1);
                        updateListViewWithSearch(2, searchQuery);
                        break;
                    case "Hero card":
                        //makeMeToast("h",1);
                        updateListViewWithSearch(3, searchQuery);
                        break;
                    case "Quest card":
                        //makeMeToast("q",1);
                        updateListViewWithSearch(4, searchQuery);
                        break;
                    default:
                        break;
                }
                searchDialogBox.dismiss();
            }
        });

        searchDialogBox.show();
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

                settingsDialogBox.dismiss();
            }
        });

        settingsDialogBox.show();
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

    public void displayHeroCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Card details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

        heroesClass card = db_helper.findAHeroCard(text);
        String keywords;
        String traits;

        if (!card.getHerocard_keyword1().equals("")) {
            keywords = card.getHerocard_keyword1();
            if (!card.getHerocard_keyword2().equals(""))
            {
                keywords = keywords + ", " + card.getHerocard_keyword2();
                if (!card.getHerocard_keyword3().equals(""))
                {
                    keywords = keywords + ", " + card.getHerocard_keyword3();
                    if (!card.getHerocard_keyword4().equals(""))
                    {
                        keywords = keywords + card.getHerocard_keyword4();
                    }
                }
            }
        }
        else { keywords = "None"; }
        if (!card.getHerocard_trait1().equals("")){

            traits = card.getHerocard_trait1();
            if (!card.getHerocard_trait2().equals(""))
            {
                traits = traits + ", " + card.getHerocard_trait2();
                if (!card.getHerocard_trait3().equals(""))
                {
                    traits = traits + ", " + card.getHerocard_trait3();
                    if (!card.getHerocard_trait4().equals(""))
                    {
                        traits = traits + ", " + card.getHerocard_trait4();
                    }
                }
            }
        }
        else {traits = "None";}

        final String textForDisplay = "Name: " + card.getHerocard_name() + "\nNumber: " + card.getHerocard_no()+ "\nThreat: " + card.getHerocard_threat() + "\nQuest: " + card.getHerocard_quest() + "\nAttack: " + card.getHerocard_attack() + "\nDefence: " + card.getHerocard_defence() + "\nHP: " + card.getHerocard_hp()+ "\nKeywords: " + keywords + "\nTraits: " + traits + "\nSpecial Text: " + card.getHerocard_specialrules();
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

    public void displayEncounterCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Card details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

        encountercardClass card = db_helper.findAnEncounterCard(text);
        String keywords;
        String traits;

        if (!card.getEncountercard_keyword1().equals("")) {
            keywords = card.getEncountercard_keyword1();
            if (!card.getEncountercard_keyword2().equals(""))
            {
                keywords = keywords + ", " + card.getEncountercard_keyword2();
                if (!card.getEncountercard_keyword3().equals("")) {
                    keywords = keywords + ", " + card.getEncountercard_keyword3();
                    if (!card.getEncountercard_keyword4().equals(""))
                    {
                        keywords = keywords + card.getEncountercard_keyword4();
                    }
                }
            }
        }
        else { keywords = "None"; }
        if (!card.getEncountercard_trait1().equals("")){

            traits = card.getEncountercard_trait1();
            if (!card.getEncountercard_trait2().equals(""))
            {
                traits = traits + ", " + card.getEncountercard_trait2();
                if (!card.getEncountercard_trait3().equals("")) {
                    traits = traits + ", " + card.getEncountercard_trait3();
                    if (!card.getEncountercard_trait4().equals("")) {
                        traits = traits + ", " + card.getEncountercard_trait4();
                    }
                }
            }
        }
        else {traits = "None";}

        final String textForDisplay = "Name: " + card.getEncountercard_name() + "\nNumber: " + card.getEncountercard_no() + "\nType: " + card.getEncountercard_type() + "\nThreat: " + card.getEncountercard_threat() + "\nEngagement: " + card.getEncountercard_engage() + "\nAttack: " + card.getEncountercard_attack() + "\nDefence: " + card.getEncountercard_defence() + "\nHP: " + card.getEncountercard_hp() + "\nShadow: " + card.getEncountercard_shadow() + "\nKeywords: " + keywords + "\nTraits: " + traits + "\nSpecial Text: " + card.getEncountercard_special_rules();
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

    public void displayQuestCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Card details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

        questcardClass card = db_helper.findAQuestCard(text);
        String keywords;
        String traits;

        if (!card.getQuestcard_keyword1().equals("")) {
            keywords = card.getQuestcard_keyword1();
            if (!card.getQuestcard_keyword2().equals(""))
            {
                keywords = keywords + ", " + card.getQuestcard_keyword2();
                if (!card.getQuestcard_keyword3().equals(""))
                {
                    keywords = keywords + ", " + card.getQuestcard_keyword3();
                    if (!card.getQuestcard_keyword4().equals(""))
                    {
                        keywords = keywords + card.getQuestcard_keyword4();
                    }
                }
            }
        }
        else { keywords = "None"; }
        if (!card.getQuestcard_trait1().equals("")){

            traits = card.getQuestcard_trait1();
            if (!card.getQuestcard_trait2().equals(""))
            {
                traits = traits + ", " + card.getQuestcard_trait2();
                if (!card.getQuestcard_trait3().equals(""))
                {
                    traits = traits + ", " + card.getQuestcard_trait3();
                    if (!card.getQuestcard_trait4().equals(""))
                    {
                        traits = traits + ", " + card.getQuestcard_trait4();
                    }
                }
            }
        }
        else {traits = "None";}

        final String textForDisplay = "Name: " + card.getQuestcard_name() + "\nNumber: " + card.getEncountercard_no() + "\nProgress req: " + card.getQuestcard_progress() + "\nBox: " + card.getQuestcard_box() + "\nPart: " + card.getQuestcard_part() + "\nParent quest: " + card.getEncountercard_deckpart() + "\nKeywords: " + keywords + "\nTraits: " + traits + "\nSpecial Text: " + card.getQuestcard_text();
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
