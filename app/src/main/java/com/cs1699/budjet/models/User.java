package com.cs1699.budjet.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class User {

    private int id;
    private String name;
    private String email;
    private String securityQuestions;
    private String securityAnswers;
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Budget> budgets = new ArrayList<>();

    public User() {

    }

    public User(String name, String email) {
        this.id = 10;
        this.name = name;
        this.email = email;
    }

    public int getID() { return this.id; }
    public void setID()
    {
        //check database for latest UID and iterate by one
        this.id = 10;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSecQ() { return this.securityQuestions; }
    public void setSecQ(String secQ) { this.securityQuestions = secQ; }

    public String getSecA() { return this.securityAnswers; }
    public void setSecA(String secA) { this.securityAnswers = secA; }

    public List<Income> getIncomes() { return this.incomes; }
    public void addIncome(Income income) { this.incomes.add(income); }

    public List<Expense> getExpenses() { return this.expenses; }
    public void addExpense(Expense expense) { this.expenses.add(expense); }

    public List<Category> getCategories() { return this.categories; }
    public void addCategory(Category category) { this.categories.add(category); }

    public List<Budget> getBudgets() { return this.budgets; }
    public void setBudget(Budget budget, int index) { this.budgets.add(index, budget); }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

}
