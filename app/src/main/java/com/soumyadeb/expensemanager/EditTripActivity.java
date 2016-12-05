package com.soumyadeb.expensemanager;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
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

public class EditTripActivity extends AppCompatActivity {
    EditText tripID,from,to,startDate,endDate,approvedBudget;
    String sTripId,sFrom,sTo,sStartDate,sEndDate,sApprovedBudget;
    SQLiteDatabase db;
    static int dd=0,mm=9,yyyy=2016;
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
        setContentView(R.layout.activity_edit_trip);
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


                    DatePickerDialog dialog= new DatePickerDialog(EditTripActivity.this,new MyDateChooser(),yyyy,mm,dd);
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


                    DatePickerDialog dialog= new DatePickerDialog(EditTripActivity.this,new MyDateChooser(),yyyy,mm,dd);
                    dialog.show();

                }
            });



        approvedBudget=(EditText)findViewById(R.id.editText5);

        db=openOrCreateDatabase("EXPENSE_MAN",MODE_APPEND,null);

        Intent i=getIntent();
        sTripId=i.getStringExtra("ID");

        Cursor c=db.rawQuery("select * from Table1 where tripID='"+sTripId+"'",null);
        c.moveToFirst();
        sFrom=c.getString(1);
        sTo=c.getString(2);
        sStartDate=c.getString(3);
        sEndDate=c.getString(4);
        sApprovedBudget=c.getString(5);

        tripID.setText(sTripId);
        from.setText(sFrom);
        to.setText(sTo);
        startDate.setText(sStartDate);
        endDate.setText(sEndDate);
        approvedBudget.setText(sApprovedBudget);

        Intent back=null;
        setResult(0,back);
    }
    public void updateDatabase(View v)
    {
        if(tripID.getText().toString().equals("")||from.getText().toString().equals("")||to.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        } else if(startDate.getText().toString().equals("")||endDate.getText().toString().equals("")||approvedBudget.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }

        else {
            //********************************updating values to database****************************
            String query = "update Table1 set tripID='" + tripID.getText().toString() + "',source='" + from.getText().toString() + "',destination='" +
                    to.getText().toString() + "',startDate='" + startDate.getText().toString() + "',endDate='" + endDate.getText().toString() + "',approvedBudget='" +
                    approvedBudget.getText().toString() + "',balance='" + approvedBudget.getText().toString() + "' where tripID='" + sTripId + "'";

            db.execSQL(query);

            Toast.makeText(this, "Trip data updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
