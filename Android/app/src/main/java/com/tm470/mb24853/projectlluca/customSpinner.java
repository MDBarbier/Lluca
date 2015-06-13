package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 13/06/2015.
 */
public class customSpinner extends ArrayAdapter<String>{

    public customSpinner(Context context, int resource, List<String> items){
        super(context, resource, items);

    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/aniron.ttf");
        ((TextView) v).setTypeface(font);
        return v;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Fonts/aniron.ttf");
        ((TextView) v).setTypeface(font);
        return v;
    }
}
