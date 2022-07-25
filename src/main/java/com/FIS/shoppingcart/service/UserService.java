package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.impl.UserDetailServiceImpl;
import org.springframework.security.core.Authentication;

public interface UserService {

    boolean saveUser(Account account);

    public Account addUser(Account account);


    Account findUserByEmail(String mail);



//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
