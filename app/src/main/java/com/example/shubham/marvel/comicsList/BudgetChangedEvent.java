package com.example.shubham.marvel.comicsList;

class BudgetChangedEvent {

    private final String mBudget;

    public BudgetChangedEvent(String budget) {
        mBudget = budget;
    }

    public String getBudget() {
        return mBudget;
    }
}
