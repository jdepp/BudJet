package com.cs1699.budjet.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Budget {
    private int id;
    private Category category;
    private double value;


    public Budget(Category category, double value) {
        this.id = 10;
        this.category = category;
        this.value = value;
    }

    public Category getCategory() { return this.category; }
    public void setCategory(Category category) { this.category = category; }

    public double getValue() { return this.value; }
    public void setValue(double value ) { this.value = value; }
}