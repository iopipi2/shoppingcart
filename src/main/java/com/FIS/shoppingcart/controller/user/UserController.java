package com.FIS.shoppingcart.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.FIS.shoppingcart.entities.Category;
import com.FIS.shoppingcart.service.CategoryService;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.impl.CategoryServiceImpl;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {

//    @Autowired
//    UserRepository userRepo;
//
//    @Autowired
//    UserService userService;
    @Autowired
    @Qualifier("categoryService")
    private CategoryServiceImpl categoryService;

    @Autowired
    @Qualifier("productService")
    private ProductServiceImpl productService;

    //comerce

    @GetMapping(value = "/trang-chu")
    public String getAllProduct(Model model, HttpServletRequest request, @ModelAttribute("categories") Category category, HttpSession session) {

        //Object object = session.getAttribute("cart");// Tạo ngay lập tức một session 'cart' ngay cả khi khách hàng chưa thêm giỏ hàng để tránh bị null
//        try {
//            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("id", principal.getId());
//            model.addAttribute("user", userService.getUserById(principal.getId()));
//        } catch (Exception e) {
//            e.getStackTrace();
//        }

        model.addAttribute("products", productService.findAllProducts());

        model.addAttribute("cate", categoryService.findAllCategories());

        return "index";
    }

//    @GetMapping("/member/user")
//    public String memberUser(Model model, int id) {
//
//        model.addAttribute("user", userService.getUserById(id));
//
//        return "/informationUser";
//    }
//
//    @PostMapping("/member/user/edit")
//    public String editInfomationMember(@ModelAttribute(name = "user") UserDTO userDTO, @RequestParam(name="avatarImage") MultipartFile file) throws IOException {
//
//        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
//
//        userDTO.setAvatar(fileName);
//
//        User user =  userService.editUser(userDTO);
//
//        String uploadDir = "./avatar-images/" + user.getId();
//
//        Path uploadPath = Paths.get(uploadDir);
//
//        if(!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        try(InputStream inputStream = file.getInputStream()){
//            Path filePathMain = uploadPath.resolve(fileName);
//            System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());
//
//            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
//        }catch(IOException e) {
//            throw new IOException("Could not save upload file : " + fileName);
//        }
//
//        return "redirect:/trang-chu";
//    }

}
