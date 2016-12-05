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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditExpenseActivity extends AppCompatActivity {
    EditText particulars,amount,date;
    Spinner spinner;
    String categoryArray[]={"Travel", "Lodge", "Meal","Shopping", "Miscellaneous"};
    String sParticulars,sAmount,sDate,sSerial,sCategory,tripID,balance;
    static int dd=0,mm=9,yyyy=2016;
    SQLiteDatabase db;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        Intent i=getIntent();
        sParticulars=i.getStringExtra("PART");
        sAmount=i.getStringExtra("AMT");
        sDate=i.getStringExtra("DATE");
        sSerial=i.getStringExtra("ID");
        balance=i.getStringExtra("BAL");
        tripID=i.getStringExtra("TID");
        SimpleDateFormat df = new SimpleDateFormat("dd");
        dd = Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        df=new SimpleDateFormat("M");
        mm=Integer.parseInt(df.format(Calendar.getInstance().getTime()))-1;
        df=new SimpleDateFormat("yyyy");
        yyyy=Integer.parseInt(df.format(Calendar.getInstance().getTime()));

        db=openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
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


                    DatePickerDialog dialog= new DatePickerDialog(EditExpenseActivity.this,new MyDateChooser(),yyyy,mm,dd);
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


                    DatePickerDialog dialog= new DatePickerDialog(EditExpenseActivity.this,new MyDateChooser(),yyyy,mm,dd);
                    dialog.show();

                }
            });

            particulars.setText(sParticulars);
        amount.setText(sAmount);
        date.setText(sDate);

        ArrayAdapter adapter = new ArrayAdapter(EditExpenseActivity.this, android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sCategory = categoryArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent back=null;
        setResult(0,back);
    }

    public void updateTable2(View v)
    {
        if(particulars.getText().toString().equals("")||amount.getText().toString().equals("")||date.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }
        else {
            db.execSQL("update Table2 set category='" + sCategory + "',particulars='" + particulars.getText().toString() + "', " +
                    "amount='" + amount.getText().toString() + "', date='" + date.getText().toString() + "' where serial='"+sSerial+"'");
            Toast.makeText(this, "Expense Data Updated", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,AllExpensesActivity.class);
            i.putExtra("SR",sSerial);
            i.putExtra("CAT",sCategory);
            i.putExtra("PART",particulars.getText().toString());
            i.putExtra("AMT",amount.getText().toString());
            i.putExtra("DT",date.getText().toString());
            setResult(0,i);
            finish();
        }
    }
}
