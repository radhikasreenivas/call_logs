package com.example.roadsider.saii;



import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
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
    ImageButton missed, outgng, incmng,all;
    databasehelper dbhelper;
    public Cursor mycursor;
    public SQLiteDatabase db;
    public StringBuilder sbb = new StringBuilder();
    MainActivity m=new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);
        callDetails = (TextView) findViewById(R.id.callog);
        all=(ImageButton) findViewById(R.id.imageButton);
        outgng = (ImageButton) findViewById(R.id.imageButton3);
        incmng = (ImageButton) findViewById(R.id.imageButton2);
        missed = (ImageButton) findViewById(R.id.imageButton4);
        addLog();
        mycursor= getlog();
        incode(mycursor);
    }

    public void help(View view)
    {
        ImageButton clicked=(ImageButton) view;

        switch (clicked.getId()){
            case R.id.imageButton:
                 mycursor= getlog();
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
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"INCOMING CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }

    public Cursor missed() {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"MISSED CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor outgng()
    {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"OUTGOING CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }




    public void addLog() {

        dbhelper = new databasehelper(this);
        Cursor cursor;
        ContentResolver cr = getContentResolver();
        cursor = cr.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC ");
        db = dbhelper.getWritableDatabase();

        db.delete(databasehelper.TABLE_NAME,null,null);
        int idno=0;
        int calltype = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int numberColumnId = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int durationId = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int dateId = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);

        if (cursor.moveToFirst()) {
            do {
                String cType = cursor.getString(calltype);
                String callogstr = "";
                switch (Integer.parseInt(cType)) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callogstr = "OUTGOING CALLS";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callogstr = "INCOMING CALLS";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        callogstr = "MISSED CALLS";
                        break;
                }
                String contactNumber = cursor.getString(numberColumnId);
                String duration = cursor.getString(durationId);
                String ddd=cursor.getString(dateId);
                Date dd = new Date(Long.valueOf(ddd));
                ContentValues values = new ContentValues();
                values.put(databasehelper.ID,idno);
                values.put(databasehelper.pnam, callogstr);
                values.put(databasehelper.phno, contactNumber);
                values.put(databasehelper.pdur, duration);
                values.put(databasehelper.pdate, String.valueOf(dd));

                db.insert(databasehelper.TABLE_NAME, null, values);
                idno++;
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public Cursor getlog() {
        dbhelper = new databasehelper(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public void incode(Cursor cursor)
    {
        StringBuilder sb=new StringBuilder();
        int idno;
        String type=null;
        String phn=null;
        String dur=null;
        String dates=null;
        if (cursor.moveToFirst()) {
            do {
                //  idno = cursor.getInt(0);
                type = cursor.getString(1);
                phn = cursor.getString(2);
                dur = cursor.getString(3);
                dates = cursor.getString(4);
                sb.append(type).append("\n");
                sb.append("Phone number : ").append(phn).append("\n");
                sb.append("call duration : ").append(dur).append("\n").append("call date : ").append(dates).append("\n").append("\n").append("\n").append(System.getProperty("line.separator"));

            } while (cursor.moveToNext());
        }
        callDetails.setText(sb.toString());
        cursor.close();
    }

    }









