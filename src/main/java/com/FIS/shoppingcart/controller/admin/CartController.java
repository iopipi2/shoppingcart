package com.FIS.shoppingcart.controller.admin;
import com.FIS.shoppingcart.dao.CartRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.CartLine;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.model.CartItemDTO;
import com.FIS.shoppingcart.service.LoginService;
import com.FIS.shoppingcart.service.impl.CartLineServiceImpl;
import com.FIS.shoppingcart.service.impl.CartServiceImpl;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import com.FIS.shoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class CartController {

    @Autowired

    private CartLineServiceImpl cartLineService;

    @Autowired

    private ProductServiceImpl productService;

    @Autowired
    private UserServiceImpl userService;
    @Autowired

    private CartServiceImpl cartService;

    @Autowired

    private CartRepository cartRepository;


    //View All Cart By Admin
    //View All Cart By Admin
    @GetMapping("/admin/cart")
    public String viewallCart(Model model) {

        return listCartByAdmin(model,1);
    }

    @GetMapping("/admin/cart/page/{pageNumber}")
    public String listCartByAdmin(Model model,@PathVariable(name = "pageNumber") int currentPage){

        Page<Cart> pagingCart = cartService.listAllByAdmin(currentPage);

        int totalPage = pagingCart.getTotalPages();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("listCart",pagingCart.getContent());
        return "/admin/viewCart";
    }


    //View All Cart Item In Cart By Admin
    @GetMapping("/admin/cart/cartline")
    public String viewCartLine(Model model, @RequestParam int id,
                               HttpSession session) {

        List<CartLine> cartLines = cartLineService.findCartLineByCartId(id);
        System.out.println("${cartLines.get().getProduct().getId()}");
        model.addAttribute("cartLines", cartLines);
        return "/admin/viewCartLine.html";
    }

    @GetMapping("/changeStatusCart")
    public String changeStatusCart(@RequestParam int id) {
        Optional<Cart> cart = cartService.findCartById(id);
        if (cart.isPresent()) {
            cart.get().setStatus("Done");
            cartService.saveCart(cart.get());
        }
        return "redirect:/admin/cart";
    }




}

