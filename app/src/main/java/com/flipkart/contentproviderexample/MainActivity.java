package com.flipkart.contentproviderexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getCalllogs(View view) {
//        Uri for th content provider
//        If I am exposing a content provider from my class, then the uri needs to be full uri.
//        Uri browserUri = Uri.parse("content://browser/bookmarks");
        Uri callLogUri = CallLog.Calls.CONTENT_URI;

//        columns exposed by the content provicer table
        String[] columns = {CallLog.Calls._ID, CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};

        Cursor cursor = getContentResolver().query(callLogUri, columns, null, null, null);

        if (cursor.getCount() > 0) {
//            move to first
            cursor.moveToFirst();
//            Iterating through the cursor without an Adapter
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            do {
                String date = cursor.getString(dateIndex);
                String number = cursor.getString(numberIndex);
                String duration = cursor.getString(durationIndex);

                Log.i(TAG, date + " " + number + " " + duration);

                cursor.moveToNext();
            } while (!cursor.moveToLast());
        }
        cursor.close();
    }

    public void getRSSFeed(View view) {
        Uri rssUri = Uri.parse("content://com.flipkart.rssreader");
        Cursor cursor = getContentResolver().query(rssUri,null,null,null,null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            int titleIndex = cursor.getColumnIndex("title");
            do {
                String title = cursor.getString(titleIndex);
                Log.i(TAG, title);

                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
    }
}
