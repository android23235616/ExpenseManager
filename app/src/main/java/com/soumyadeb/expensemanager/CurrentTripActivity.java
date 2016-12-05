package com.soumyadeb.expensemanager;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;

public class CurrentTripActivity extends AppCompatActivity {
    Button addExpenseButton,viewAllExpenses;
    String from, to, tripID,balance;
    TextView tripSummary,avlBalance;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trip);
        addExpenseButton=(Button)findViewById(R.id.button1);
        viewAllExpenses=(Button)findViewById(R.id.button2);
        tripSummary=(TextView)findViewById(R.id.textView2);
        avlBalance=(TextView)findViewById(R.id.textView3);
        db=openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
        String q="select * from Table1";
        Cursor c;

        c = db.rawQuery(q, null);

        ArrayList<AllTrips> toList=new ArrayList<AllTrips>();

            c.moveToLast();
            tripID=c.getString(0);
            from=c.getString(1);
            to=c.getString(2);
            balance=c.getString(6);


        tripSummary.setText("Trip to: "+to+"   From: "+from);
        avlBalance.setText("Balance Left: Rs. "+balance);

        SharedPreferences sp=getSharedPreferences("TripStatusFile1",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("CT",tripID);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor c=db.rawQuery("select * from Table1 where tripID='"+tripID+"'",null);
        c.moveToFirst();
        balance=c.getString(6);
        avlBalance.setText("Balance Left: Rs. "+balance);
    }

    public void endTrip(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm Action");
        builder.setMessage("Once the trip is ended you wont be able to edit expenses. End the trip to "+to+"?");
        builder.setPositiveButton("END TRIP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp=getSharedPreferences("TripStatusFile1",0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("STATUS","false");
                editor.putString("CT","false");
                editor.apply();
                Toast.makeText(CurrentTripActivity.this,"Trip to "+to+" ended",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void mainMenu(View v)
    {
       finish();
    }

    public void addExpense(View v)
    {
        Intent i=new Intent(this,AddExpenseActivity.class);
        i.putExtra("ID",tripID);
        startActivityForResult(i,0);
    }

    public void allExpenses(View v)
    {
        Intent i=new Intent(this,AllExpensesActivity.class);
        i.putExtra("BAL",balance);
        i.putExtra("ID",tripID);
        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SQLiteDatabase db=openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
        String q="select * from Table1";
        Cursor c;

        c = db.rawQuery(q, null);

        c.moveToLast();
        String lBalance=c.getString(6);
        db.close();
        avlBalance.setText("Balance Left: Rs."+lBalance);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.myoptionsmenu,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item1)
        {
            startActivity(new Intent(this,AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
