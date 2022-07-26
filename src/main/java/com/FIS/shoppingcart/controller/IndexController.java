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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
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

        model.addAttribute("products", productService.findAllProducts());

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

//        long numberOfReview = reviewDao.countById(id);
//
//        model.addAttribute("numberOfReview", numberOfReview);
//
//        System.out.println(numberOfReview);
//
//        model.addAttribute("reviews", reviewService.find(id));
        productService.findProductById(id).ifPresent(p->model.addAttribute("products",p));

//        model.addAttribute("products", product);

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
    @PostMapping(value = "/add-to-cart")
    public String AddToCart(HttpSession session, Model model,
                            @ModelAttribute("cartForm") CartLine cartLineForm) throws IOException {
        LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userid", principal.getId());
        model.addAttribute("user", userService.findUserByEmail(principal.getUsername()));
        Account account= userService.findUserByEmail(principal.getUsername());
        int productId=cartLineForm.getId();
        Optional<Product> product = productService.findProductById(productId); // lay thong tin san pham
        Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart

        CartDTO giohang=null;
        //check object
        if(object!=null)
        {
            giohang= (CartDTO) object;
            List<CartItemDTO> cartItemDTOS=giohang.getItemInCart();

        }
        else {
            giohang=new CartDTO();
            session.setAttribute("cart",giohang);
        }
        int numorder=cartLineForm.getNumorder();
        List<CartItemDTO> sanphamTrongGioHangs=giohang.getItemInCart();
        boolean sanPhamDaCoTrongGioHangPhaiKhong = false;

        // trường hợp đã có sản phẩm trong giỏ hàng.
        for(CartItemDTO item : sanphamTrongGioHangs) {
            if(item.getId() == product.get().getId()) {
                sanPhamDaCoTrongGioHangPhaiKhong = true;
                item.setQuantity(item.getQuantity() + numorder);
            }
            item.setTongGia((new BigDecimal(String.valueOf( item.getQuantity()))).multiply(new BigDecimal(String.valueOf(item.getPrice()))));
        }

        // nếu sản phẩm chưa có trong giỏ hàng.
        if(!sanPhamDaCoTrongGioHangPhaiKhong) {
            Optional<Product> productDto = productService.findProductById(product.get().getId());
            CartItemDTO addNewProduct= new CartItemDTO();
            addNewProduct.setProduct(productDto.get());
            addNewProduct.setTongGia((new BigDecimal(String.valueOf(numorder))).multiply(productDto.get().getPrice()));
            sanphamTrongGioHangs.add(addNewProduct);
            giohang.setItemInCart(sanphamTrongGioHangs);
        }
        BigDecimal sum = BigDecimal.ZERO;
        for(CartItemDTO item : sanphamTrongGioHangs) {
            sum = sum.add(item.getTongGia());
            giohang.setPriceTotal(sum);
        }
        session.setAttribute("cart",giohang);



        //Cart cart= (Cart)session;

        Cart cart= new Cart();
        BeanUtils.copyProperties(giohang, cart);


        cart.setBuyer(account);
        cartService.saveCart(cart);
        return "redirect:/product-detail?id=" + productId;
    }


//    @RequestMapping(value = { "/xoa-sp-gio-hang" }, method = RequestMethod.POST)
//    public ResponseEntity<AjaxResponse> xoaSP_in_Cart(@RequestBody ProductDTO sanPhamTrongGioHang,
//                                                      final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
//            throws IOException {
//        HttpSession httpSession = request.getSession();
//
//        CartDTO gioHang = (CartDTO) httpSession.getAttribute("cart");
//        ProductDTO itemRemove = new ProductDTO();
//        for (ProductDTO item : gioHang.getItemInCart()) {
//            if(item.getId() == sanPhamTrongGioHang.getId())
//            {
//                itemRemove = item;
//            }
//        }
//        gioHang.getItemInCart().remove(itemRemove);
//
//        BigDecimal sum = BigDecimal.ZERO;
//        for(ProductDTO item : gioHang.getItemInCart()) {
//            sum = sum.add(item.getTongGia());
//        }
//        Locale localeVN = new Locale("vi", "VN");
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//        String total = currencyVN.format(sum);
//        httpSession.setAttribute("tong_gia", sum);
//        httpSession.setAttribute("SL_SP_GIO_HANG", getTotalItems(request));
//        ParseNumToString parseNumToString=new ParseNumToString();
//        parseNumToString.setSoLuong(String.valueOf(getTotalItems(request)));
//        parseNumToString.setTongGia(total);
//        return ResponseEntity.ok(new AjaxResponse(200, parseNumToString));
//    }
//
//    @RequestMapping(value = { "/update-sp-gio-hang" }, method = RequestMethod.POST)
//    public ResponseEntity<AjaxResponse> update_SP_in_Cart(@RequestBody ProductDTO productInCart,
//                                                          final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
//            throws IOException {
//        HttpSession httpSession = request.getSession();
//        CartDTO gioHang = (CartDTO) httpSession.getAttribute("cart");
//        for (ProductDTO item : gioHang.getItemInCart()) {
//            if(item.getId() == productInCart.getId())
//            {
//                item.setProductquantity(productInCart.getProductquantity());
//                item.setTongGia(item.getPrice().multiply(new BigDecimal(item.getProductquantity())));
//                productInCart.setTongGia(item.getTongGia());
//            }
//        }
//        BigDecimal sum = BigDecimal.ZERO;
//        for(ProductDTO item : gioHang.getItemInCart()) {
//            sum = sum.add(item.getTongGia());
//        }
//        Locale localeVN = new Locale("vi", "VN");
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//        String tongGia =currencyVN.format(sum);
//        String soLuong = currencyVN.format(productInCart.getTongGia());
//        ParseNumToString parseNumToString = new ParseNumToString();
//        parseNumToString.setTongGia(tongGia);
//        parseNumToString.setSoLuong(soLuong);
//        httpSession.setAttribute("tong_gia", sum);
//        return ResponseEntity.ok(new AjaxResponse(200, parseNumToString));
//    }


    //Cart
    @GetMapping("/My Order")
    public String viewallCart(Model model,@RequestParam int id) {
        //Test find by user id
        //List<Cart> allcart=cartService.findAllCart();

        List<CartLine> cartItem = cartLineService.findCartLineByCartId(id);
        System.out.println(cartItem);
        model.addAttribute("cartLines", cartItem);

        return "";
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


//    @GetMapping("/{id}/update")
//    public String updateCart(@PathVariable int id, @RequestParam int count) {
//        CartLine cartLine = cartLineService.findCartLineById(id);
//        if (cartLine != null) {
//            Product product = cartLine.getProduct();
//            double oldTotal = cartLine.getTotal();
//            if (product.getProductquantity() <= count) {
//                count = product.getQuantity();
//            }
//            cartLine.setQuantity(count);
//            cartLine.set(product.getUnitPrice());
//            cartLine.setTotal(product.getUnitPrice() * count);
//            String response = cartLineService.updateCartLine(cartLine) + "";
//            Cart cart = cartService.findCart();
//            cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
//            cartService.updateCart(cart);
//            return "redirect:/cart/show?result=updated";
//        } else {
//            return "redirect:/cart/show?result=error";
//        }
//    }

//    @GetMapping("/{id}/delete")
//    public String deleteCart(@PathVariable int id) {
//        // TODO : fetch the cartLine
//        CartLine cartLine = cartLineService.findCartLineById(id);
//        if (cartLine != null) {
//            Cart cart = cartService.findCart();
//            cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
//            cart.setCartLines(cart.getCartLines() - 1);
//            cartService.updateCart(cart);
//            // TODO : remove the cartLine
//            cartLineService.deleteCartLine(cartLine);
//            return "redirect:/cart/show?result=deleted";
//        } else {
//            return "redirect:/cart/show?result=error";
//        }
//    }
//



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

//    @GetMapping("/show/{id}/product")
//    public ModelAndView showSingleProduct(@PathVariable("id") int id) throws ProductNotFoundExceptoion {
//
//        ModelAndView modelAndView = new ModelAndView("page");
//
//        Product product = productService.findProductById(id);
//
//        productService.updateProduct(product);
//        modelAndView.addObject("title", product.getName());
//        modelAndView.addObject("product", product);
//        modelAndView.addObject("userClickShowProduct", true);
//
//        return modelAndView;
//
//    }

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