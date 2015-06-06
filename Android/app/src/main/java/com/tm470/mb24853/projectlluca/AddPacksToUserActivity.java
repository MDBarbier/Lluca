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


public class AddPacksToUserActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_packs_to_user);

        //loads the available deckparts into list view
        Cursor deckpart_cursor = db_helper.getDeckpartDataCursor();
        ListView deckparts = (ListView) findViewById(R.id.ownedPackListView);
        tableadapter_deckpart_helper adapter = new tableadapter_deckpart_helper(this, deckpart_cursor, false);
        deckparts.setAdapter(adapter);
        deckparts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                String text = deckpart_name.getText().toString();
                displayCardDialog(text);
            }
        });

        deckparts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                TextView deckpart_box = (TextView) view.findViewById(R.id.template_deckpart_box);
                String text = deckpart_name.getText().toString();
                String text2 = deckpart_box.getText().toString();
                db_helper.setPackOwnership(text, text2);
                if (db_helper.doesPlayerOwnPack(text)) {
                    String textToToast = "Added to collection";
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();
                } else {
                    String textToToast = "Removed from collection";
                    Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();
                }
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

    //MDB: loads the profile screen having saved changes
    public void returnToUserProfile(View view)
    {
        userAccountClass user = db_helper.getCurrentUser();
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("username", user.getUsername());
        startActivity(intent);
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

        TextView a = (TextView) findViewById(R.id.add_packs_a);
        TextView b = (TextView) findViewById(R.id.add_packs_b);
        TextView c = (TextView) findViewById(R.id.return_to_user_profile);

        a.setTypeface(font2);
        b.setTypeface(font2);
        c.setTypeface(font);


    }
}
