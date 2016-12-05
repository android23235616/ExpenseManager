package com.soumyadeb.expensemanager;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllTripsActivity extends ListActivity {
    String to = null, from = null, startDate = null, tripID = null, balance = null,budget=null;
    boolean ct;
    ArrayList<AllTrips> toList = new ArrayList<AllTrips>();
    AllTripsAdapter adapter;
    static int pos;
    Cursor c;
    static byte count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ImageView iv=(ImageView) findViewById(R.id.imageView1);

        SharedPreferences sp1=getSharedPreferences("TripStatusFile1",0);
        String currTripID=sp1.getString("CT",null);
        SQLiteDatabase db = openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
        String q = "select * from Table1 order by startDate asc";

        c = db.rawQuery(q, null);

        while (c.moveToNext()) {

            tripID = c.getString(0);
            from = c.getString(1);
            to = c.getString(2);
            startDate = c.getString(3);
            budget=c.getString(5);
            balance = c.getString(6);
            if (tripID.equals(currTripID)) ct=true;
            else ct=false;
            toList.add(new AllTrips(from, to, startDate, tripID, balance,budget,ct));
            ++count;
        }
        db.close();

        if(count==0)
        {
            SharedPreferences sp=getSharedPreferences("TripStatusFile1",0);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("STATUS","false");
            editor.putString("TRIP","true");
            editor.apply();
            AlertDialog.Builder builder1=new AlertDialog.Builder(AllTripsActivity.this);
            builder1.setTitle("No trips added yet");
            builder1.setMessage("Your trip list is empty");
            builder1.setPositiveButton("HOME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            builder1.setNegativeButton("START A TRIP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AllTripsActivity.this,AddTripActivity.class));
                    finish();
                }
            });

            AlertDialog dialog1=builder1.create();
            dialog1.show();
                        //Toast.makeText(this,"Trip List Empty",Toast.LENGTH_SHORT).show();
        }

        adapter = new AllTripsAdapter(this, R.layout.activity_view_all_trips, toList);
        setListAdapter(adapter);
        registerForContextMenu(getListView());

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final SQLiteDatabase db=openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        pos=info.position;
        final AllTrips selectedTrip=toList.get(info.position);
        if (item.getTitle().equals("Edit")) {
            Intent i =new Intent(AllTripsActivity.this,EditTripActivity.class);
            i.putExtra("ID",selectedTrip.getTripId());
            startActivity(i);
            finish();

        }
        else if (item.getTitle().equals("Delete")) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete Trip");
            builder.setMessage("Are you sure you want to delete the trip to "+to+"?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.execSQL("delete from Table1 where tripID='"+selectedTrip.getTripId()+"'");
                    toList.remove(info.position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AllTripsActivity.this,"Trip Deleted",Toast.LENGTH_LONG).show();
                    --count;
                    if(count==0)
                    {
                        SharedPreferences sp=getSharedPreferences("TripStatusFile1",0);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("STATUS","false");
                        editor.putString("TRIP","true");
                        editor.apply();
                        AlertDialog.Builder builder1=new AlertDialog.Builder(AllTripsActivity.this);
                        builder1.setTitle("No trips added yet");
                        builder1.setMessage("Your trip list is empty");
                        builder1.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder1.setPositiveButton("START A TRIP", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(AllTripsActivity.this,AddTripActivity.class));
                                finish();
                            }
                        });
                        AlertDialog dialog1=builder1.create();
                        dialog1.show();
                        //Toast.makeText(AllTripsActivity.this,"Trip List Empty",Toast.LENGTH_LONG).show();
                    }

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

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i=new Intent(this,TripSummaryActivity.class);
        i.putExtra("ID",toList.get(position).getTripId());
        startActivity(i);
    }


}