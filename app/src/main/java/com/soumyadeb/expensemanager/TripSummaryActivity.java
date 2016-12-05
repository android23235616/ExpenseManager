package com.soumyadeb.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TripSummaryActivity extends AppCompatActivity {
    TextView tripID_Tv,from_Tv,to_Tv,startDate_Tv,endDate_Tv,approvedBudget_Tv,expenditure_Tv;
    String sTripId,sFrom,sTo,sStartDate,sEndDate,sApprovedBudget;
    Spinner spinner;
    TableLayout tableLayout;
    String sort[]={"Date","Category"};
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);
        to_Tv=(TextView)findViewById(R.id.textView1);
        from_Tv=(TextView)findViewById(R.id.textView2);
        tripID_Tv=(TextView)findViewById(R.id.textView3);
        startDate_Tv=(TextView)findViewById(R.id.textView4);
        endDate_Tv=(TextView)findViewById(R.id.textView5);
        approvedBudget_Tv=(TextView)findViewById(R.id.textView8);
        expenditure_Tv=(TextView)findViewById(R.id.textView9);
        tableLayout=(TableLayout)findViewById(R.id.tableLayout);

        db=openOrCreateDatabase("EXPENSE_MAN",MODE_APPEND,null);

        Intent i=getIntent();
        sTripId=i.getStringExtra("ID");
        tripID_Tv.setText(sTripId);

        Cursor c=db.rawQuery("select * from Table1 where tripID='"+sTripId+"'",null);
        c.moveToFirst();
        sFrom=c.getString(1);
        from_Tv.setText(sFrom);

        sTo=c.getString(2);
        to_Tv.setText(sTo);

        sStartDate=c.getString(3);
        startDate_Tv.setText(sStartDate);

        sEndDate=c.getString(4);
        endDate_Tv.setText(sEndDate);

        sApprovedBudget=c.getString(5);
        approvedBudget_Tv.setText("Rs. "+sApprovedBudget);

        float expenditure=Float.parseFloat(sApprovedBudget)-Float.parseFloat(c.getString(6));
        expenditure_Tv.setText("Rs. "+expenditure);


                TableRow tableRow;
                Cursor   c1 = db.rawQuery("select * from Table2 where tripID='"+sTripId+"'",null);
                    int sNo=1;
                    while (c1.moveToNext())
                    {
                        TextView col1=new TextView(this);
                        col1.setText(""+(sNo++));          //tripID
                        TextView col2=new TextView(this);
                        col2.setText(c1.getString(4)); //category
                        TextView col3=new TextView(this);
                        col3.setText(c1.getString(1));
                        TextView col4=new TextView(this);
                        col4.setText("Rs. "+c1.getString(3));

                        tableRow=new TableRow(this);
                        tableRow.addView(col1);
                        tableRow.addView(col2);
                        tableRow.addView(col3);
                        tableRow.addView(col4);
                        tableLayout.addView(tableRow);
                    }
                }




}
