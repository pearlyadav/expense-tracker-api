package com.pearl.expensetracker.domain;

public class Category {

    private Integer userId, categoryId;
    private String title, description;
    private Double totalExpense;

    public Category(Integer userId, Integer categoryId, String title, String description, Double totalExpense) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.totalExpense = totalExpense;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
