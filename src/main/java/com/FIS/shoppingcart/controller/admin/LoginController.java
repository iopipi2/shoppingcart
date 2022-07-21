package com.FIS.shoppingcart.controller.admin;

import javax.servlet.http.HttpServletRequest;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    UserServiceImpl userService;


    @GetMapping("/dang-nhap")
    public String hello(Model model) {


        model.addAttribute("user", new Account());

        return "login";
    }
    @GetMapping("/cai-nay-thu")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/cai-nay-de-thu")
    public ResponseEntity<Account> getCustomerById() throws Exception{
        String username="lequocvietdp1999@gmail.com";
        Account entity = userService.findUserByEmail(username);

        return new ResponseEntity<Account>(HttpStatus.OK);
    }
//    @GetMapping("/cai-nay-de-thu")
//    public String index(HttpSession session, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
//            throws Exception {
//        model.addAttribute("user",new User());
//        return "login";
//    }

    @PostMapping("/sign-up")
    public String login(@ModelAttribute("userDTO") Account account) {

        userService.addUser(account);

        return "/login";
    }

    @GetMapping("/access-deny")
    public String accessDeny(HttpServletRequest req) {
        return "access-deny";
    }

}
