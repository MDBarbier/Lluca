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
            cursor = getPlayerCardListCursor();
        }
        else if (typeFilter.equals("All") && sphere.equals("All")){
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            cursor = db.rawQuery(query, null);
        }
        else if (sphere.equals("All") && cost.equals("Any")){
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"";
            cursor = db.rawQuery(query, null);
        }
        else if (cost.equals("Any") && typeFilter.equals("All")){
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            cursor = db.rawQuery(query, null);
        }
        else if (cost.equals("Any"))
        {
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            cursor = db.rawQuery(query, null);
        }
        else if (typeFilter.equals("All"))
        {
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\"";
            cursor = db.rawQuery(query, null);
        }
        else if (sphere.equals("All"))
        {
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            cursor = db.rawQuery(query, null);
        }
        else
        {
            String query = "Select * FROM " + schema.TABLE_NAME_PLAYERCARD + " WHERE " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_TYPE + " = " + "\"" + typeFilter + "\"" + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_SPHERE + " = " + "\"" + sphere + "\""  + " AND " + schema.TABLE_NAME_PLAYERCARD + "." + schema.COLUMN_NAME_PLAYERCARD_COST + " = " + actualCost;
            cursor = db.rawQuery(query, null);

        }

        //TODO

        //copy contents of cursor to temp table

        //query the temp table for cards that fall into owned packs

        //return amended cursor
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

    public void setPackOwnership(String packname)
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
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " where " + schema.COLUMN_NAME_DECK_OWNING_USER + " = '" + user.getUsername() + "' AND " + schema.COLUMN_NAME_DECK_CARD_NAME + " IS NULL"  ;
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
                values.put(schema.COLUMN_NAME_DECK_OWNING_USER, user.getUsername());
                values.put(schema.COLUMN_NAME_DECK_DECK_NAME, deckName);
                values.put(schema.COLUMN_NAME_DECK_CARD_NAME, cardName);

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
        String query = "Select * FROM " + schema.TABLE_NAME_CUSTOM_DECKS + " WHERE " + schema.COLUMN_NAME_DECK_DECK_NAME + " = " + "\"" + deckName + "\"" + " AND " + schema.COLUMN_NAME_DECK_OWNING_USER + " = " + "\"" + user.getUsername() + "\"";
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
}