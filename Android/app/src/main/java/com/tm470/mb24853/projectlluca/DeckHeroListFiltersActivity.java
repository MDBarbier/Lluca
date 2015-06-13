package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class DeckHeroListFiltersActivity extends ActionBarActivity {


    Spinner spinner2;
    Spinner spinner3;
    String cardSphere;
    String cardThreat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_hero_list_filters);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(169, 186, 182));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        spinner2 = (Spinner) findViewById(R.id.cardSphereFilterSpinner);
        spinner3 = (Spinner) findViewById(R.id.cardThreatFilterSpinner);

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                //makeMeToast(spinner.getSelectedItem().toString(),1);
                cardSphere = spinner2.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing

            }
        });

        spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                //makeMeToast(spinner.getSelectedItem().toString(),1);
                cardThreat = spinner3.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing

            }
        });

        setFonts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deck_card_list_filters, menu);
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

        switch (item.getItemId()) {

            case R.id.action_back:
                //get the deckname that was passed over
                Bundle bundle = getIntent().getExtras();
                String deckname = bundle.getString("deckname");


                //return the selected filters and deckname to the card list screen
                Intent intent = new Intent(this, AddHeroCardsToDeckCardBrowserActivity.class);
                intent.putExtra("deckname", deckname);
                intent.putExtra("sphere", cardSphere);
                intent.putExtra("threat", cardThreat);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void applyFilters(View view)
    {
        //get the deckname that was passed over
        Bundle bundle = getIntent().getExtras();
        String deckname = bundle.getString("deckname");


        //return the selected filters and deckname to the card list screen
        Intent intent = new Intent(this, AddHeroCardsToDeckCardBrowserActivity.class);
        intent.putExtra("deckname", deckname);
        intent.putExtra("sphere", cardSphere);
        intent.putExtra("threat", cardThreat);
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


    public void setFonts()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/aniron.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Fonts/ringbearer.ttf");

        TextView a = (TextView) findViewById(R.id.textView3);
        TextView b = (TextView) findViewById(R.id.threatTV);
        Button d = (Button) findViewById(R.id.applyCardFilterButton);

        final Spinner spinner = (Spinner) findViewById(R.id.cardSphereFilterSpinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.cardThreatFilterSpinner);


        String items1[] = {"All","Leadership","Tactics","Spirit","Lore","Baggins","Neutral"};
        String items2[] = {"Any","Zero","Five","Six","Seven","Eight","Nine","Ten"};
        ArrayAdapter adapter1 = new ArrayAdapter<CharSequence>(this, R.layout.custom_spinner, items1);
        ArrayAdapter adapter2 = new ArrayAdapter<CharSequence>(this, R.layout.custom_spinner, items2);

        adapter1.setDropDownViewResource(R.layout.custom_spinner);
        adapter2.setDropDownViewResource(R.layout.custom_spinner);

        spinner.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);


        a.setTypeface(font2);
        b.setTypeface(font2);

        d.setTypeface(font2);

    }
}
