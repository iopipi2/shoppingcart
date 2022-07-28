package com.FIS.shoppingcart.service.impl;

import java.util.List;
import java.util.Optional;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Account> getAllUser() {
        List<Account> accounts = userRepository.findAll();

        return accounts;
    }

    @Override
    public Account getUserById(int id) {
        Account account = userRepository.getById(id);
        System.out.println(account);
        return account;
    }

    @Override
    public Account get(int id) {

            Optional<Account> result = userRepository.findById(id);
            return userRepository.findById(id).get();

    }

    @Override
    public void save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        userRepository.save(account);

    }

}