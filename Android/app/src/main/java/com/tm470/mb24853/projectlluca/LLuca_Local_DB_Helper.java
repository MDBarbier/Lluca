package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.database.Cursor;

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
        db.execSQL(schema.getPlayerAccountCreate());
        db.execSQL(schema.getDeckpartCreate());
        db.execSQL(schema.getOwnedPacksCreate());
        db.execSQL(schema.getDeckpartPopulate());

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
            db.update(schema.TABLE_NAME_PLAYERS,values,whereClause,null);
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
}