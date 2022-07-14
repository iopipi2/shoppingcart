package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.User;

import java.util.List;

public interface UserService {
public List<User> getAllUser();

public User getUserById(int id);

public User get(int id);

public User editUser(int id);

public void editUserEntity(User user);







}
