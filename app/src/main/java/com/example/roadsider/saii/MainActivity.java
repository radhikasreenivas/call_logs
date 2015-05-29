package com.example.roadsider.saii;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.View.*;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          Button btn=(Button)findViewById(R.id.button);
            final EditText et = (EditText)findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String Name=et.getText().toString();
            if(et.length()<10) {
                et.requestFocus();
                et.setError("Enter a valid ten digit number");


            }
                else {
                startActivity(new Intent(MainActivity.this, nextpage.class));
            }
            }
        });
    }


    @Override
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
}
