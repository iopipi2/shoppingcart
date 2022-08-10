package com.FIS.shoppingcart.controller;
import com.FIS.shoppingcart.entities.*;
import com.FIS.shoppingcart.model.*;
import com.FIS.shoppingcart.service.CategoryService;
import com.FIS.shoppingcart.service.LoginService;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.impl.CartLineServiceImpl;
import com.FIS.shoppingcart.service.impl.CartServiceImpl;
import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import java.text.NumberFormat;



@Controller
public class IndexController {

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;
    @Autowired

    private UserServiceImpl userService;

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private CartLineServiceImpl cartLineService;

    //comerce

//=======================================================================Product=====================================================================
    //View trang chu customer
    @GetMapping(value = "/trang-chu")
    public String getAllProduct(Model model, HttpServletRequest request, @ModelAttribute("categories") Category category, HttpSession session) {

        Object object = session.getAttribute("cart");// Tạo ngay lập tức một session 'cart' ngay cả khi khách hàng chưa thêm giỏ hàng để tránh bị null
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.findUserByEmail(principal.getUsername()));
        } catch (Exception e) {
            e.getStackTrace();
        }
        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

        Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
                : Long.valueOf(request.getParameter("priceStart"));

        Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
                : Long.valueOf(request.getParameter("priceEnd"));

        String lowtohigh = request.getParameter("lowtohigh");

        if (lowtohigh != null && lowtohigh != "") {
            model.addAttribute("products", productService.getProductForProductPagePriceHigh(lowtohigh));
        } else {
            model.addAttribute("products", productService.getProductForProductPage(keyword, priceStart, priceEnd, 0, page * 8));
        }

        request.setAttribute("page", page);
        request.setAttribute("priceStart", priceStart);
        request.setAttribute("priceEnd", priceEnd);
        request.setAttribute("keyword", keyword);

        model.addAttribute("cate", categoryService.findAllCategories());

        return "index";
    }

    //View product detail
    @GetMapping("/product-detail")
    public String getProductById(Model model, @RequestParam int id,
                                 HttpSession session) {
            Optional<Product> product=productService.findProductById(id);
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.findUserByEmail(principal.getUsername()));
        } catch (Exception e) {
            e.getStackTrace();
        }


        productService.findProductById(id).ifPresent(p->model.addAttribute("products",p));


        return "product-detail";
    }


    //Filter product by price, category,...
    @GetMapping(value = "/product")
    public String getAllProductForProductPage(Model model, HttpServletRequest request, HttpSession session) {
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", userService.findUserByEmail(principal.getUsername()));
        } catch (Exception e) {
            e.getStackTrace();
        }

        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

        Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

        Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
                : Long.valueOf(request.getParameter("priceStart"));

        Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
                : Long.valueOf(request.getParameter("priceEnd"));

//        List<Product> listProducts = productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8);

        String lowtohigh = request.getParameter("lowtohigh");

        if(lowtohigh != null && lowtohigh != "") {
            model.addAttribute("products", productService.getProductForProductPagePriceHigh(lowtohigh));
        }else {
            model.addAttribute("products", productService.getProductForProductPage(keyword ,priceStart, priceEnd, 0, page * 8));
        }

        request.setAttribute("page", page);
        request.setAttribute("priceStart", priceStart);
        request.setAttribute("priceEnd", priceEnd);
        request.setAttribute("keyword", keyword);

        model.addAttribute("cate", categoryService.findAllCategories());

        return "product";
    }

    //Add item to cart
    @RequestMapping("/add-to-cart")
    public String addToCart(@RequestParam(name = "id") int id, HttpSession session, HttpServletRequest request, Model model,
                            @RequestParam(name = "num-product") int numproduct) throws IOException {
        Optional<Product> product = productService.findProductById(id); // lay thong tin san pham
        Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart
        int totalOfCart = 0;
        double totalPrice =0;
        if (object == null) {
            CartLine cartLine = new CartLine();
            cartLine.setProduct(product.get());
            cartLine.setQuantity(numproduct);
            Map<Integer, CartLine> map = new HashMap<>();// gio hang
            map.put(id, cartLine);
            session.setAttribute("cart", map);
            totalOfCart += numproduct;
            totalPrice = numproduct*product.get().getPrice();
        } else {
            Map<Integer, CartLine> map = (Map<Integer, CartLine>) object;// lay ra map
            CartLine cartLine = map.get(id);
            if (cartLine == null) {  //neu chua co sp trong map thi lay tt sp va sl sp =1
                cartLine = new CartLine();
                cartLine.setProduct(product.get());
                cartLine.setQuantity(numproduct);
                map.put(id, cartLine);
                Set<Integer> set = map.keySet();
                for(Integer key : set) {
                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice()*map.get(key).getQuantity();
                }
            } else { // neu co sp trong map roi thi tang sl cua sp len

                cartLine.setQuantity(cartLine.getQuantity() + numproduct);
                Set<Integer> set = map.keySet();
                for(Integer key : set) {
                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice()*map.get(key).getQuantity();
                }
            }
        }
        System.out.println(id);
        System.out.println(numproduct);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("totalOfCart", totalOfCart);
        return "redirect:/product-detail?id=" + id;
    }
    //Update cart
    @PostMapping("/update-cart")
    public String updateCart( Model model,@RequestParam(name = "id") int id, HttpServletRequest req) {
        HttpSession session = req.getSession();
        double totalPrice = 0;
        Object object = session.getAttribute("cart");
        Integer totalOfCart = (Integer) session.getAttribute("totalOfCart");
            Map<Integer, CartLine> map = (Map<Integer, CartLine>) object;
            CartLine cartLine = map.get(id);
             // neu co sp trong map roi thi tang sl cua sp len
                cartLine.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                Set<Integer> set = map.keySet();
                for (Integer key : set) {
                    session.removeAttribute("totalOfCart");
                    session.removeAttribute("totalPrice");
                    totalOfCart += cartLine.getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice()*Integer.parseInt(req.getParameter("quantity"));
                }
                session.setAttribute("totalOfCart", totalOfCart);
                session.setAttribute("totalPrice", totalPrice);
                session.setAttribute("cart", map);
            return "redirect:/trang-chu";
        }

    @SuppressWarnings({ "deprecation", "unchecked", "unused" })
    @GetMapping(value = "/delete-from-cart")
    public String Deletefromtocart(HttpServletRequest req, @RequestParam(name = "key", required = true) int key) {
        HttpSession session = req.getSession();
        Object object = session.getAttribute("cart");
        int totalOfCart = (int) session.getValue("totalOfCart");
        double totalPrice = Double.parseDouble(session.getValue("totalPrice").toString());
        if (object != null) {
            Map<Integer, CartLine> map = (Map<Integer, CartLine>) object;
            session.setAttribute("totalOfCart", totalOfCart - map.get(key).getQuantity());
            session.setAttribute("totalPrice", totalPrice - map.get(key).getQuantity() * map.get(key).getProduct().getPrice());
            map.remove(key);
            session.setAttribute("cart", map);
        }
        return "redirect:/trang-chu";
    }
    @PostMapping(value = "/check-out")
    public String checkout(HttpSession session, @ModelAttribute("checkout") CartLine cartLine1,HttpServletRequest request
                           ) throws IOException {
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account= userService.findUserByEmail(principal.getUsername());
        Cart cart=new Cart();
        cart.setBuyer(account);
        cart.setBuyDate(new Date());
        cart.setStatus("pending");
        String total= session.getValue("totalPrice").toString();
        cart.setPriceTotal(Double.parseDouble(total));

        List<CartLine>cartLines= new ArrayList<CartLine>();
        Object object = session.getAttribute("cart");
        Integer totalOfCart = (Integer) session.getAttribute("totalOfCart");
        Map<Integer, CartLine> map = (Map<Integer, CartLine>) object;
        Set<Integer> set = map.keySet();
        for (Integer key : set) {

                CartLine cartLine = new CartLine();
                cartLine.setCart(cart);

                Product product = productService.findProductById(map.get(key).getProduct().getId()).get();
                if (map.get(key).getQuantity() > product.getProductquantity()) {
                    String mess = "Vui lòng chọn lại số lượng";
                    return mess;
                } else {
                    product.setProductquantity(product.getProductquantity() - map.get(key).getQuantity());
                    productService.updateProduct(product);
                }
                cartLine.setQuantity(map.get(key).getQuantity());
                cartLine.setProduct(product);
                cartLines.add(cartLine);
        }
        cart.setCartItem(cartLines);
        cartService.saveCart(cart);
        session.removeAttribute("cart");
        session.removeAttribute("totalPrice");
        session.removeAttribute("totalOfCart");
        return "redirect:/trang-chu" ;
    }
    @GetMapping(value="/cancel-cart")
    public String cancelCart(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object object = session.getAttribute("cart");
        session.removeAttribute("cart");
        session.removeAttribute("totalPrice");
        session.removeAttribute("totalOfCart");
        session.getAttribute("cart");
//        if (object != null) {
//            Map<Integer, CartLine> map = (Map<Integer, CartLine>) object;
//            map.remove(object);
//            session.setAttribute("cart",map);
//        }
        return "redirect:/trang-chu";
    }

    //ViewCart
    @GetMapping("/cart/viewcart")
    public String viewCartLine(Model model,
                               HttpSession session,HttpServletRequest request) {
        Object object = session.getAttribute("cart");
        int id=1;
        try {
            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account user= userService.findUserByEmail(principal.getUsername());
            model.addAttribute("id", principal.getId());
            model.addAttribute("user", user);

        } catch (Exception e) {
            e.getStackTrace();
        }
        List<CartLine> cartLines = cartLineService.findCartLines();
        model.addAttribute("cart",object);
        model.addAttribute("cartLines", cartLines);
        return "cart";
    }
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

    //Cua Hoang
    @GetMapping("/infoUser")
    public String infoUser(Model model) {

        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findUserByEmail(principal.getUsername()).getId();
        Account users = userService.getUserById(id);
        model.addAttribute("user", users);
        System.out.println(users);
        return "/detailUser";
    }

    //Cua Hoang
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute(name = "user") Account account, @RequestParam(name = "avatarImage") MultipartFile file) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        account.setAvatar(fileName);
        String uploadAvt = "./avatar-images/" + account.getId();
        Path uploadPath = Paths.get(uploadAvt);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePathMain = uploadPath.resolve(fileName);
            System.out.println("check : " + filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);
            userService.save(account);

        } catch (IOException e) {
            System.out.println(e);
        }

        return "redirect:/infoUser";

    }

    @RequestMapping(value = "/editInfo", method = RequestMethod.POST)
    public String editInfomationAccount(@ModelAttribute(name = "users") Account account) {
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findUserByEmail(principal.getUsername()).getId();
        account.setAvatar(userService.getUserById(account.getId()).getAvatar());
        userService.save(account);
        return "redirect:/infoUser";
    }
    @RequestMapping("/ViewResetPassword")
    public ModelAndView updatePassword() {
        ModelAndView mav = new ModelAndView("changePassword");
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = userService.getUserById(principal.getId());
        mav.addObject("account", account);
        userService.save(account);
        return mav;
    }

    //Cua Hoang----------------------------------------------------------------------------------
    @RequestMapping("/editPassword")
    public String changePass(Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("confirmPass");
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findUserByEmail(principal.getUsername()).getId();
        Account account = userService.getUserById(id);
        model.addAttribute("title", "Reset your password");
        if (account == null) {
            model.addAttribute("message", "Invalid id");
            return "message";
        } else {
            userService.updatePassword(account, password);
            model.addAttribute(account);
        }

        return "redirect:/infoUser";


    }
    //Cua Hoang----------------------------------------------------------------------------------
//    @GetMapping("/viewListCart")
//    public String viewAllCart(Model model) {
//        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        int id= userService.findUserByEmail(principal.getUsername()).getId();
//        List<Cart> findAllCartByUserID = cartService.findCartByBuyerId(id);
//        model.addAttribute("carts", findAllCartByUserID);
//        return "/viewCartDetail";
//    }

    @GetMapping("/cart/cartline")
    public String viewCartLine(Model model, @RequestParam int id) {
        List<CartLine> cartLines = cartLineService.findCartLineByCartId(id);
        model.addAttribute("cartLines", cartLines);
        return "/viewCartLineUser";
    }

    @GetMapping("/viewCart")
    public String viewCart(Model model) {
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findUserByEmail(principal.getUsername()).getId();
        String done = "done";
        List<Cart> findAllCartDoneByUserID = cartService.findCartDone(id, done);
        model.addAttribute("cartStatusDone", findAllCartDoneByUserID);
        return "/viewCartUser";
    }

    @GetMapping("/viewListCart")
    public String pagingCartView(Model model,HttpServletRequest request){

        return listByPage(model,1);

    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage){
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.findUserByEmail(principal.getUsername()).getId();
        System.out.println(currentPage);
        Page<Cart> pagingCart = cartService.listAll(currentPage);


        int totalPage = pagingCart.getTotalPages();
        System.out.println(pagingCart.getTotalPages());
        System.out.println(pagingCart.getTotalElements());
        System.out.println(pagingCart.getNumber());
        System.out.println(pagingCart.getNumberOfElements());
        System.out.println(pagingCart.getSize());

        List<Cart> listCart = new ArrayList<>();
        for(Cart c: pagingCart ){
            if(c.getBuyer().getId() == id){
                listCart.add(c);
            }
        }

        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("listCart",listCart);

        return "/viewCartDetail";
    }
}