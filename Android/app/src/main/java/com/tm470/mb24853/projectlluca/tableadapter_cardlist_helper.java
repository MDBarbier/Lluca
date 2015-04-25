package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Admin on 06/04/2015.
 */


    //Owned packs subclass - These are what will do the work, one per thing that needs binding
    public class tableadapter_cardlist_helper extends CursorAdapter {

    Context context;
    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(context, null, null, 1);

        //Constructor
        public tableadapter_cardlist_helper(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            //inflates the template xml layout file
            return LayoutInflater.from(context).inflate(R.layout.listviewtemplate_cardlist,parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            //find the fields to populate
            TextView card_name = (TextView) view.findViewById(R.id.template_card_name);
            TextView card_no = (TextView) view.findViewById(R.id.template_card_no);

            Log.w("table adapter", "just before reading from cursor");
            //Get data from cursor
            String name = cursor.getString(cursor.getColumnIndex("playercard_name"));
            String no = cursor.getString(cursor.getColumnIndex("playercard_no"));
            //Get data from cursor
            //String name = cursor.getString(cursor.getColumnIndex("deckpart_name"));
            //String cycle = cursor.getString(cursor.getColumnIndex("deckpart_cycle"));



            //Populate views
            card_name.setText(name);
            card_no.setText(no);


        }

    }


