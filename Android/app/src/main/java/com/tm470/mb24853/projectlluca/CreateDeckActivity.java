package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class CreateDeckActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_deck, menu);
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
}
