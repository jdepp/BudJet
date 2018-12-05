package com.cs1699.budjet.models;

import java.util.Date;

public class Income {

    private double Value;
    private String Description;
    private Category category;
    private String date;
    private boolean recurring;
    private int recurRate; // 1 = daily, 2 = weekly, 3 = monthly

    public Income() {

    }

    public Income(Category c, boolean rec, int recurRate, double val, String des, String date) {
        this.category = c;
        this.recurring = rec;
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
    
    public boolean isRecurring() { return this.recurring; }
    public void setType(boolean t) { this.recurring = t; }

    public int getRecurRate() { return this.recurRate; }
    public void setRecurRate() { this.recurRate = recurRate; }

}