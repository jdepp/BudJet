package com.cs1699.budjet.models;

import java.util.Date;

public class Expense implements MoneyChange {

    private double Value;
    private String Description;
    private Category category;
    private String date;
    private boolean type;
    private int recurRate; // 1 = daily, 2 = weekly, 3 = monthly

    public Expense() {

    }

    public Expense(Category c, boolean rec, int recurRate, double val, String des, String date) {
        this.category = c;
        this.type = rec;
        this.recurRate = recurRate;
        this.Value = val;
        this.Description = des;
        this.date = date;
    }

    public double getValue() { return this.Value; }
    public void setValue(double val) { this.Value = val; }
    
    public String getDescription() { return this.Description; }
    public void setDescription(String descript) { this.Description = descript; }
    
    public Category getCategory() { return this.category; }
    public void setCategory(Category categ) { this.category = categ; }
    
    public String getDate() { return this.date; }
    public void setDate(String d) { this.date = d; }
    
    public boolean isRecurring() { return this.type; }
    public void setType(boolean t) { this.type = t; }

    public int getRecurRate() { return this.recurRate; }
    public void setRecurRate() { this.recurRate = recurRate; }

}