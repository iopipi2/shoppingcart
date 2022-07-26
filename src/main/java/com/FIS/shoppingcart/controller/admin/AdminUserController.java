package com.FIS.shoppingcart.controller.admin;


import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminUserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserServiceImpl userService;

//
}