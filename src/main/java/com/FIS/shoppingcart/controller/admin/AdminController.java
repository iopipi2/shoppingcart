package com.FIS.shoppingcart.controller.admin;


import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;


    //View infomation of users
    @GetMapping("/listAllUsers")
    public String getAllUser(Model model) {
        List<Account> users = userService.listAll();
        System.out.println(users);

        model.addAttribute("user", users);
//        model.addAttribute("u", new Account());

        return "/admin/viewInfoUsers";
    }


    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String saveAccounts(@ModelAttribute("user") Account account,
                              @RequestParam(name = "avatarImage") MultipartFile file) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        account.setAvatar(fileName);
        userService.saveInfoUser(account);
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

        return "redirect:/listAllUsers";

    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam int id) {
        Optional<Account> opt = userService.findUserById(id);
        userService.deleteUser(opt.get());
        return "redirect:/listAllUsers";
    }

    //View all account
    @GetMapping("/listAllAccount")
    public String getAllAccount(Model model) {
        List<Account> users = userService.listAll();
        System.out.println(users);

        model.addAttribute("user", users);
//        model.addAttribute("u", new Account());

        return "/admin/viewAccount";
    }

    @GetMapping("/changeStatusAccount")
    public String changeStatusAccount(@RequestParam int id) {
        Optional<Account> account = userService.findUserById(id);
        if (account.isPresent()) {
//            account.get().setEnabled(Boolean.valueOf("True"));
//            userRepository.save(account.get());

            Account acc = account.get();
            acc.setEnabled(!acc.getEnabled());
            userRepository.save(account.get());
        }
        return "redirect:/listAllAccount";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public ModelAndView findUser(@Param("keyword") String keyword) {
        ModelAndView mav = new ModelAndView("/admin/viewAccount");
        if (keyword == null) {
            keyword = "";
        }
        try {
            List<Account> all = userService.findUser(keyword);
            for (Account u : all) {
            }
            mav.addObject("user", all);
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "Invalid ID");
        }
        return mav;
    }


}