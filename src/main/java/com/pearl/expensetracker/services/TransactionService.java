package com.pearl.expensetracker.services;

import com.pearl.expensetracker.domain.Transaction;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;
import com.pearl.expensetracker.exceptions.etBadRequestException;

import java.util.List;

public interface TransactionService {

    List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer TransactionId) throws etResourceNotFoundException;

    Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws etBadRequestException;

    void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws etBadRequestException;

    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws etResourceNotFoundException;

}
