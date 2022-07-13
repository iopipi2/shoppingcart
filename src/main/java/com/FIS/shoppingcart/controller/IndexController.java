package com.FIS.shoppingcart.controller;
import com.FIS.shoppingcart.entities.Category;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.service.CategoryService;
import com.FIS.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

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

    @GetMapping("/product-detail")
    public String getProductById(Model model, @RequestParam(name = "id", required = false) int id,
                                 HttpSession session) {
//
//        try {
//            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("id", principal.getId());
//            model.addAttribute("user", userService.getUserById(principal.getId()));
//        } catch (Exception e) {
//            e.getStackTrace();
//        }

//        long numberOfReview = reviewDao.countById(id);
//
//        model.addAttribute("numberOfReview", numberOfReview);
//
//        System.out.println(numberOfReview);
//
//        model.addAttribute("reviews", reviewService.find(id));

        model.addAttribute("product", productService.findProductById(id));

        return "product-detail";
    }
//
//    @PostMapping(value = "/member/product-detail/review")
//    public String review(HttpServletRequest request, @ModelAttribute ReviewDTO reviewDTO, @RequestParam(name = "productId", required = false) Integer productId,
//                         @RequestParam(name = "starNumber") int starNumber, @RequestParam(name = "review") String review) {
//
//        LoginService loginService = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        int check = 0;
//        List<ReviewDTO> list = reviewService.find(productId);
//
//        if (list.isEmpty()) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setName(loginService.getName());
//            userDTO.setId(loginService.getId());
//            reviewDTO.setUserDTO(userDTO);
//            reviewDTO.setReview(review);
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setId(productId);
//            reviewDTO.setProductDTO(productDTO);
//            reviewDTO.setStarNumber(starNumber);
//            reviewService.add(reviewDTO);
//        }
//        for (ReviewDTO reviewDTO2 : list) {// kiem tra de moi nguoi dung chi comment dc  1 laan
//            if (reviewDTO2.getUserDTO().getId()==loginService.getId()) {
//                check = 1;
//                break;
//
//            } else {check=2;}
//        }
//        if (check == 2) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setName(loginService.getName());
//            userDTO.setId(loginService.getId());
//            reviewDTO.setUserDTO(userDTO);
//            reviewDTO.setReview(review);
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setId(productId);
//            reviewDTO.setProductDTO(productDTO);
//            reviewDTO.setStarNumber(starNumber);
//            reviewService.add(reviewDTO);
//        }
//
//
//        return "redirect:/product-detail?id=" + productId;
//    }

    @GetMapping(value = "/product")
    public String getAllProductForProductPage(Model model, HttpServletRequest request, HttpSession session) {

//        try {
//            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("id", principal.getId());
//            model.addAttribute("user", userService.getUserById(principal.getId()));
//        } catch (Exception e) {
//            e.getStackTrace();
//        }

        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

        Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
                : Long.valueOf(request.getParameter("priceStart"));

        Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
                : Long.valueOf(request.getParameter("priceEnd"));

        //List<ProductDTO> listProducts = productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8);

        String lowtohigh = request.getParameter("lowtohigh");
//
//        if(lowtohigh != null && lowtohigh != "") {
//            model.addAttribute("products", productService.getProductForProductPagePriceHigh(lowtohigh));
//        }else {
//            model.addAttribute("products", productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8));
//        }

        request.setAttribute("page", page);
        request.setAttribute("priceStart", priceStart);
        request.setAttribute("priceEnd", priceEnd);
        request.setAttribute("keyword", keyword);

        model.addAttribute("cate", categoryService.findAllCategories());

        return "product";
    }




    //onlineshoppingcart
    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("page");
        modelAndView.addObject("userClickHome", true);
        modelAndView.addObject("title", "Home");

        modelAndView.addObject("categories", categoryService.findAllCategories());

        return modelAndView;
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView("page");
        modelAndView.addObject("userClickContact", true);
        modelAndView.addObject("title", "Contact Us");

        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView("page");
        modelAndView.addObject("userClickAbout", true);
        modelAndView.addObject("title", "About Us");

        return modelAndView;
    }

    /*
     * Creating Custom Login Controller
     *
     *
     */

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "error", required = false) String error,
                              @RequestParam(name = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (error != null) {
            modelAndView.addObject("message", "Invalid User Name or Password!");
        }
        if (logout != null) {
            modelAndView.addObject("logout", "User has Successfully Logged out!");
        }
        modelAndView.addObject("title", "Login");

        return modelAndView;
    }

    /*
     * Methods to load all the products and based on category
     *
     */

    @GetMapping("/show/all/products")
    public ModelAndView showAllProducts() {
        ModelAndView modelAndView = new ModelAndView("page");
        modelAndView.addObject("userClickAllProducts", true);
        modelAndView.addObject("title", "All Products");

        // passing the list of categories

        modelAndView.addObject("categories", categoryService.findAllCategories());

        return modelAndView;
    }

    @GetMapping("/show/category/{id}/products")
    public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("page");

        Category category = null;
        category = categoryService.findCategoryById(id);

        modelAndView.addObject("userClickCategoryProducts", true);
        modelAndView.addObject("title", category.getType());

        // passing the list of categories

        modelAndView.addObject("categories", categoryService.findAllCategories());

        // passing the single category object
        modelAndView.addObject("category", category);

        return modelAndView;
    }

    /*
     * Viewing a single product
     */

    @GetMapping("/show/{id}/product")
    public ModelAndView showSingleProduct(@PathVariable("id") int id) throws ProductNotFoundExceptoion {

        ModelAndView modelAndView = new ModelAndView("page");

        Product product = productService.findProductById(id);




        productService.updateProduct(product);
        modelAndView.addObject("title", product.getName());
        modelAndView.addObject("product", product);
        modelAndView.addObject("userClickShowProduct", true);

        return modelAndView;

    }

    /*
     * Access denied Page
     */
    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.addObject("title", "403 - Access Denied");
        modelAndView.addObject("errorTitle", "Aha! Caught You");
        modelAndView.addObject("errorDescription", "You Are not authorized to Access this Page");
        return modelAndView;
    }

    /*
     * Logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // first we are going to fetch the authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login?logout";
    }

}