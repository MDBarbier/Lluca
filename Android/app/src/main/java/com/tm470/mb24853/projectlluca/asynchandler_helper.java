package com.tm470.mb24853.projectlluca;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Admin on 06/04/2015.
 */
public class asynchandler_helper extends AsyncTask<URL, Integer, Long>
{
    protected Long doInBackground(URL... urls)
    {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            totalSize ++;
            publishProgress((int) ((i / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //do stuff
    }

    protected void onPostExecute(Long result) {
        //do stuff
    }
}

