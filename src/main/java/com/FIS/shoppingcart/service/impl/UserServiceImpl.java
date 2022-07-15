package com.FIS.shoppingcart.service.impl;

import java.util.List;
import java.util.Optional;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.User;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();

        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = userRepository.getById(id);
        System.out.println(user);
        return user;
    }

    @Override
    public User get(int id) {

            Optional<User> result = userRepository.findById(id);
            return userRepository.findById(id).get();

    }

}