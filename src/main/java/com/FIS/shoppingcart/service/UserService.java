package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.User;
import com.FIS.shoppingcart.model.UserDTO;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    boolean saveUser(User user);

    User findUserByEmail(String email);


    void generateOneTimePassword(UserDTO userDTO) throws MessagingException, UnsupportedEncodingException;

}
