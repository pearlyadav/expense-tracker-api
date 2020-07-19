package com.pearl.expensetracker.services;

import com.pearl.expensetracker.domain.User;
import com.pearl.expensetracker.exceptions.etAuthException;

public interface UserService {

    User validateUser(String email, String password) throws etAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws etAuthException;

}
