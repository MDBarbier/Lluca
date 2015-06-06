package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class QuestBrowserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_browser);

        //loads the available deckparts into list view
        Cursor questlist_cursor = db_helper.getQuestNameCursor();
        ListView questview = (ListView) findViewById(R.id.questListView);
        tableadapter_deckpart_helper adapter = new tableadapter_deckpart_helper(this, questlist_cursor, false);
        questview.setAdapter(adapter);

        questview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView quest_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                String text = quest_name.getText().toString();
                displayCardDialog(text);
            }
        });

        questview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView quest_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                String text = quest_name.getText().toString();
                Intent intent = new Intent(QuestBrowserActivity.this, CardBrowserActivity.class);
                intent.putExtra("type", "particularQuest");
                intent.putExtra("name", text);
                startActivity(intent);
                return true;
            }
        });

        setFonts();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_packs_to_user, menu);
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



    public void displayCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Quest details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

        deckpartClass deckpart = db_helper.findADeckpart(text);

        final String[] questCardArray = db_helper.findRelatedQuestCards(text);
        String tempText = "";

        for (String s: questCardArray)
        {
            tempText = tempText + "\n" + s;
        }

         final String textForDisplay = "Name: " + deckpart.getDeckpart_name() + "\nCycle: " + deckpart.getDeckpart_cycle() + "\nBox: " + deckpart.getDeckpart_box() + "\nQuest cards: " + tempText;

        cardDataView.setText(textForDisplay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardDetailsDialogue.dismiss();
            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        cardDataView.setTypeface(font);
        cardDataView.setTextSize(10);
        okButton.setTypeface(font);
        okButton.setTextSize(8);

        cardDetailsDialogue.show();

    }

    public void setFonts()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");

        TextView a = (TextView) findViewById(R.id.quest_browser_a);
        TextView b = (TextView) findViewById(R.id.quest_browser_b);


        a.setTypeface(font2);
        b.setTypeface(font2);
    }
}
