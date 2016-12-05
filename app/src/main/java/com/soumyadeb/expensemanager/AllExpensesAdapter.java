package com.soumyadeb.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Soumya Deb on 12-10-2016.
 */
public class AllExpensesAdapter extends ArrayAdapter<AllExpenses> {
    public AllExpensesAdapter(Context context, int resource, ArrayList<AllExpenses> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View expenseView=convertView;
        if(expenseView == null) {
            expenseView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_all_expenses, parent, false);
        }
        AllExpenses currentExpense=getItem(position);
        TextView category=(TextView)expenseView.findViewById(R.id.textView1);
        category.setText(currentExpense.getCategory());
        TextView particulars=(TextView)expenseView.findViewById(R.id.textView2);
        particulars.setText(currentExpense.getParticulars());
        TextView date=(TextView)expenseView.findViewById(R.id.textView3);
        date.setText(currentExpense.getmDate());
        TextView amount=(TextView)expenseView.findViewById(R.id.textView4);
        amount.setText("Rs. "+currentExpense.getAmount());
        ImageView iv=(ImageView)expenseView.findViewById(R.id.imageView1);
        if(currentExpense.getCategory().equals("Meal"))
            iv.setImageResource(R.drawable.meal);
        else if(currentExpense.getCategory().equals("Lodge"))
            iv.setImageResource(R.drawable.lodge);
        else if(currentExpense.getCategory().equals("Shopping"))
            iv.setImageResource(R.drawable.shopping);
        else if(currentExpense.getCategory().equals("Travel"))
            iv.setImageResource(R.drawable.travel);
        else if(currentExpense.getCategory().equals("Miscellaneous"))
            iv.setImageResource(R.drawable.misc);
        else iv.setImageResource(R.drawable.misc);

        return expenseView;
    }
}
