package com.soumyadeb.expensemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bCurrentTrip,bAllTrips;
    TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bCurrentTrip=(Button)findViewById(R.id.button1);
        bAllTrips=(Button)findViewById(R.id.button2);
        msg=(TextView)findViewById(R.id.textView);
        SharedPreferences sp= getSharedPreferences("TripStatusFile1",0);
        String status=sp.getString("STATUS","null");
        if(status.equals("true"))
        {
            bAllTrips.setVisibility(bCurrentTrip.GONE);
            bCurrentTrip.setVisibility(bCurrentTrip.VISIBLE);
            msg.setText("Managing your current trip expenses. :)");
            startActivity(new Intent(this,CurrentTripActivity.class));
        }
        else {
            bCurrentTrip.setVisibility(bCurrentTrip.GONE);
            bAllTrips.setVisibility(bAllTrips.VISIBLE);
            msg.setText("Ready to start a new trip? ;)");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp= getSharedPreferences("TripStatusFile1",0);
        String status=sp.getString("STATUS","null");
        if(status.equals("true"))
        {   bAllTrips.setVisibility(bAllTrips.GONE);
            bCurrentTrip.setVisibility(bCurrentTrip.VISIBLE);
            msg.setText("Managing your current trip expenses. :)");
        }
        else {
            bCurrentTrip.setVisibility(bCurrentTrip.GONE);
            bAllTrips.setVisibility(bAllTrips.VISIBLE);
            msg.setText("All clear! No trips to manage.");
        }

    }

    public void addTrip(View v)
    {
        startActivity(new Intent(this,AddTripActivity.class));
    }
    public void viewAllTrips(View v)
    {
        SharedPreferences sp =getSharedPreferences("TripStatusFile1", 0);
        String status=sp.getString("TRIP", null);
        if(status==null)
        {
            AlertDialog.Builder builder1=new AlertDialog.Builder(this);
            builder1.setTitle("No trips added yet");
            builder1.setMessage("Your trip list is empty. Do you want to add a new trip?");
            builder1.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this,AddTripActivity.class));
                }
            });
            builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            AlertDialog dialog1=builder1.create();
            dialog1.show();
            //Toast.makeText(this,"No Trips Added Yet",Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(this, AllTripsActivity.class));
        }
    }
    public void currentTrip(View v)
    {
        startActivity(new Intent(this,CurrentTripActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.myoptionsmenu,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                startActivity(new Intent(this,AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}
