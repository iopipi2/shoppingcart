package com.FIS.shoppingcart.controller.admin;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.CartLine;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.model.CartItemDTO;
import com.FIS.shoppingcart.service.impl.CartLineServiceImpl;
import com.FIS.shoppingcart.service.impl.CartServiceImpl;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private CartServiceImpl cartService;

    //View All Cart By Admin
    //View All Cart By Admin
    @GetMapping("/admin/cart")
    public String viewallCart(Model model) {
        //Test find by user id
        //List<Cart> allcart=cartService.findAllCart();
        List<Cart> allcart = cartService.findAllCart();
        System.out.println(allcart);
        model.addAttribute("carts", allcart);

        return "/admin/viewCart";
    }


    //View All Cart Item In Cart By Admin
    @GetMapping("/admin/cart/cartline")
    public String viewCartLine(Model model, @RequestParam int id,
                               HttpSession session) {

        List<CartLine> cartLines = cartLineService.findCartLineByCartId(id);
        System.out.println("${cartLines.get().getProduct().getId()}");
        model.addAttribute("cartLines", cartLines);
        return "/admin/viewCartLine";
    }

//    @GetMapping("/{id}/update")
//    public String updateCart(@PathVariable int id, @RequestParam int count) {
//        CartLine cartLine = cartLineService.findCartLineById(id);
//        if (cartLine != null) {
//            Product product = cartLine.getProduct();
//            double oldTotal = cartLine.getTotal();
//            if (product.getQuantity() <= count) {
//                count = product.getQuantity();
//            }
//            cartLine.setProductCount(count);
//            cartLine.setBuyingPrice(product.getUnitPrice());
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
//    @GetMapping("/add/{id}/product")
//    public String addCart(@PathVariable int id) {
//        // TODO : fetch the cart
//        Cart cart = cartService.findCart();
//        CartLine cartLine = cartLineService.findCartLineByCartIdAndProductId(cart.getId(), id);
//        if (cartLine != null) {
//
//			/*cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
//			cart.setCartLines(cart.getCartLines() - 1);
//			cartService.updateCart(cart);
//			// TODO : remove the cartLine
//			cartLineService.deleteCartLine(cartLine);
//			return "redirect:/cart/show?result=deleted";*/
//            return "";
//        } else {
//            cartLine = new CartLine();
//
//            Product product = productService.findProductById(id);
//            cartLine.setCartId(cart.getId());
//            cartLine.setProduct(product);
//            cartLine.setBuyingPrice(product.getUnitPrice());
//            cartLine.setProductCount(1);
//            cartLine.setTotal(product.getUnitPrice());
//            cartLine.setAvailable(true);
//            cartLineService.saveCartLine(cartLine);
//            cart.setCartLines(cart.getCartLines() + 1);
//            cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
//            cartService.saveCart(cart);
//            return "redirect:/cart/show?result=added";
//        }
//    }
}

