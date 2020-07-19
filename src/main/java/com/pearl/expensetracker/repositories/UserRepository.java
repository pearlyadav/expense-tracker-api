package com.pearl.expensetracker.repositories;

import com.pearl.expensetracker.domain.User;
import com.pearl.expensetracker.exceptions.etAuthException;

public interface UserRepository {

    Integer createUser(String firstName, String lastName, String email, String password) throws etAuthException;

    User findByEmailAndPassword(String email, String password) throws etAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

}
