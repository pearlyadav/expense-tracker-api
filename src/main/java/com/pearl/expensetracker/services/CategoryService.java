package com.pearl.expensetracker.services;

import com.pearl.expensetracker.domain.Category;
import com.pearl.expensetracker.exceptions.etBadRequestException;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws etResourceNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws etBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws etBadRequestException;

    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws etResourceNotFoundException;

}
