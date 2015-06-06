package com.tm470.mb24853.projectlluca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Admin on 06/06/2015.
 */
public class customTextViewForListView extends TextView{

    public customTextViewForListView(Context context){
        super(context);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Fonts/aniron.ttf");
        this.setTypeface(font);
    }

    public customTextViewForListView(Context context, AttributeSet attrs){
        super(context, attrs);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Fonts/aniron.ttf");
        this.setTypeface(font);
    }

    public customTextViewForListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Fonts/aniron.ttf");
        this.setTypeface(font);
    }

    protected void onDraw (Canvas canvas)
    {
        super.onDraw(canvas);
    }
}
