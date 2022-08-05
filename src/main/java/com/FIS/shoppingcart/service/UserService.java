package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.model.AccountDTO;
import com.FIS.shoppingcart.service.impl.UserDetailServiceImpl;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public Account editUser(AccountDTO accountDTO);
    public Account addUser(Account account);


    Account findUserByEmail(String mail);

    // Cua Hoang
    public List<Account> getAllUser();

    public Account getUserById(int id);

    public Account get(int id);

    public void save(Account account);

    public List<Account> listAll();

    public List<Account> findUser(String keyword);
    Optional<Account> findUserById(int id);

    public void updatePassword(Account account, String newPassword);



//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
