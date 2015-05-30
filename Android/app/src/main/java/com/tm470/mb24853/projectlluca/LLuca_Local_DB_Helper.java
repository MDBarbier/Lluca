package com.tm470.mb24853.projectlluca;

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
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Admin on 28/03/2015.
 */
public class LLuca_Local_DB_Helper extends SQLiteOpenHelper
{

    LLuca_Local_DB_schema schema = new LLuca_Local_DB_schema();


    // Database definition (NOTE upon schema change increment db version unless reinstalling app)
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LLuca_Local.db";
    public static final String KEY_ID = "_id";

    //onCreate
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create the tables
        db.execSQL(schema.getPlayerAccountCreate());
        db.execSQL(schema.getDeckpartCreate());
        db.execSQL(schema.getOwnedPacksCreate());
        db.execSQL(schema.getPlayercardCreation());
        db.execSQL(schema.getencountercardCreation());
        db.execSQL(schema.getherocardCreation());
        db.execSQL(schema.getquestcardCreation());
        db.execSQL(schema.getSqlCreateControlDataTable());
        db.execSQL(schema.getCustomDecksCreate());
        db.execSQL(schema.getFilteredPlayercardCreation());
        db.execSQL(schema.getFilteredHerocardCreation());
    }

   //Constructor
    public LLuca_Local_DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_PLAYERS);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_DECKPARTS);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_OWNED_PACKS);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_PLAYERS);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_CONTROL_DATA);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_HEROCARD);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_PLAYERCARD);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_ENCOUNTERCARD);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_QUESTCARD);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_CUSTOM_DECKS);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_FILTERED_PLAYERCARD);
        db.execSQL("DROP TABLE IF IT EXISTS " + schema.TABLE_NAME_FILTERED_HEROCARD);
        onCreate(db);
     }

    //onDowngrade
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    /* Below here is the methods which will respond to requests from the app*/

    //Method to check if a username is in use
    public boolean checkForUser(String userName)
    {
        //create cursor and check the results to see if user exists
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERS + " WHERE " + schema.COLUMN_NAME_USERNAME + " =  \"" + userName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        userAccountClass user = new userAccountClass();

        //get data from cursor - there will only ever be one row with a given username
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setEmailAddress(cursor.getString(2));
            cursor.close();
        }
        else
        {
            user = null;
        }

        if (user == null)
        //if user exists false is returned as another user cannot exist with that username
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }

    }

    //Method to create a new user account and sets them to logged in
    public boolean createUser(userAccountClass user)
    {
        try {

            ContentValues values = new ContentValues();
            values.put(schema.COLUMN_NAME_USERNAME, user.getUsername());
            values.put(schema.COLUMN_NAME_PASSWORD, user.getPassword());
            values.put(schema.COLUMN_NAME_EMAIL, user.getEmailAddress());
            values.put(schema.COLUMN_NAME_LOGGED_IN, 1);


            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(schema.TABLE_NAME_PLAYERS, null, values);
            db.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //Delete a user
    public void deleteUser(String userName)
    {

    }

    //Method to get and return a user account
    public userAccountClass getUser(String userName)
    {
        //create cursor and check the results to see if user exists
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERS + " WHERE " + schema.COLUMN_NAME_USERNAME + " =  \"" + userName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        userAccountClass user = new userAccountClass();

        //get data from cursor - there will only ever be one row with a given username
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            user.setUser_id(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setEmailAddress(cursor.getString(3));
            user.setLogged_in(cursor.getInt(4));
            cursor.close();
        }
        else
        {
            user = null;
        }

        return user;

    }

    public boolean isAnyUserLoggedIn() {
        //create cursor with any records where logged in is 1
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERS + " WHERE " + schema.COLUMN_NAME_LOGGED_IN + " = 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        userAccountClass user = new userAccountClass();

        //get data from cursor - there will only ever be one row with a given username
        if (cursor.moveToFirst())
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }

    public boolean updateUser(String username, String password, String emailAddress, int loggedIn) {

        userAccountClass user = getUser(username);

        if (password.equals("")){
            password = user.getPassword();
        }

        if (emailAddress.equals("")){
            emailAddress = user.getEmailAddress();
        }

        if (user != null) {

            int userID = user.getUserID();
            String whereClause = " " + KEY_ID + " = " + userID + "";
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(schema.COLUMN_NAME_USERNAME, user.getUsername());
            values.put(schema.COLUMN_NAME_PASSWORD, password);
            values.put(schema.COLUMN_NAME_EMAIL, emailAddress);
            values.put(schema.COLUMN_NAME_LOGGED_IN, loggedIn);
            db.update(schema.TABLE_NAME_PLAYERS, values, whereClause, null);
            return true;
        }
        else { return false;}
    }

    //Method to get and return the logged in user account
    public userAccountClass getCurrentUser()
    {
        //create cursor and check the results to see if user exists
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERS + " WHERE " + schema.COLUMN_NAME_LOGGED_IN + " = 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        userAccountClass user = new userAccountClass();

        //get data from cursor
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            user.setUser_id(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setEmailAddress(cursor.getString(3));
            user.setLogged_in(cursor.getInt(4));
            cursor.close();
        }
        else
        {
            user = null;
        }

        return user;

    }

    public Cursor getDeckpartDataCursor()
    {

        String query = "Select * FROM " + schema.TABLE_NAME_DECKPARTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getOwnershipAndDeckpartCursor()
    {
        userAccountClass user = getCurrentUser();
        String query = "Select * FROM " + schema.TABLE_NAME_DECKPARTS + " INNER JOIN " + schema.TABLE_NAME_OWNED_PACKS + " ON " + schema.TABLE_NAME_DECKPARTS + "." + schema.COLUMN_NAME_DECKPART_NAME + " = " + schema.TABLE_NAME_OWNED_PACKS + "." + schema.COLUMN_NAME_PACK_NAME + " WHERE " + schema.TABLE_NAME_OWNED_PACKS + "." + schema.COLUMN_NAME_OWNING_USER + " = " + "\"" + user.getUsername() + "\"" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getPlayerCardListCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    //this method applied the filters a player might choose and copies the cards that match that into filtered_player_cards table (it always empties it before it starts)
    //it then determines whether the user has the the option of only listing cards they own
    //if user has set this option it goes through each card in the filtered_player_cards table and checks if the player owns the box that it comes in
    //if the player does not own the box that contains the card, it is deleted
    //NB this is a big method with a log of processing - should be run async
    public Cursor getFilteredPlayerCardListCursor(String typeFilter, String sphere, String cost)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        int actualCost = 999;

        switch (cost){
            case "One":
                actualCost = 1;
                break;
            case "Two":
                actualCost = 2;
                break;
            case "Three":
                actualCost = 3;
                break;
            case "Four":
                actualCost = 4;
                break;
            case "Five":
                actualCost = 5;
                break;
            case "Zero":
                actualCost = 0;
                break;
            default:
                break;
        }
        if (typeFilter.equals("All") && sphere.equals("All") && cost.equals("Any")) {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards SELECT * FROM player_cards";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (typeFilter.equals("All") && sphere.equals("All")){
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (sphere.equals("All") && cost.equals("Any")){
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (cost.equals("Any") && typeFilter.equals("All")){
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (cost.equals("Any"))
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (typeFilter.equals("All"))
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else if (sphere.equals("All"))
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_player_cards " + "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\""  + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);

        }

        if (getOnlyOwnedStatus())
        {
            //goes through filtered playercard table and removes and that are not owned
            Cursor cursor3;
            String filterOwnedQuery = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_PLAYERCARD;
            cursor3 = db.rawQuery(filterOwnedQuery,null);
            //get data from cursor
            playercardClass card = new playercardClass();

            while (cursor3.moveToNext()) {
                card.setPlayercard_no(cursor3.getInt(1));
                card.setPlayercard_name(cursor3.getString(3));
                card.setPlayercard_box(cursor3.getInt(2));
                if (!doesPlayerOwnCard(card.getPlayercard_name(), "Player")) {
                    String query = "DELETE FROM filtered_player_cards WHERE filtered_player_cards.playercard_name = " + "\"" + card.getPlayercard_name() + "\"";
                    db.execSQL(query);
                }
            }
            cursor3.close();

            //prepares cursor to return
            Cursor cursor2;

            //return amended cursor
            cursor2 = db.rawQuery(filterOwnedQuery,null);
            return cursor2;
        }
        else
        {
            //return original cursor
            return cursor;
        }

    }

    public Cursor getFilteredHeroCardListCursor(String sphere, String threat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        int actualThreat = 999;

        switch (threat){
            case "Six":
                actualThreat = 6;
                break;
            case "Seven":
                actualThreat = 7;
                break;
            case "Eight":
                actualThreat = 8;
                break;
            case "Nine":
                actualThreat = 9;
                break;
            case "Five":
                actualThreat = 5;
                break;
            case "Ten":
                actualThreat = 10;
                break;
            case "Eleven":
                actualThreat = 11;
                break;
            case "Twelve":
                actualThreat = 12;
                break;
            case "Thirteen":
                actualThreat = 13;
                break;
            case "Zero":
                actualThreat = 0;
                break;
            default:
                break;
        }
        if (sphere.equals("All") && threat.equals("Any")) {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_heroes SELECT * FROM heroes";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }

        else if (threat.equals("Any"))
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_heroes " + "Select * FROM " + schema.TABLE_NAME_HEROCARD+ " WHERE " + schema.TABLE_NAME_HEROCARD + "." + schema.COLUMN_NAME_HEROCARD_SPHERE + " = " + "\"" + sphere + "\"";
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }

        else if (sphere.equals("All"))
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_heroes " + "Select * FROM " + schema.TABLE_NAME_HEROCARD+ " WHERE " + schema.TABLE_NAME_HEROCARD+ "." + schema.COLUMN_NAME_HEROCARD_THREAT+ " = " + actualThreat;
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);
        }
        else
        {
            String deleteQuery = "DELETE FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.execSQL(deleteQuery);
            String query2 = "INSERT INTO filtered_heroes " + "Select * FROM " + schema.TABLE_NAME_HEROCARD+ " WHERE " + schema.TABLE_NAME_HEROCARD+ "." + schema.COLUMN_NAME_HEROCARD_SPHERE + " = " + "\"" + sphere + "\""  + " AND " + schema.TABLE_NAME_HEROCARD + "." + schema.COLUMN_NAME_HEROCARD_THREAT + " = " + actualThreat;
            db.execSQL(query2);
            String query3 = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            db.rawQuery(query3,null);
            cursor = db.rawQuery(query3, null);

        }

        if (getOnlyOwnedStatus())
        {
            //goes through filtered heoes table and removes and that are not owned
            Cursor cursor3;
            String filterOwnedQuery = "SELECT * FROM " + schema.TABLE_NAME_FILTERED_HEROCARD;
            cursor3 = db.rawQuery(filterOwnedQuery,null);
            //get data from cursor
            heroesClass card = new heroesClass();

            while (cursor3.moveToNext()) {
                card.setHerocard_no(cursor3.getInt(1));
                card.setHerocard_name(cursor3.getString(3));
                card.setHerocard_box(cursor3.getInt(2));
                if (!doesPlayerOwnCard(card.getHerocard_name(), "Hero")) {
                    String query = "DELETE FROM filtered_heroes WHERE filtered_heroes.herocard_name = " + "\"" + card.getHerocard_name() + "\"";
                    db.execSQL(query);
                }
            }
            cursor3.close();

            //prepares cursor to return
            Cursor cursor2;

            //return amended cursor
            cursor2 = db.rawQuery(filterOwnedQuery,null);
            return cursor2;
        }
        else
        {
            //return original cursor
            return cursor;
        }

    }

    public Cursor getBasicHeroList(String sphere, String threat)
    {
        String query = "Select * FROM " + schema.TABLE_NAME_HEROCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor getEncounterCardListCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_ENCOUNTERCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getHeroCardListCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_HEROCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getQuestCardListCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_QUESTCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getQuestNameCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_DECKPARTS + " WHERE " + schema.COLUMN_NAME_DECKPART_PARENT + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String[] findRelatedQuestCards(String parentQuestName)
    {

        deckpartClass deckpart = findADeckpart(parentQuestName);
        int deckpartId = deckpart.getDeckpart_id();
        String query = "SELECT " + schema.COLUMN_NAME_QUESTCARD_NAME + " FROM " + schema.TABLE_NAME_QUESTCARD + " WHERE " + schema.COLUMN_NAME_QUESTCARD_DECKPART + " = " + deckpartId + " AND " + schema.COLUMN_NAME_QUESTCARD_TYPE + " = \'Quest\' ORDER BY " + schema.COLUMN_NAME_QUESTCARD_PART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        int counter = 1;

        while (cursor.moveToNext())
        {
            counter ++;
        }

        String[] array = new String[counter - 1];
        counter = 0;
        //cursor.moveToFirst();
        cursor = db.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            array[counter] = cursor.getString(0);
            counter ++;
        }


        return array;
    }

    public Cursor getRelatedQuestCardCursor(String parentQuestName)
    {
        deckpartClass deckpart = findADeckpart(parentQuestName);
        int deckpartId = deckpart.getDeckpart_id();
        String query = "SELECT * FROM " + schema.TABLE_NAME_QUESTCARD + " WHERE " + schema.COLUMN_NAME_QUESTCARD_DECKPART + " = " + deckpartId + " AND " + schema.COLUMN_NAME_QUESTCARD_TYPE + " = \'Quest\' ORDER BY " + schema.COLUMN_NAME_QUESTCARD_PART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getOwnedCardListCursor()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public deckpartClass getDeckpartData()
    {
        String query = "Select * FROM " + schema.TABLE_NAME_DECKPARTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        deckpartClass deckPackList = new deckpartClass();

        //get data from cursor
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            deckPackList.setDeckpart_id(cursor.getInt(1));
            deckPackList.setDeckpart_name(cursor.getString(2));
            deckPackList.setDeckpart_box_id(cursor.getInt(3));
            deckPackList.setDeckpart_cycle(cursor.getString(4));
            deckPackList.setDeckpart_box(cursor.getString(5));
            cursor.close();
        }
        else {deckPackList = null;}

        return deckPackList;
    }

    public boolean doesPlayerOwnPack(String packname)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        String query = "Select * FROM " + schema.TABLE_NAME_OWNED_PACKS + " WHERE " + schema.COLUMN_NAME_PACK_NAME + " = " + "\"" + packname + "\"" + " AND " + schema.COLUMN_NAME_OWNING_USER + " = " + "\"" + user.getUsername() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);


        //get data from cursor - there will only ever be one row with a given owning user and a given packname
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
         }

    }

    public boolean doesPlayerOwnBox(String boxname)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        String query = "Select * FROM " + schema.TABLE_NAME_OWNED_PACKS + " WHERE " + schema.COLUMN_NAME_BOX_NAME + " = " + "\"" + boxname + "\"" + " AND " + schema.COLUMN_NAME_OWNING_USER + " = " + "\"" + user.getUsername() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);


        //get data from cursor - there will only ever be one row with a given owning user and a given packname
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }

    }

    public boolean doesPlayerOwnCard(String cardname, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String boxName;
        String query;
        int boxId;

        if (type.equals("Hero"))
        {
            //What box is the player card in?
            query = "Select * from " + schema.TABLE_NAME_HEROCARD+ " WHERE " + schema.COLUMN_NAME_HEROCARD_NAME+ " = " + "\"" + cardname + "\"";
            c = db.rawQuery(query, null);
            heroesClass card = new heroesClass();

            if (c.moveToFirst()) {
                c.moveToFirst();
                card.setHerocard_box(c.getInt(2));
                c.close();
            } else {
                return false;
            }
            boxId = card.getHerocard_box();
        }
        else {
            //What box is the player card in?
            query = "Select * from " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.COLUMN_NAME_PLAYERCARD_NAME + " = " + "\"" + cardname + "\"";
            c = db.rawQuery(query, null);
            playercardClass card = new playercardClass();


            if (c.moveToFirst()) {
                c.moveToFirst();
                card.setPlayercard_box(c.getInt(2));
                c.close();
            } else {
                return false;
            }
            boxId = card.getPlayercard_box();
        }

        //Get box name from box id
        String query2 = "SELECT deckpart_box from Deckparts WHERE deckpart_box_id = " + boxId;
        c = db.rawQuery(query2, null);
        if (c.moveToFirst()) {
            c.moveToFirst();
            boxName = (c.getString(0));
            c.close();
        } else {
            return false;
        }

        //Does player own that box?
        if (doesPlayerOwnBox(boxName)) {
            return true;
        } else
        {
            return false;
        }


    }

    public void setPackOwnership(String packname, String boxname)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        String query = "Select * FROM " + schema.TABLE_NAME_OWNED_PACKS + " WHERE " + schema.COLUMN_NAME_PACK_NAME + " = " + "\"" + packname + "\"" + " AND " + schema.COLUMN_NAME_OWNING_USER + " = " + "\"" + user.getUsername() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            //remove row
            String removeSQL = "DELETE FROM " + schema.TABLE_NAME_OWNED_PACKS + " WHERE " + schema.COLUMN_NAME_PACK_NAME + " = " + "\"" + packname + "\"" + " AND " + schema.COLUMN_NAME_OWNING_USER + " = " + "\"" + user.getUsername() + "\"";
            db.execSQL(removeSQL);
            cursor.close();
            db.close();

        }
        else
        {
            try {

                ContentValues values = new ContentValues();
                values.put(schema.COLUMN_NAME_OWNING_USER, user.getUsername());
                values.put(schema.COLUMN_NAME_PACK_NAME, packname);
                values.put(schema.COLUMN_NAME_BOX_NAME, boxname);

                db.insert(schema.TABLE_NAME_OWNED_PACKS, null, values);
                db.close();

            }
            catch (Exception e)
            {
                //do nothing
                cursor.close();
                db.close();
            }
            cursor.close();
        }

    }

    //get population status
    public boolean getPopulationStatus() {
        String query = "Select * FROM " + schema.TABLE_NAME_CONTROL_DATA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        int populationStatus = 0;

        //get data from cursor - there will only ever be one row with a given owning user and a given packname
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            populationStatus = (cursor.getInt(0));
            if (populationStatus == 1) {
                cursor.close();
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return false;
        }

    }

    //set population status
    public void setPopulationStatus(int status) {


            try {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(schema.COLUMN_NAME_POPULATED, status);
                db.update(schema.TABLE_NAME_CONTROL_DATA, values, null, null);
                Log.w("pop status", "Setting to 1");
            }
            catch (Exception e)
            {
                Log.w("pop status", e.toString());
            }
     }

    //get population status
    public boolean getOnlyOwnedStatus() {
        String query = "Select * FROM " + schema.TABLE_NAME_CONTROL_DATA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        int onlyOwnedStatus = 0;

        //get data from cursor - there will only ever be one row with a given owning user and a given packname
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            onlyOwnedStatus = (cursor.getInt(1));
            if (onlyOwnedStatus == 1) {
                cursor.close();
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return false;
        }

    }

    //set population status
    public void setOnlyOwnedStatus(int status) {


        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(schema.COLUMN_NAME_ONLYOWNED, status);
            db.update(schema.TABLE_NAME_CONTROL_DATA, values, null, null);
            Log.w("only owned status", "Setting to 1");
        }
        catch (Exception e)
        {
            Log.w("only owned", e.toString());
        }
    }

    //creates a new custom deck
    public void createCustomDeck(userAccountClass user, String deckName)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(schema.COLUMN_NAME_DECK_OWNING_USER, user.getUsername());
            values.put(schema.COLUMN_NAME_DECK_DECK_NAME, deckName);

            db.insert(schema.TABLE_NAME_CUSTOM_DECKS, null, values);
            db.close();
        }
        catch(Exception e)
        {
            //do nothing
        }
    }

    public void deleteCustomDeck(String deckName)
    {
        userAccountClass user = getCurrentUser();
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "DELETE FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = '" + deckName + "' AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = '" + user.getUsername() + "'";
        db.execSQL(sqlDelete);
        db.close();
    }

    //gets entries in the custom deck table which match the current player
    public Cursor getCustomDeckNames()
    {
        userAccountClass user = getCurrentUser();
        //String query = "Select distinct " + schema.COLUMN_NAME_DECK_DECK_NAME + " FROM " + schema.TABLE_NAME_CUSTOM_DECKS;
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " where " + schema.COLUMN_NAME_DECK_OWNING_USER + " = '" + user.getUsername() + "' AND " + schema.COLUMN_NAME_DECK_CARD_NAME + " IS NULL ORDER BY " + schema.COLUMN_NAME_DECK_DECK_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);

        return cursor;
    }

    public boolean isCardInDeck(String deckname, String cardName)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckname + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\" AND " + schema.COLUMN_NAME_DECK_CARD_NAME+ " = " + "\"" + cardName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);

       if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }

    }

    public void putCardInDeck(String deckName, String cardName)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        SQLiteDatabase db = this.getWritableDatabase();

        try {
                ContentValues values = new ContentValues();
                playercardClass card = findACard(cardName);
                values.put(schema.COLUMN_NAME_DECK_OWNING_USER, user.getUsername());
                values.put(schema.COLUMN_NAME_DECK_DECK_NAME, deckName);
                values.put(schema.COLUMN_NAME_DECK_CARD_NAME, cardName);
                values.put(schema.COLUMN_NAME_DECK_CARD_TYPE, card.getPlayercard_type());

                db.insert(schema.TABLE_NAME_CUSTOM_DECKS, null, values);
                db.close();

            }
            catch (Exception e)
            {
                //do nothing

                db.close();
            }
    }

    public void putHeroCardInDeck(String deckName, String cardName)
    {
        //get current user
        userAccountClass user = getCurrentUser();

        //cycle through the owned_pack table
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(schema.COLUMN_NAME_DECK_OWNING_USER, user.getUsername());
            values.put(schema.COLUMN_NAME_DECK_DECK_NAME, deckName);
            values.put(schema.COLUMN_NAME_DECK_CARD_NAME, cardName);
            values.put(schema.COLUMN_NAME_DECK_CARD_TYPE, "Hero");

            db.insert(schema.TABLE_NAME_CUSTOM_DECKS, null, values);
            db.close();

        }
        catch (Exception e)
        {
            //do nothing

            db.close();
        }
    }

    public Cursor getCardsInDeck(String deckName)
    {
        userAccountClass user = getCurrentUser();
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckName + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\"" + " AND " + schema.COLUMN_NAME_DECK_CARD_TYPE + " IS NOT \"" + "Hero" + "\" ORDER BY " + schema.COLUMN_NAME_DECK_CARD_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getHeroCardsInDeck(String deckName, String cardType)
    {
        userAccountClass user = getCurrentUser();
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckName + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\"" + " AND " + schema.COLUMN_NAME_DECK_CARD_TYPE + " = \"" + cardType + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deleteCardFromDeck(String deckName, String cardName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        userAccountClass user = getCurrentUser();

        try {

                String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckName + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\" AND " + schema.COLUMN_NAME_DECK_CARD_NAME+ " = " + "\"" + cardName + "\"";
                cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst())
                {
                    //remove row
                    String removeSQL = "DELETE FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckName + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\" AND " + schema.COLUMN_NAME_DECK_CARD_NAME+ " = " + "\"" + cardName + "\"";
                    db.execSQL(removeSQL);
                    cursor.close();
                    db.close();

                }
        } catch (Exception e) {
                //do nothing
                db.close();
            }


    }

    public Cursor searchQueryCursor(String searchTerm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM player_cards WHERE playercard_name LIKE \"%" + searchTerm + "%\"";
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor searchHeroQueryCursor(String searchTerm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM heroes WHERE herocard_name LIKE \"%" + searchTerm + "%\"";
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public Cursor searchEncounterQueryCursor(String searchTerm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM encounter_cards WHERE encountercard_name LIKE \"%" + searchTerm + "%\"";
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public Cursor searchQuestQueryCursor(String searchTerm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM quest_cards WHERE questcard_name LIKE \"%" + searchTerm + "%\"";
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public playercardClass findACard(String name)
    {
        playercardClass card = new playercardClass();
        card.setPlayercard_name(name);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.COLUMN_NAME_PLAYERCARD_NAME + " = \"" + card.getPlayercard_name() + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            card.setPlayercard_no(cursor.getInt(1));
            card.setPlayercard_unique(cursor.getInt(4));
            card.setPlayercard_type(cursor.getString(5));
            card.setPlayercard_cost(cursor.getInt(6));
            card.setPlayercard_sphere(cursor.getString(7));
            card.setPlayercard_special_rules(cursor.getString(8));
            card.setPlayercard_count(cursor.getInt(9));
            card.setPlayercard_keyword1(cursor.getString(10));
            card.setPlayercard_keyword2(cursor.getString(11));
            card.setPlayercard_keyword3(cursor.getString(12));
            card.setPlayercard_keyword4(cursor.getString(13));
            card.setPlayercard_trait1(cursor.getString(14));
            card.setPlayercard_trait2(cursor.getString(15));
            card.setPlayercard_trait3(cursor.getString(16));
            card.setPlayercard_trait4(cursor.getString(17));
            card.setPlayercard_ally_quest(cursor.getInt(18));
            card.setPlayercard_ally_attack(cursor.getInt(19));
            card.setPlayercard_ally_defence(cursor.getInt(20));
            card.setPlayercard_ally_hp(cursor.getInt(21));
            card.setPlayercard_secrecy(cursor.getInt(22));
        }
        return card;
    }

    public heroesClass findAHeroCard(String name)
    {
        heroesClass card = new heroesClass();
        card.setHerocard_name(name);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_HEROCARD + " WHERE " + schema.COLUMN_NAME_HEROCARD_NAME+ " = \"" + card.getHerocard_name()+ "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            card.setHerocard_no(cursor.getInt(1));
            card.setHerocard_box(cursor.getInt(2));
            card.setHerocard_threat(cursor.getInt(4));
            card.setHerocard_quest(cursor.getInt(5));
            card.setHerocard_attack(cursor.getInt(6));
            card.setHerocard_defence(cursor.getInt(7));
            card.setHerocard_hp(cursor.getInt(8));
            card.setHerocard_name(cursor.getString(3));
            card.setHerocard_sphere(cursor.getString(9));
            card.setHerocard_specialrules(cursor.getString(10));
            card.setHerocard_keyword1(cursor.getString(11));
            card.setHerocard_keyword2(cursor.getString(12));
            card.setHerocard_keyword3(cursor.getString(13));
            card.setHerocard_keyword4(cursor.getString(14));
            card.setHerocard_trait1(cursor.getString(15));
            card.setHerocard_trait2(cursor.getString(16));
            card.setHerocard_trait3(cursor.getString(17));
            card.setHerocard_trait4(cursor.getString(18));
        }
        return card;
    }

    public questcardClass findAQuestCard(String name)
    {
        questcardClass card = new questcardClass();
        card.setQuestcard_name(name);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_QUESTCARD + " WHERE " + schema.COLUMN_NAME_QUESTCARD_NAME + " = \"" + card.getQuestcard_name() + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            card.setQuestcard_no(cursor.getInt(1));
            card.setQuestcard_box(cursor.getInt(2));
            card.setQuestcard_deckpart(cursor.getInt(3));
            card.setQuestcard_name(cursor.getString(4));
            card.setQuestcard_type(cursor.getString(5));
            card.setQuestcard_part(cursor.getInt(6));
            card.setQuestcard_progress(cursor.getInt(7));
            card.setQuestcard_cost(cursor.getInt(8));
            card.setQuestcard_unique(cursor.getInt(9));
            card.setQuestcard_text(cursor.getString(10));
            card.setQuestcard_count(cursor.getInt(11));
            card.setQuestcard_keyword1(cursor.getString(12));
            card.setQuestcard_keyword2(cursor.getString(13));
            card.setQuestcard_keyword3(cursor.getString(14));
            card.setQuestcard_keyword4(cursor.getString(15));
            card.setQuestcard_trait1(cursor.getString(16));
            card.setQuestcard_trait2(cursor.getString(17));
            card.setQuestcard_trait3(cursor.getString(18));
            card.setQuestcard_trait4(cursor.getString(19));
            card.setQuestcard_hp(cursor.getInt(16));
            card.setQuestcard_attack(cursor.getInt(17));
            card.setQuestcard_defence(cursor.getInt(18));
            card.setQuestcard_secrecy(cursor.getInt(19));
            card.setQuestcard_vp(cursor.getInt(20));
        }
        return card;
    }

    public deckpartClass findADeckpart(String name)
    {
        deckpartClass deckpart = new deckpartClass();
        deckpart.setDeckpart_name(name);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_DECKPARTS + " WHERE " + schema.COLUMN_NAME_DECKPART_NAME + " = \"" + deckpart.getDeckpart_name() + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            deckpart.setDeckpart_id(cursor.getInt(1));
            deckpart.setDeckpart_name(cursor.getString(2));
            deckpart.setDeckpart_box_id(cursor.getInt(3));
            deckpart.setDeckpart_cycle(cursor.getString(4));
            deckpart.setDeckpart_box(cursor.getString(5));
            deckpart.setDeckpart_parent(cursor.getInt(6));

        }
        return deckpart;
    }

    public encountercardClass findAnEncounterCard(String name)
    {
        encountercardClass card = new encountercardClass();
        card.setEncountercard_name(name);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_ENCOUNTERCARD+ " WHERE " + schema.COLUMN_NAME_ENCOUNTERCARD_NAME+ " = \"" + card.getEncountercard_name() + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            card.setEncountercard_no(cursor.getInt(1));
            card.setEncountercard_box(cursor.getInt(2));
            card.setEncountercard_name(cursor.getString(3));
            card.setEncountercard_type(cursor.getString(4));
            card.setEncountercard_threat(cursor.getInt(5));
            card.setEncountercard_engage(cursor.getInt(6));
            card.setEncountercard_attack(cursor.getInt(7));
            card.setEncountercard_defence(cursor.getInt(8));
            card.setEncountercard_hp(cursor.getInt(9));
            card.setEncountercard_special_rules(cursor.getString(10));
            card.setEncountercard_shadow(cursor.getString(11));
            card.setEncountercard_trait1(cursor.getString(14));
            card.setEncountercard_trait2(cursor.getString(15));
            card.setEncountercard_trait3(cursor.getString(16));
            card.setEncountercard_trait4(cursor.getString(17));
            card.setEncountercard_keyword1(cursor.getString(18));
            card.setEncountercard_keyword2(cursor.getString(19));
            card.setEncountercard_keyword3(cursor.getString(20));
            card.setEncountercard_keyword4(cursor.getString(21));
            card.setEncountercard_vp(cursor.getInt(13));

        }
        return card;
    }

    public boolean areThereSpares(String name, String type, String deckname)
    {
        int copiesAvailable;
        int copiesUsed = 0;

        if (type.equals("Hero")) {

            if (isCardInDeck(deckname, name)) {
                copiesUsed = 1;
            }

            if (copiesUsed == 1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            playercardClass card = findACard(name);
            copiesAvailable = card.getPlayercard_count();
            if (howManyCopiesInDeck(deckname, card.getPlayercard_name()) >= copiesAvailable)
            {
            return false;
            }
            else
            {
                return true;
            }
        }
    }

    public int howManyHeroes(String deckname)
    {
        int numberHeroes = 0;

        heroesClass card = new heroesClass();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_CARD_TYPE + " = \"Hero\"";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext())
        {
           numberHeroes++;
        }
        return numberHeroes;
    }

    public int howManyCopiesInDeck(String deckname, String cardname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int howManyInDeck = 0;
        String query = "SELECT * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_CARD_NAME + " = \"" + cardname + "\" AND " + schema.COLUMN_NAME_DECK_DECK_NAME + " = \"" + deckname + "\"";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            howManyInDeck++;
        }

        return howManyInDeck;
    }

    public int[] demographics(String deckname)
    {
        int[] demographics = new int[9];
        SQLiteDatabase db = this.getWritableDatabase();
        playercardClass card;
        String query = "SELECT * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = \"" + deckname + "\" AND " + schema.COLUMN_NAME_DECK_CARD_TYPE + " IS NOT NULL AND " + schema.COLUMN_NAME_DECK_CARD_TYPE + " IS NOT \"Hero\"";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            card = findACard(cursor.getString(2));
            demographics[0]++;


            switch (card.getPlayercard_type())
            {
                    case "Ally":
                        demographics[1]++;
                        break;
                    case "Event":
                        demographics[2]++;
                        break;
                    case "Attachment":
                        demographics[3]++;
                        break;
                    default:
                        break;

            }

            switch (card.getPlayercard_sphere())
            {
                case "Leadership":
                    demographics[4]++;
                    break;
                case "Tactics":
                    demographics[5]++;
                    break;
                case "Spirit":
                    demographics[6]++;
                    break;
                case "Lore":
                    demographics[7]++;
                    break;
                default:
                    break;
            }
            demographics[8] = demographics[8] + card.getPlayercard_cost();
        }

        return demographics;
    }
}