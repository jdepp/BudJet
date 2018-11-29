package com.cs1699.budjet.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@IgnoreExtraProperties
public class User {

    private String name;
    private String email;
    private String secA;
    private String secQ;
    private HashMap<String, Expense> expenses = new HashMap<>();
    private HashMap<String, Income> incomes = new HashMap<>();
    private HashMap<String, Category> categories = new HashMap<>();
    private HashMap<String, Budget> budgets = new HashMap<>();

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.secA = "";
        this.secQ = "";
        this.expenses = null;
        this.incomes = null;
        this.categories = null;
        this.budgets = null;
    }

    public User(String name, String email, String secA, String secQ, HashMap<String, Expense> expenses, HashMap<String, Income> incomes, HashMap<String, Category> categories, HashMap<String, Budget> budgets) {
        this.name = name;
        this.email = email;
        this.secA = secA;
        this.secQ = secQ;
        this.expenses = expenses;
        this.incomes = incomes;
        this.categories = categories;
        this.budgets = budgets;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getSecQ() { return this.secQ; }
    public void setSecQ(String secQ) { this.secA = secQ; }

    public String getSecA() { return this.secA; }
    public void setSecA(String secA) { this.secA = secA; }

    public HashMap<String, Income> getIncomes() { return this.incomes; }
    public void setIncomes(HashMap<String, Income> incomes) { this.incomes = incomes; }

    public HashMap<String, Expense> getExpenses() { return this.expenses; }
    public void setExpenses(HashMap<String, Expense> expenses) { this.expenses = expenses; }

    public HashMap<String, Category> getCategories() { return this.categories; }
    public void setCategories(HashMap<String, Category> categories) { this.categories = categories; }

    public HashMap<String, Budget> getBudgets() { return this.budgets; }
    public void setBudgets(HashMap<String, Budget> budgets) { this.budgets = budgets; }


}
