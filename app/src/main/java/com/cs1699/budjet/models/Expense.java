package com.cs1699.budjet.models;

import java.util.Date;

public class Expense {

    private String EID;
    private double Value;
    private String Description;
    private Category category;
    private String date;
    private boolean type;

    public Expense() {

    }

    public Expense(Category c, boolean rec, double val, String des, String date) {
        this.category = c;
        this.type = rec;
        this.Value = val;
        this.Description = des;
        this.date = date;
    }

    public String getID() { return this.EID; }
    public void setID(String ID) { this.EID = ID; }

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