package com.soumyadeb.expensemanager;

/**
 * Created by Soumya Deb on 09-10-2016.
 */
public class AllTrips {
    private String mFrom,mTo,mStartDate,mTripId,mBalance,mBudget;
    private boolean mCurr;



    public AllTrips(String from,String to,String startDate,String tripId,String balance, String budget,boolean curr)
    {
        mFrom=from;
        mTo=to;
        mStartDate=startDate;
        mTripId=tripId;
        mBalance=balance;
        mBudget=budget;
        mCurr=curr;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getTo() {
        return mTo;
    }

    public String getTripId() {
        return mTripId;
    }

    public float getSpent() {
        return  Float.parseFloat(mBudget)-Float.parseFloat(mBalance);
    }

    public boolean getCurr(){ return mCurr; }
}
