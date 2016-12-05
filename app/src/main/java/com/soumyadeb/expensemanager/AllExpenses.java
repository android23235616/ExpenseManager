package com.soumyadeb.expensemanager;

/**
 * Created by Soumya Deb on 12-10-2016.
 */
public class AllExpenses {
    private String mSerial,mCategory,mParticulars,mAmount,mDate;

    public AllExpenses(String serial,String category,String particulars,String amount,String date)
    {
        mSerial=serial;
        mCategory=category;
        mParticulars=particulars;
        mAmount=amount;
        mDate=date;
    }

    public String getSerial() {
        return mSerial;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getParticulars() {
        return mParticulars;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getmDate() {
        return mDate;
    }
}
