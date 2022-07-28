package com.FIS.shoppingcart.service.impl;

import java.util.Date;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;

import com.FIS.shoppingcart.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Qualifier;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean saveUser(Account account) {
        // TODO Auto-generated method stub
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        userRepository.saveAndFlush(account);
        return true;
    }

    @Override
    public Account addUser(Account accountDTO) {

        Account account = new Account();

        account.setId(accountDTO.getId());

        account.setName(accountDTO.getName());
        //Lấy password người dùng nhập, mã hóa về dạng BCrypt r lưu database
        BCryptPasswordEncoder endcoder = new BCryptPasswordEncoder();
        String rawPassword = accountDTO.getPassword();
        String endcodedPassword = endcoder.encode(rawPassword);
        System.out.println(endcodedPassword);
        account.setPassword(endcodedPassword);
        account.setUsername(accountDTO.getUsername());
        account.setAvatar(accountDTO.getAvatar());
        account.setCreated_time(new Date());
        account.setRole("ROLE_USER");
        account.setEnabled(true);
        userRepository.saveAndFlush(account);
        return account;

    }

    @Override
    public Account findUserByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepository.findUserByEmail(email);
    }

//    @Override
//    public void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException {
//
//    }

}