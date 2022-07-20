package com.FIS.shoppingcart.controller;
import com.FIS.shoppingcart.model.UserModel;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    private UserModel userModel = null;

//    @ModelAttribute("userModel")
//    public UserModel getUserModel() {
//
//        if (httpSession.getAttribute("userModel") == null) {
//            // add the user model
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            User user = userService.findUserByEmail(authentication.getName());
//            if (user != null) {
//                userModel = new UserModel();
//                userModel.setId(user.getId());
//                userModel.setEmail(user.getUsername());
//                userModel.setRole(user.getRole());
//
//
//                if (userModel.getRole().equalsIgnoreCase("USER")) {
//                    // set the cart only if user is a buyer
//
//                    userModel.setCart(user.getCart());
//                }
//                httpSession.setAttribute("userModel", userModel);
//            }
//
//        }
//
//        return (UserModel) httpSession.getAttribute("userModel");
//    }

}
