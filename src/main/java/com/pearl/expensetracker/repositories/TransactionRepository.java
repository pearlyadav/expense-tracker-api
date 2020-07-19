package com.pearl.expensetracker.repositories;

import com.pearl.expensetracker.domain.Transaction;
import com.pearl.expensetracker.exceptions.etBadRequestException;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws etResourceNotFoundException;

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws etBadRequestException;

    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws etBadRequestException;

    void removeById(Integer userId, Integer categoryId, Integer transactionId) throws etResourceNotFoundException;

}
