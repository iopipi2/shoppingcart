package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;

public interface UserService {

    boolean saveUser(Account account);

    public Account addUser(Account account);


    Account findUserByEmail(String mail);


//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
