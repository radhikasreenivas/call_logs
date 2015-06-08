package com.example.roadsider.saii;


import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Roadside r on 6/1/2015.
 */
import android.util.Log;

public class databasehelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "logs.db";
        public static final String TABLE_NAME = "call_logs";
        public static final String ID="IDNO";
        public static final String pnam="Type";
        public static final String phno="PhoneNo";
        public static final String pdur="Duration";
        public static final String pdate="Date";
        public static final String create_db=" CREATE TABLE " + databasehelper.TABLE_NAME +" ( " +
               ""+ID+ " TEXT, " +
               ""+pnam+" TEXT , "+
                phno+ " TEXT , "+
               pdur+ " TEXT  , "+
               pdate+ " DATE)";


        public databasehelper(Context context){
                super(context, DATABASE_NAME, null, 2);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL(create_db);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST"+databasehelper.TABLE_NAME);
                onCreate(db);
        }
}




