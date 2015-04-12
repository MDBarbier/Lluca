package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class UserProfileActivity extends ActionBarActivity {

    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        setContentView(R.layout.activity_user_profile);
        TextView userIdTextView = (TextView) findViewById(R.id.userprofile_username);


        LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(this, null, null, 1);
        userAccountClass user = db_helper.getUser(username);

        if (user != null) {
            userIdTextView.setText(user.getUsername());

        } else {
            userIdTextView.setText("No Match Found");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

    //MDB: loads the main menu screen
    public void loadMainMenu (View view)
    {
        Intent intent= new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    //MDB: loads the edit deck screen
    public void loadEditOwnedPacksActivity (View view)
    {
        Intent intent= new Intent(this, EditOwnedPacksActivity.class);
        startActivity(intent);
    }

    //MDB: synchronises account with server
    public void syncWithServer (View view)
    {
        makeMeToast("Server synchronisation not implemented in this release.",1);

    }


    //Logs out the current user
    public void logoutCurrentUser(View view)
    {
        if (db_helper.isAnyUserLoggedIn()){
            userAccountClass user = db_helper.getCurrentUser();
            String userName = user.getUsername();
            db_helper.updateUser(userName, "","",0);

            makeMeToast("Current user has been logged out", 1);

            Intent intent= new Intent(this, MainMenuActivity.class);
            startActivity(intent);
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

}
