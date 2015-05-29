package com.example.roadsider.saii;

import android.app.Activity;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;


public class nextpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);
        Cursor mc=managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number=mc.getColumnIndex(CallLog.Calls.NUMBER);
        int date=mc.getColumnIndex(CallLog.Calls.DATE);
        int duration=mc.getColumnIndex(CallLog.Calls.DURATION);
        int type=mc.getColumnIndex(CallLog.Calls.TYPE);
        StringBuilder sb=new StringBuilder();
        while(mc.moveToNext()){
            String phnnum=mc.getString(number);
            String calldur=mc.getString(duration);
            String calldate=mc.getString(date);
            String calltype=mc.getString(type);
            Date d=new Date(date);
            String callogstr="";
            switch(Integer.parseInt(calltype))
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    callogstr="OUTGOING CALLS";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callogstr="INCOMING CALLS";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callogstr="MISSED CALLS";
                    break;

            }
            sb.append(callogstr+"\n");
            sb.append("Phone number"+phnnum+"\n");
            sb.append("call duration"+calldur+"\n");
            sb.append("call date"+d+"\n"+"\n"+"\n");
            sb.append(System.getProperty("line.separator"));
        }

        TextView callDetails=(TextView) findViewById(R.id.callog);
        callDetails.setText(sb.toString());
    }





}
