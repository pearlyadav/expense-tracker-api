package com.pearl.expensetracker.services;

import com.pearl.expensetracker.domain.Category;
import com.pearl.expensetracker.exceptions.etBadRequestException;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;
import com.pearl.expensetracker.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws etResourceNotFoundException {
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws etBadRequestException {
        int categoryId = categoryRepository.createCategory(userId, title, description);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws etBadRequestException {
        categoryRepository.update(userId, categoryId, category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws etResourceNotFoundException {
        this.fetchCategoryById(userId, categoryId);
        categoryRepository.removeById(userId, categoryId);
    }
}
