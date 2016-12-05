package com.soumyadeb.expensemanager;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllExpensesActivity extends ListActivity {
    String serial=null,category=null,particulars=null,amount=null,date=null,tripID=null,sBalance;
    static float balance;
    AllExpensesAdapter adapter;
    static int pos;
    ArrayList<AllExpenses> expensesList=new ArrayList<AllExpenses>();
    static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent box=getIntent();
        sBalance=box.getStringExtra("BAL");
        balance=Float.parseFloat(sBalance);
        tripID=box.getStringExtra("ID");

        SQLiteDatabase db=openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
        String q="select * from Table2 where tripID='"+tripID+"' order by serial asc";
        Cursor c;

        c = db.rawQuery(q, null);
        

        while (c.moveToNext()) {
            serial=c.getString(0);
            category=c.getString(1);
            particulars=c.getString(2);
            amount=c.getString(3);
            date=c.getString(4);
            expensesList.add(new AllExpenses(serial,category,particulars,amount,date));
            ++count;
        }
        db.close();
        if(count==0)
        {
            AlertDialog.Builder builder1=new AlertDialog.Builder(AllExpensesActivity.this);
            builder1.setTitle("No expenses added yet");
            builder1.setMessage("Your expense list is empty");
            builder1.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            builder1.setPositiveButton("ADD EXPENSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AllExpensesActivity.this,AddExpenseActivity.class));
                    finish();
                }
            });

            AlertDialog dialog1=builder1.create();
            dialog1.show();
            //Toast.makeText(this,"Trip List Empty",Toast.LENGTH_SHORT).show();
        }
        adapter = new AllExpensesAdapter(this,R.layout.activity_all_expenses, expensesList);
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
        final AllExpenses selectedExpense=expensesList.get(info.position);
        if (item.getTitle().equals("Edit")) {
            Intent i =new Intent(AllExpensesActivity.this,EditExpenseActivity.class);
            i.putExtra("PART",selectedExpense.getParticulars());
            i.putExtra("AMT",selectedExpense.getAmount());
            i.putExtra("DATE",selectedExpense.getmDate());
            i.putExtra("ID",selectedExpense.getSerial());
            i.putExtra("BAL",balance);
            i.putExtra("TID",tripID);
            startActivityForResult(i,0);
        }
        else if (item.getTitle().equals("Delete")) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete Expense");
            builder.setMessage("Are you sure you want to delete this expense?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Float currentBalance=balance+Float.parseFloat(selectedExpense.getAmount());
                    db.execSQL("delete from Table2 where serial='"+selectedExpense.getSerial()+"'");
                    db.execSQL("update Table1 set balance='"+currentBalance+"' where tripID='"+tripID+"'");
                    expensesList.remove(info.position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AllExpensesActivity.this,"Expense Deleted",Toast.LENGTH_LONG).show();
                    --count;

                    if(count==0)
                    {
                        AlertDialog.Builder builder1=new AlertDialog.Builder(AllExpensesActivity.this);
                        builder1.setTitle("No expenses added yet");
                        builder1.setMessage("Your expense list is empty");
                        builder1.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        });
                        builder1.setPositiveButton("ADD EXPENSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(AllExpensesActivity.this,AddExpenseActivity.class));
                                finish();
                            }
                        });
                        AlertDialog dialog1=builder1.create();
                        dialog1.show();
                        //Toast.makeText(this,"Trip List Empty",Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            Bundle bundle = data.getExtras();
            String category = bundle.getString("CAT");
            String particulars = bundle.getString("PART");
            String amount = bundle.getString("AMT");
            String date = bundle.getString("DT");
            String serial = bundle.getString("SR");
            expensesList.set(pos, new AllExpenses(serial, category, particulars, amount, date));
            adapter.notifyDataSetChanged();
            SQLiteDatabase db = openOrCreateDatabase("EXPENSE_MAN", MODE_APPEND, null);
            Float currentBalance = balance + Float.parseFloat(amount);
            db.execSQL("update Table1 set balance='" + currentBalance + "' where tripID='" + tripID + "'");
        }
       else Toast.makeText(this,"No changes made",Toast.LENGTH_SHORT).show();
    }
}
