package com.soumyadeb.expensemanager;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddTripActivity extends AppCompatActivity {
    EditText tripID,from,to,startDate,endDate,approvedBudget;
    static int index=0;
    static int dd=0,mm=9,yyyy=2016;
    SQLiteDatabase db;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat df = new SimpleDateFormat("dd");
        dd = Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        df=new SimpleDateFormat("M");
        mm=Integer.parseInt(df.format(Calendar.getInstance().getTime()))-1;
        df=new SimpleDateFormat("yyyy");
        yyyy=Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        setContentView(R.layout.activity_add_trip);
        tripID=(EditText)findViewById(R.id.editText);
        from=(EditText)findViewById(R.id.editText1);
        to=(EditText)findViewById(R.id.editText2);
        startDate=(EditText)findViewById(R.id.editText3);

            startDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class MyDateChooser implements DatePickerDialog.OnDateSetListener
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            startDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    }


                    DatePickerDialog dialog= new DatePickerDialog(AddTripActivity.this,new MyDateChooser(),yyyy,mm,dd);
                    dialog.show();

                }
            });

        endDate=(EditText)findViewById(R.id.editText4);


            endDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class MyDateChooser implements DatePickerDialog.OnDateSetListener
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            endDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    }


                    DatePickerDialog dialog= new DatePickerDialog(AddTripActivity.this,new MyDateChooser(),yyyy,mm,dd);
                    dialog.show();

                }
            });

        approvedBudget=(EditText)findViewById(R.id.editText5);

        //*********************************creating database****************************************

        db=openOrCreateDatabase("EXPENSE_MAN",MODE_APPEND,null);
        db.execSQL("create table if not exists Table1(tripID varchar ,source varchar,destination varchar,startDate varchar,endDate varchar," +
                "approvedBudget varchar,balance varchar)");


    }
    public void updateDatabase(View v) {

        //********************************inserting values to database****************************

        if (tripID.getText().toString().equals("") || from.getText().toString().equals("") || to.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        } else if (startDate.getText().toString().equals("") || endDate.getText().toString().equals("") || approvedBudget.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            int counter=0;
            Cursor c=db.rawQuery("select * from Table1 where tripID='"+tripID.getText().toString()+"'",null);
            while(c.moveToNext())
            {
                ++counter; break;
            }
            if(counter>0)
            {
                Toast.makeText(this, "Trip ID already exists", Toast.LENGTH_LONG).show();
            }
            else{

                db.execSQL("insert into Table1 values('" + tripID.getText().toString() + "','" + from.getText().toString() + "','" +
                        to.getText().toString() + "','" + startDate.getText().toString() + "','" + endDate.getText().toString() + "','" +
                        approvedBudget.getText().toString() + "','" + approvedBudget.getText().toString() + "')");

                Toast.makeText(this, "Trip to " + to.getText().toString() + " started", Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("TripStatusFile1", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("STATUS", "true");
                editor.putString("TRIP", "true");
                editor.apply();

                Intent i = new Intent(this, CurrentTripActivity.class);
                startActivity(i);
                finish();
            }
        }
    }




}
