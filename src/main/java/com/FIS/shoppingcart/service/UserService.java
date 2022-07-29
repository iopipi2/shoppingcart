package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean saveUser(Account account);

    Account findUserByEmail(String email);
    Optional<Account> findUserById(int id);


//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;
    public List<Account> listAll();
    public Account getUserById(int id);

    public Account get(int id);

    public List<Account> findUser(String keyword);




}
