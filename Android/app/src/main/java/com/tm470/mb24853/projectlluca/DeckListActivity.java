package com.tm470.mb24853.projectlluca;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DeckListActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);
        updateListView();
        setFonts();
    }


    //loads tableadapter and cursor depending on the parameter sent 1=playercard,2=encountercard,3=herocard,4=questcard
    public void updateListView()
    {

            //loads the available cards into list view
            ListView deckList = (ListView) findViewById(R.id.deckListView);
            Cursor decklist_cursor = db_helper.getCustomDeckNames();

            tableadapter_custom_deck_helper adapter = new tableadapter_custom_deck_helper(this, decklist_cursor, false);
            deckList.setAdapter(adapter);
            deckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    TextView currentDeck = (TextView) view.findViewById(R.id.customDeckName);
                    String text = currentDeck.getText().toString();
                    //String textToToast = "Deck name: " + text;
                    //Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DeckListActivity.this, EditDeckActivity.class);
                    intent.putExtra("deckname", text);
                    startActivity(intent);
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_list, menu);
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

    //MDB: loads the main menu
    public void loadMainMenuActivity (View view)
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    //MDB: loads the create deck screen
    public void loadCreateDeckActivity (View view)
    {
        createDeckDialog();
    }

    public void createDeckDialog()
    {
        final Dialog createDeckDialogBox = new Dialog(this);
        createDeckDialogBox.setContentView(R.layout.custom_dialogue_createdeck);
        createDeckDialogBox.setTitle("New deck");

        Button dialogOKButton = (Button) createDeckDialogBox.findViewById(R.id.createDeckOKButton);

        dialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) createDeckDialogBox.findViewById(R.id.createDeckEditDeckName);
                String name = tv.getText().toString();
                createNewDeck(name);
               updateListView();
                createDeckDialogBox.dismiss();
                makeMeToast("Deck created. Now tap your deck's name to add cards and heroes!",1,"TOP",0,300,25);
            }
        });

        createDeckDialogBox.show();
    }

    public void createNewDeck(String deckName)
    {
        //get user
        userAccountClass user = db_helper.getCurrentUser();

        //call the creation method in db helper with the username and deckname
        db_helper.createCustomDeck(user, deckName);
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
        toast.show();
    }

    public void setFonts()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");

        TextView a = (TextView) findViewById(R.id.create_deck_button);
        TextView b = (TextView) findViewById(R.id.button3);

        a.setTypeface(font);
        b.setTypeface(font);
    }
}
