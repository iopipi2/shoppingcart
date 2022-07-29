package com.FIS.shoppingcart.service.impl;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean saveUser(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        userRepository.saveAndFlush(account);
        return true;
    }

    @Override
    public Account findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<Account> findUserById(int id) {
        return userRepository.findById(id);
    }


    public void saveInfoUser(Account account) {
        userRepository.saveAndFlush(account);
    }

    public void deleteUser(Account account) {
        userRepository.delete(account);
    }


//    @Override
//    public void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException {
//
//    }


    @Override
    public List<Account> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Account getUserById(int id) {
        Account user = userRepository.getById(id);
        System.out.println(user);
        return user;
    }

    @Override
    public Account get(int id) {
        Optional<Account> result = userRepository.findById(id);
        return userRepository.findById(id).get();
    }

    @Override
    public List<Account> findUser(String keyword) {
        if(keyword!=null)
        {
            return userRepository.findUser(keyword);
        }
        return userRepository.findAll();
    }


}