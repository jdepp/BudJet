package com.cs1699.budjet.models;

import java.util.*;

public class Category {

    private String CID;
    private String Name;
    private String Description;
    private ArrayList<Expense> Expenses = new ArrayList<Expense>();
    private ArrayList<Income> Incomes = new ArrayList<Income>();
    private Budget budget = new Budget();

    public Category() {

    }
    
    public Category(String n, String d, double b){
      this.Name = n;
      this.Description = d;
      budget.setValue(b);
      //budget.setCategory(this);
    }
    
    public String getName() { return this.Name; }
    public void setName(String n) { this.Name = n; }
    
    public String getDescription() { return this.Description; }
    public void setDescription(String d) { this.Description = d; }

    public Budget getBudget() { return this.budget; }

    public ArrayList<Expense> getExpenses(){return this.Expenses;}
    
    public void addExpense(Expense exp){this.Expenses.add(exp);}
    
    public void deleteExpense(Expense exp){
      for(int i = 0; i < this.Expenses.size(); i++){
        if((this.Expenses.get(i)).equals(exp)){
          this.Expenses.remove(i);
        }
      }
    }
    
    public ArrayList<Income> getIncomes(){return this.Incomes;}
    
    public void addIncome(Income inc){this.Incomes.add(inc);}
    
    public void deleteIncome(Income inc){
      for(int i = 0; i < this.Incomes.size(); i++){
        if((this.Incomes.get(i)).equals(inc)){
          this.Incomes.remove(i);
        }
      }
    }
}
