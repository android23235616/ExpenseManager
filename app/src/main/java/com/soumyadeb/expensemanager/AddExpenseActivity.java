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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity {
    EditText particulars,amount,date;
    Spinner spinner;
    String categoryArray[]={"Travel", "Lodge", "Meal","Shopping", "Miscellaneous"};
    static String category,tripID;
    static String serial;
    static int dd=0,mm=9,yyyy=2016;
    SQLiteDatabase db;
    float previousBalance=0,currentBalance=0;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat df = new SimpleDateFormat("dd");
        dd = Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        df=new SimpleDateFormat("M");
        mm=Integer.parseInt(df.format(Calendar.getInstance().getTime()))-1;
        df=new SimpleDateFormat("yyyy");
        yyyy=Integer.parseInt(df.format(Calendar.getInstance().getTime()));
       // Toast.makeText(this,,Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        SharedPreferences sp= getSharedPreferences("ExpenseStatusFile",0);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("STATUS","true");
        editor.apply();
        particulars=(EditText)findViewById(R.id.editText1);
        amount=(EditText)findViewById(R.id.editText2);
        date=(EditText)findViewById(R.id.editText3);
        spinner=(Spinner)findViewById(R.id.spinner1);
            date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    class MyDateChooser implements DatePickerDialog.OnDateSetListener
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    }


                    DatePickerDialog dialog= new DatePickerDialog(AddExpenseActivity.this,new MyDateChooser(),yyyy,mm,dd);
                    dialog.show();
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class MyDateChooser implements DatePickerDialog.OnDateSetListener
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    }


                        DatePickerDialog dialog= new DatePickerDialog(AddExpenseActivity.this,new MyDateChooser(),yyyy,mm,dd);
                        dialog.show();

                }
            });
        ArrayAdapter adapter = new ArrayAdapter(AddExpenseActivity.this, android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        serial=sp.getString("SERIAL","false");
        if (serial.equals("false"))
        {
            editor.putString("SERIAL","1");
            editor.apply();
            serial="1";
        }

        db=openOrCreateDatabase("EXPENSE_MAN",MODE_APPEND,null);
        db.execSQL("create table if not exists Table2(serial varchar,category varchar,particulars varchar,amount integer," +
                "date varchar,tripID varchar)");
      /*  Cursor c=db.rawQuery("select serial from Table2",null);
        c.moveToLast();
        if(c==null)
        {
            serial=1;
        }
        else
        {
            serial=Integer.parseInt(c.getString(0));
        }
*/
        Intent i=getIntent();
        tripID=i.getStringExtra("ID");
       // Toast.makeText(this,"ID: "+tripID,Toast.LENGTH_SHORT).show();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    category = categoryArray[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void updateTable2(View v)
    {
        if(particulars.getText().toString().equals("")||amount.getText().toString().equals("")||date.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }
        else {

            db.execSQL("insert into Table2 values('" + (Integer.parseInt(serial) + 1) + "','" + category + "','" +
                    particulars.getText().toString() + "','" + Integer.parseInt(amount.getText().toString()) + "','" + date.getText().toString() + "','" +
                    tripID + "')");
            String query = "select * from Table1 where tripID='" + tripID + "'";
            Log.d("Test", "" + (Integer.parseInt(serial) + 1));

            SharedPreferences sp = getSharedPreferences("ExpenseStatusFile", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("SERIAL", "" + (Integer.parseInt(serial) + 1));
            editor.apply();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            previousBalance = Float.parseFloat(cursor.getString(6));
            currentBalance = previousBalance - Float.parseFloat(amount.getText().toString());
            db.execSQL("update Table1 set balance='" + currentBalance + "' where tripID='" + tripID + "'");
            Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Current Balance: " + currentBalance, Toast.LENGTH_SHORT).show();

        /*SharedPreferences.Editor editor =sp.edit();
        editor.putString("STATUS","true");
        editor.putString("SERIAL",serial);
        editor.apply(); */
            finish();
        }

    }

}
