package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        tableadapter_deckpart_helper adapter = new tableadapter_deckpart_helper(this, deckpart_cursor, false);
        deckparts.setAdapter(adapter);
        deckparts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
                //String text = deckpart_name.getText().toString();
                //Boolean owned = db_helper.doesPlayerOwnPack(text);
                //String textToToast = "Deckpart name: " + text + " Owned: " + owned.toString();
                //Toast.makeText(getBaseContext(), textToToast, Toast.LENGTH_SHORT).show();

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
