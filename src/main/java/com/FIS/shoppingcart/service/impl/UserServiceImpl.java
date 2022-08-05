package com.FIS.shoppingcart.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;

import com.FIS.shoppingcart.model.AccountDTO;
import com.FIS.shoppingcart.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;



    @Override
    public Account editUser(AccountDTO accountDTO) {
        Account account = userRepository.getById(accountDTO.getId());

        System.out.println(account);

        account.setName(accountDTO.getName());
        account.setUsername(accountDTO.getUsername());
        account.setAddress(accountDTO.getAddress());
        account.setPhone(accountDTO.getPhone());
        account.setAvatar(accountDTO.getAvatar());

        System.out.println("In ra cho vui : " + accountDTO.getAvatar());



        return userRepository.save(account);
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


    //Cua Hoang -----------------------------------------------------------------------------------------------
    @Override
    public List<Account> getAllUser() {
        List<Account> accounts = userRepository.findAll();

        return accounts;
    }

    @Override
    public Account getUserById(int id) {
        Account account = userRepository.findById(id).get();
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
        userRepository.save(account);

    }

    @Override
    public List<Account> listAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Account> findUser(String keyword) {
        if(keyword!=null)
        {
            return userRepository.findUser(keyword);
        }
        return userRepository.findAll();
    }

    @Override
    public Optional<Account> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        String encoded = new String(bCryptPasswordEncoder.encode(newPassword));
        account.setPassword(encoded);
        userRepository.save(account);

    }

    public void deleteUser(Account account) {
        userRepository.delete(account);
    }

    public void saveInfoUser(Account account) {
        userRepository.saveAndFlush(account);
    }

//    @Override
//    public void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException {
//
//    }

}