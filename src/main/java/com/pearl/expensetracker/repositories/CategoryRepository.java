package com.pearl.expensetracker.repositories;

import com.pearl.expensetracker.domain.Category;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;
import com.pearl.expensetracker.exceptions.etBadRequestException;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll(Integer userId) throws etResourceNotFoundException;

    Category findById(Integer userId, Integer categoryId) throws etResourceNotFoundException;

    Integer createCategory(Integer userId, String title, String description) throws etBadRequestException;

    void update(Integer userId, Integer categoryId, Category category) throws etBadRequestException;

    void removeById(Integer userId, Integer categoryId);

}
