package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.User;

public interface UserService {

    boolean saveUser(User user);


    User findUserByEmail(String mail);


//    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
