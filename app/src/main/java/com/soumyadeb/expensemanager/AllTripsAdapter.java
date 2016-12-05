package com.soumyadeb.expensemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Soumya Deb on 09-10-2016.
 */
public class AllTripsAdapter extends ArrayAdapter<AllTrips> {
    public AllTripsAdapter(Context context, int resource, ArrayList<AllTrips> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tripView=convertView;
        if(tripView == null) {
            tripView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_view_all_trips, parent, false);
        }
        AllTrips currentTrip=getItem(position);
        TextView to=(TextView)tripView.findViewById(R.id.textView1);
        to.setText(currentTrip.getTo());
        TextView from=(TextView)tripView.findViewById(R.id.textView2);
        from.setText("From: "+currentTrip.getFrom());
        TextView startDate=(TextView)tripView.findViewById(R.id.textView3);
        startDate.setText(currentTrip.getStartDate());
        TextView balance=(TextView)tripView.findViewById(R.id.textView5);
        balance.setText("Amount Spent: Rs. "+currentTrip.getSpent());
        ImageView iv=(ImageView)tripView.findViewById(R.id.imageView1);
        String s=currentTrip.getTo();
        if(s.startsWith("A")||s.startsWith("a"))
            iv.setImageResource(R.drawable.a);
        else if(s.startsWith("B")||s.startsWith("b"))
            iv.setImageResource(R.drawable.b);
        else if(s.startsWith("C")||s.startsWith("c"))
            iv.setImageResource(R.drawable.c);
        else if(s.startsWith("D")||s.startsWith("d"))
            iv.setImageResource(R.drawable.d);
        else if(s.startsWith("E")||s.startsWith("e"))
            iv.setImageResource(R.drawable.e);
        else if(s.startsWith("F")||s.startsWith("f"))
            iv.setImageResource(R.drawable.f);
        else if(s.startsWith("G")||s.startsWith("g"))
            iv.setImageResource(R.drawable.g);
        else if(s.startsWith("H")||s.startsWith("h"))
            iv.setImageResource(R.drawable.h);
        else if(s.startsWith("I")||s.startsWith("i"))
            iv.setImageResource(R.drawable.i);
        else if(s.startsWith("J")||s.startsWith("j"))
            iv.setImageResource(R.drawable.j);
        else if(s.startsWith("K")||s.startsWith("k"))
            iv.setImageResource(R.drawable.k);
        else if(s.startsWith("L")||s.startsWith("l"))
            iv.setImageResource(R.drawable.l);
        else if(s.startsWith("M")||s.startsWith("m"))
            iv.setImageResource(R.drawable.m);
        else if(s.startsWith("N")||s.startsWith("n"))
            iv.setImageResource(R.drawable.n);
        else if(s.startsWith("O")||s.startsWith("o"))
            iv.setImageResource(R.drawable.o);
        else if(s.startsWith("P")||s.startsWith("p"))
            iv.setImageResource(R.drawable.p);
        else if(s.startsWith("Q")||s.startsWith("q"))
            iv.setImageResource(R.drawable.q);
        else if(s.startsWith("R")||s.startsWith("r"))
            iv.setImageResource(R.drawable.r);
        else if(s.startsWith("S")||s.startsWith("s"))
            iv.setImageResource(R.drawable.s);
        else if(s.startsWith("T")||s.startsWith("t"))
            iv.setImageResource(R.drawable.t);
        else if(s.startsWith("U")||s.startsWith("u"))
            iv.setImageResource(R.drawable.u);
        else if(s.startsWith("V")||s.startsWith("v"))
            iv.setImageResource(R.drawable.v);
        else if(s.startsWith("W")||s.startsWith("w"))
            iv.setImageResource(R.drawable.w);
        else if(s.startsWith("X")||s.startsWith("x"))
            iv.setImageResource(R.drawable.x);
        else if(s.startsWith("Y")||s.startsWith("y"))
            iv.setImageResource(R.drawable.y);
        else if(s.startsWith("Z")||s.startsWith("z"))
            iv.setImageResource(R.drawable.z);
        else iv.setImageResource(R.drawable.others);

        TextView currentTripIndicator=(TextView)tripView.findViewById(R.id.textView4);
        currentTripIndicator.setVisibility(currentTripIndicator.GONE);
        if(currentTrip.getCurr()==true)
        {
            currentTripIndicator.setVisibility(currentTripIndicator.VISIBLE);
        }
        return tripView;
    }
}
