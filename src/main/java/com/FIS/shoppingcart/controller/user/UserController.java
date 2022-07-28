package com.FIS.shoppingcart.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.UserService;


@Controller
public class UserController {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/list-user")
    public String getAllUser(Model model) {
        List<Account> accounts = userService.getAllUser();
        System.out.println(accounts);
        model.addAttribute("user", accounts);
        model.addAttribute("u", new Account());

        return "/admin/viewAddUser";
    }


    @GetMapping("/infoUser")
    public String infoUser(Model model) {
        Account users = userService.getUserById(1);
        model.addAttribute("user", users);
        System.out.println(users);
        return "/user/detailUser";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute(name = "user") Account account, @RequestParam(name = "avatarImage") MultipartFile file) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        account.setAvatar(fileName);
        userService.save(account);
        String uploadAvt = "./avatar-images/" + account.getId();
        Path uploadPath = Paths.get(uploadAvt);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePathMain = uploadPath.resolve(fileName);
            System.out.println("check : " + filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("Could not save upload file : " + fileName);
        }

        return "redirect:/infoUser";


    }


}
