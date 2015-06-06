package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class EditOwnedPacksActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ownedpacks);

        //loads the available deckparts into list view
        Cursor deckpart_cursor = db_helper.getOwnershipAndDeckpartCursor();
        ListView deckparts = (ListView) findViewById(R.id.ownedPackListView);
        final tableadapter_deckpart_helper adapter = new tableadapter_deckpart_helper(this, deckpart_cursor, false);
        deckparts.setAdapter(adapter);


        deckparts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                String text = deckpart_name.getText().toString();
                //Boolean owned = db_helper.doesPlayerOwnPack(text);
                //String textToToast = "Deckpart name: " + text + " Owned: " + owned.toString();
                //Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();
                displayCardDialog(text);

            }
        });

        deckparts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListView deckparts = (ListView) findViewById(R.id.ownedPackListView);
                makeMeToast("Pack removed", 1,"BOTTOM",0,100,18);
                TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                TextView deckpart_box = (TextView) view.findViewById(R.id.template_deckpart_box);
                String box = deckpart_box.getText().toString();
                String deck = deckpart_name.getText().toString();
                db_helper.setPackOwnership(deck,box);
                adapter.notifyDataSetInvalidated();
                Cursor deckpart_cursor = db_helper.getOwnershipAndDeckpartCursor();
                final tableadapter_deckpart_helper adapter = new tableadapter_deckpart_helper(EditOwnedPacksActivity.this, deckpart_cursor, false);
                deckparts.setAdapter(adapter);
                return true;
            }
        });

        setFonts();
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

    //MDB: loads the profile screen having saved changes
    public void saveChanges(View view)
    {


        userAccountClass user = db_helper.getCurrentUser();
        Intent intent = new Intent(this, AddPacksToUserActivity.class);
        intent.putExtra("username", user.getUsername());
        startActivity(intent);
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

    public void displayCardDialog(String text)
    {
        final Dialog cardDetailsDialogue = new Dialog(this);
        cardDetailsDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cardDetailsDialogue.setContentView(R.layout.custom_dialogue_cardetails);
        cardDetailsDialogue.setTitle("Quest details");
        final Button okButton = (Button) cardDetailsDialogue.findViewById(R.id.okButtonSearch);
        final TextView cardDataView = (TextView) cardDetailsDialogue.findViewById(R.id.cardInfo);

        deckpartClass deckpart = db_helper.findADeckpart(text);

        final String textForDisplay = "Name: " + deckpart.getDeckpart_name() + "\nCycle: " + deckpart.getDeckpart_cycle() + "\nBox: " + deckpart.getDeckpart_box();
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

        TextView a = (TextView) findViewById(R.id.owned_packs_a);
        TextView b = (TextView) findViewById(R.id.owned_packs_b);
        TextView c = (TextView) findViewById(R.id.save_deck_button);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font);


    }
}
