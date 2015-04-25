package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CardBrowserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_browser);

        //loads the available cards into list view
        Cursor cardlist_cursor = db_helper.getPlayerCardListCursor();
        ListView cardList = (ListView) findViewById(R.id.cardListListView);
        tableadapter_cardlist_helper adapter = new tableadapter_cardlist_helper(this, cardlist_cursor, false);
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
                makeMeToast("filters",1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //MDB: applies the configured filters to the list view
    public void applyFilter(View view) {
        //get selected filters
        //apply selected filters to card list
        //refresh listview
        int howBrownDoYouWantIt = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        String textToToast = "Selected filters applied and card list refreshed";
        Toast toast = Toast.makeText(context, textToToast, howBrownDoYouWantIt);
        toast.show();
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

    public void changeCardView(Cursor c)
    {
        //loads the available cards into list view
        ListView cardList = (ListView) findViewById(R.id.cardListListView);
        tableadapter_cardlist_helper adapter = new tableadapter_cardlist_helper(this, c, false);
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
}
