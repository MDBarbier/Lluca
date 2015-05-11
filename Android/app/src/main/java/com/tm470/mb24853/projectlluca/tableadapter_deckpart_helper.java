package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Admin on 06/04/2015.
 */


    //Owned packs subclass - These are what will do the work, one per thing that needs binding
    public class tableadapter_deckpart_helper extends CursorAdapter {

    Context context;
    LLuca_Local_DB_Helper db_helper = new LLuca_Local_DB_Helper(context, null, null, 1);

        //Constructor
        public tableadapter_deckpart_helper(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            //inflates the template xml layout file
            return LayoutInflater.from(context).inflate(R.layout.listviewtemplate_deckpart,parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor deckpart_cursor) {

            //find the fields to populate
            TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
            TextView deckpart_cycle = (TextView) view.findViewById(R.id.template_deckpart_box);


            //Get data from cursor
            String name = deckpart_cursor.getString(deckpart_cursor.getColumnIndex("deckpart_name"));
            String cycle = deckpart_cursor.getString(deckpart_cursor.getColumnIndex("deckpart_box"));



            //Populate views
            deckpart_name.setText(name);
            deckpart_cycle.setText(cycle);


        }

    }


