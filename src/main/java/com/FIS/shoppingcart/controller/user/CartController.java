package com.FIS.shoppingcart.controller.user;


import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.CartLine;
import com.FIS.shoppingcart.service.impl.CartLineServiceImpl;
import com.FIS.shoppingcart.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;



@Controller
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    CartLineServiceImpl cartLineService;

    @GetMapping("/viewListCart")
    public String viewAllCart(Model model) {
        List<Cart> allCart = cartService.findAllCart();
        model.addAttribute("carts", allCart);

        return "/user/viewCartDone";
    }

    @GetMapping("/user/cart/cartline")
    public String viewCartLine(Model model, @RequestParam int id) {

        List<CartLine> cartLines = cartLineService.findCartLineByCartId(id);
        model.addAttribute("cartLines", cartLines);
        return "/user/viewCartLine";
    }


}
