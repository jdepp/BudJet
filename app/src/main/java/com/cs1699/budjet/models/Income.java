package com.cs1699.budjet.models;

import java.util.Date;

public class Income implements MoneyChange {

    private double Value;
    private String Description;
    private Category category;
    private String date;
    private boolean type;

    public Income() {

    }

    public Income(Category c, boolean rec, double val, String des, String date) {
        this.category = c;
        this.type = rec;
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

}