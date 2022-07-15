package com.FIS.shoppingcart.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.FIS.shoppingcart.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.User;
import com.FIS.shoppingcart.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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

//    @GetMapping(value = "/product")
//    public String getAllProductForProductPage(Model model, HttpServletRequest request, HttpSession session) {
//
////        try {
////            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            model.addAttribute("id", principal.getId());
////            model.addAttribute("user", userService.getUserById(principal.getId()));
////        } catch (Exception e) {
////            e.getStackTrace();
////        }
//
//        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");
//
//        Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
//
//        Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
//                : Long.valueOf(request.getParameter("priceStart"));
//
//        Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
//                : Long.valueOf(request.getParameter("priceEnd"));
//
////        List<Product> listProducts = productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8);
//
//        String lowtohigh = request.getParameter("lowtohigh");
//
//        if(lowtohigh != null && lowtohigh != "") {
//            model.addAttribute("products", productService.getProductForProductPagePriceHigh(lowtohigh));
//        }else {
//            model.addAttribute("products", productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8));
//        }
//
//        request.setAttribute("page", page);
//        request.setAttribute("priceStart", priceStart);
//        request.setAttribute("priceEnd", priceEnd);
//        request.setAttribute("keyword", keyword);
//
//        model.addAttribute("cate", categoryService.findAllCategories());
//
//        return "product";
//    }
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



    @GetMapping("/infoUser")
    public String infoUser(Model model) {
        User users = userService.getUserById(52);
        model.addAttribute("user",users);
        System.out.println(users);
         return "/user/detailUser";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute (name = "user") User user, @RequestParam (name = "avatarImage") MultipartFile file) throws IOException {
        String fileName =org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        user.setAvatar(fileName);
        userRepository.save(user);
        String uploadAvt = "./avatar-images/" + user.getId();
        Path uploadPath = Paths.get(uploadAvt);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = file.getInputStream()){
            Path filePathMain = uploadPath.resolve(fileName);
            System.out.println("check : "+filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e){
            throw new IOException("Could not save upload file : " + fileName);
        }

        return "redirect:/infoUser";





    }






}
