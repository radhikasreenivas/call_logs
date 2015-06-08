package com.example.roadsider.saii;

        import android.content.ContentResolver;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.provider.CallLog;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.util.Date;


public class MainActivity extends ActionBarActivity {
    EditText phneno;
    Button signup;
    databasehelper dbhelper;
    SQLiteDatabase db;
    public String num;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public SharedPreferences sharedpreferences;
   // SharedPreferences.Editor editor = sharedpreferences.edit();
    SharedPreferences.Editor editor1;

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isFirstTime;
        SharedPreferences app_preferences;
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        editor1 = app_preferences.edit();
        sharedpreferences=getSharedPreferences(MyPREFERENCES, 0);
        sp=getSharedPreferences(MyPREFERENCES,0);
        isFirstTime = app_preferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            //implement your first time logic
            phneno = (EditText) findViewById(R.id.editText);
            signup = (Button) findViewById(R.id.button);
         //   sharedpreferences = getSharedPreferences(MyPREFERENCES,0);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String myphoneno = phneno.getText().toString();
                    if (phneno.length() < 10) {
                        phneno.requestFocus();
                        phneno.setError("Enter a valid ten digit number");
                    }
                    else{

                       // editor.putString(Name, myphoneno);
                       // editor.apply();
                        editor1.putBoolean("isFirstTime", false);
                        editor1.apply();
                        addLog();
                        String con=getcond();
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString(Name, con);
                        editor.apply();
                       // num= (sharedpreferences.getString(Name, ""));
                       // Toast.makeText(getBaseContext(),"hello"+num,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, nextpage.class));
                        finish();}
                }
            });
        } else {
            //app open directly
           // SharedPreferences.Editor editor = sharedpreferences.edit();
            String conn= (sp.getString(Name, ""));
            Toast.makeText(getBaseContext(),"hi",Toast.LENGTH_LONG).show();
            newaddlog(conn);
            SharedPreferences.Editor editor = sp.edit();
            String con=getcond();
            editor.putString(Name, con);
            editor.apply();
            startActivity(new Intent(MainActivity.this, nextpage.class));
            finish();
        }
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void newaddlog(String Keyname)
    {
       // String ssp;
        dbhelper = new databasehelper(this);
        Cursor cursor;
        ContentResolver cr = getContentResolver();
        String con=CallLog.Calls._ID+"> "+ Keyname;
        cursor = cr.query(
                android.provider.CallLog.Calls.CONTENT_URI, null,con , null,
                android.provider.CallLog.Calls.DATE + " DESC ");
        db = dbhelper.getWritableDatabase();
        // db.delete(databasehelper.TABLE_NAME,null,null);
        int idno=cursor.getColumnIndex(android.provider.CallLog.Calls._ID);
        int calltype = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int numberColumnId = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int durationId = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int dateId = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);

        if (cursor.moveToFirst()) {
           // ssp=cursor.getString(idno);
            do {

                String id=cursor.getString(idno);
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
                values.put(databasehelper.ID, id);
                values.put(databasehelper.pnam, callogstr);
                values.put(databasehelper.phno, contactNumber);
                values.put(databasehelper.pdur, duration);
                values.put(databasehelper.pdate, String.valueOf(dd));

                db.insert(databasehelper.TABLE_NAME, null, values);
                //idno++;
                // Integer.parseInt(sp.getInt().toString());
            } while (cursor.moveToNext());

        }
        cursor.close();
       // return ssp;
    }
    public void addLog() {
        String ssp;
        dbhelper = new databasehelper(this);
        Cursor cursor;
        ContentResolver cr = getContentResolver();
        cursor = cr.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC ");
        db = dbhelper.getWritableDatabase();
        // db.delete(databasehelper.TABLE_NAME,null,null);
        int idno=cursor.getColumnIndex(android.provider.CallLog.Calls._ID);
        int calltype = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int numberColumnId = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int durationId = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int dateId = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);

        if (cursor.moveToFirst()) {
            ssp=cursor.getString(idno);
            do {

                String id=cursor.getString(idno);
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
                values.put(databasehelper.ID, id);
                values.put(databasehelper.pnam, callogstr);
                values.put(databasehelper.phno, contactNumber);
                values.put(databasehelper.pdur, duration);
                values.put(databasehelper.pdate, String.valueOf(dd));

                db.insert(databasehelper.TABLE_NAME, null, values);
                //idno++;
                // Integer.parseInt(sp.getInt().toString());
            } while (cursor.moveToNext());

        }
        cursor.close();
       // return ssp;
    }
   /* public String shared()
    {
        sp = getSharedPreferences(MyPREFERENCE, MODE_PRIVATE);
        editor=sp.edit();
        editor.putString(KeyName, ssp);
        editor.commit();
        channel = (sp.getString(KeyName, ""));
        return KeyName;
    }*/
    public String getcond()
    {
        Cursor cursor;
        String cond=null;
        dbhelper=new databasehelper(this);
        db=dbhelper.getWritableDatabase();
        ContentResolver cr = getContentResolver();
        cursor = cr.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC LIMIT 1 ");
        int idno=cursor.getColumnIndex(android.provider.CallLog.Calls._ID);
        //String cond=cursor.getString(idno);
        if(cursor.moveToFirst())
        {
            cond=cursor.getString(idno);
    }
        cursor.close();
        return cond;
}
}



