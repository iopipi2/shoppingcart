package com.FIS.shoppingcart.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.User;
import com.FIS.shoppingcart.model.UserDTO;
import com.FIS.shoppingcart.service.UserService;

import antlr.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Controller
public class UserController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/list-user")
    public String getAllUser(Model model) {
        List<User> users = userService.getAllUser();
        System.out.println(users);

        model.addAttribute("user", users);
        model.addAttribute("u", new User());

        return "/admin/viewAddUser";
    }

    @GetMapping("/infoUser")
    public String infoUser(Model model) {
        User users = userService.getUserById(52);
        model.addAttribute("user",users);
        System.out.println(users);
         return "/user/detailUser";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute (name = "user") User user) throws IOException {
        userRepository.save(user);
        return "redirect:/infoUser";

    }






}
