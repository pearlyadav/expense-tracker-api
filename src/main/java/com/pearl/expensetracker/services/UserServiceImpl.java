package com.pearl.expensetracker.services;

import com.pearl.expensetracker.domain.User;
import com.pearl.expensetracker.exceptions.etAuthException;
import com.pearl.expensetracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws etAuthException {

        if (email != null)
            email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws etAuthException {

        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null)
            email = email.toLowerCase();

        assert email != null;
        if (!emailPattern.matcher(email).matches())
            throw new etAuthException("Invalid Email Format!");

        Integer count = userRepository.getCountByEmail(email);

        if (count > 0)
            throw new etAuthException("Email already in Use");

        Integer userId = userRepository.createUser(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}
