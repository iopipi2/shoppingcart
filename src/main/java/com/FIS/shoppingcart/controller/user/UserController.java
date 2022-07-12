package com.FIS.shoppingcart.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.User;
import com.FIS.shoppingcart.model.UserDTO;
import com.FIS.shoppingcart.service.UserService;

import antlr.StringUtils;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/member/user")
    public String memberUser(Model model, int id) {

        model.addAttribute("user", userService.getUserById(id));

        return "/informationUser";
    }

    @PostMapping("/member/user/edit")
    public String editInfomationMember(@ModelAttribute(name = "user") UserDTO userDTO, @RequestParam(name="avatarImage") MultipartFile file) throws IOException {

        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());

        userDTO.setAvatar(fileName);

        User user =  userService.editUser(userDTO);

        String uploadDir = "./avatar-images/" + user.getId();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = file.getInputStream()){
            Path filePathMain = uploadPath.resolve(fileName);
            System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e) {
            throw new IOException("Could not save upload file : " + fileName);
        }

        return "redirect:/trang-chu";
    }

}
