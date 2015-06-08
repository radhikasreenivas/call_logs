package com.example.roadsider.saii;



import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
public class nextpage extends Activity {
    TextView callDetails;
   // String ssp=null;
    ImageButton missed, outgng, incmng,all;
    databasehelper dbhelper;
    public Cursor mycursor;
    public SQLiteDatabase db;
   // public String i;
   // public String channel;
    public StringBuilder sbb = new StringBuilder();
    MainActivity m=new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);

        //shared prefs

        callDetails = (TextView) findViewById(R.id.callog);
        all=(ImageButton) findViewById(R.id.imageButton);
        outgng = (ImageButton) findViewById(R.id.imageButton3);
        incmng = (ImageButton) findViewById(R.id.imageButton2);
        missed = (ImageButton) findViewById(R.id.imageButton4);
       // addLog();
       // getlog();
       // shared();
        mycursor= getlog();
        incode(mycursor);
       // Toast.makeText(getBaseContext(),channel,Toast.LENGTH_LONG).show();
    }

    public void incode(Cursor cursor)
    {
        StringBuilder sb=new StringBuilder();
        String idno=null;
        String type=null;
        String phn=null;
        String dur=null;
        String dates=null;
        if (cursor.moveToFirst()) {
            do {
                 idno = cursor.getString(0);
                type = cursor.getString(1);
                phn = cursor.getString(2);
                dur = cursor.getString(3);
                dates = cursor.getString(4);
                sb.append(idno).append("\n");
                sb.append(type).append("\n");
                sb.append("Phone number : ").append(phn).append("\n");
                sb.append("call duration : ").append(dur).append("\n").append("call date : ").append(dates).append("\n").append("\n").append("\n").append(System.getProperty("line.separator"));

            } while (cursor.moveToNext());
        }
       // shared();
       // Toast.makeText(getBaseContext(),"sp"+channel ,Toast.LENGTH_LONG).show();
        callDetails.setText(sb.toString());
        cursor.close();
    }
    public void help(View view)
    {
        ImageButton clicked=(ImageButton) view;

        switch (clicked.getId()){
            case R.id.imageButton:
                mycursor=getlog();
                 incode(mycursor);
                break;
            case R.id.imageButton2:
                mycursor = incmng();
                incode(mycursor);
                break;
            case R.id.imageButton3:
                mycursor = outgng();
                incode(mycursor);
            break;
            case R.id.imageButton4:
                mycursor = missed();
                incode(mycursor);
                break;

        }
    }


    public Cursor incmng() {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM call_logs WHERE TYPE=\"INCOMING CALLS\" ORDER BY IDNO DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }

    public Cursor missed() {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM call_logs WHERE TYPE=\"MISSED CALLS\" ORDER BY IDNO DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor outgng()
    {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM call_logs WHERE TYPE=\"OUTGOING CALLS\" ORDER BY IDNO DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }



    public Cursor getlog() {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM call_logs ORDER BY IDNO DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }



    }









