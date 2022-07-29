package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.impl.UserDetailServiceImpl;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    boolean saveUser(Account account);

    public Account addUser(Account account);


    Account findUserByEmail(String mail);

    // Cua Hoang
    public List<Account> getAllUser();

    public Account getUserById(int id);

    public Account get(int id);

    public void save(Account account);



//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
