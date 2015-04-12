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

        //Constructor
        public tableadapter_deckpart_helper(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            //inflates the template xml layout file
            return LayoutInflater.from(context).inflate(R.layout.listviewtemplate,parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            //find the fields to populate
            TextView deckpart_name = (TextView) view.findViewById(R.id.template_deckpart_name);
            TextView deckpart_cycle = (TextView) view.findViewById(R.id.template_deckpart_cycle);
            //Get data from cursor
            String name = cursor.getString(cursor.getColumnIndex("deckpart_name"));
            String cycle = cursor.getString(cursor.getColumnIndex("deckpart_cycle"));
            //Populate views
            deckpart_name.setText(name);
            deckpart_cycle.setText(cycle);
        }

    }


