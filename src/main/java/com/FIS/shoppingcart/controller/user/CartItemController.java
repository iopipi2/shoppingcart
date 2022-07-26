package com.FIS.shoppingcart.controller.user;

import com.FIS.shoppingcart.dao.CartLineRepository;
import com.FIS.shoppingcart.entities.CartLine;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.model.CartItemDTO;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class CartItemController {
    @Autowired
    ProductService productService;

    @Autowired
    CartLineRepository cartLineRepository;

    @Autowired
    UserService userService;


//    @GetMapping("/cart")
//    public String getAllCartItem(Model model, @AuthenticationPrincipal Authentication authentication, HttpSession session) {
//
//        Object object = session.getAttribute("cart");
//
//        try {
//            LoginService principal = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("id", principal.getId());
//            model.addAttribute("user", userService.getUserById(principal.getId()));
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
//
//        return "shoping-cart";
//    }


    @RequestMapping("/add-to-cart")
    public String AddToCart(@RequestParam(name = "id") int id, HttpSession session, HttpServletRequest request, Model model,
                            @RequestParam(name = "num-product") int numproduct) throws IOException {

        Optional<Product> product = productService.findProductById(id); // lay thong tin san pham
        Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart
        int totalOfCart = 0;
        double totalPrice = 0;
        double totalPriceAfterApplyCoupon = 0;

        if (object == null) {
            CartLine cartLine = new CartLine();
            cartLine.setProduct(product.get());
            cartLine.setQuantity(numproduct);
            Map<Integer, CartLine> map = new HashMap<>();// gio hang
            map.put(id, cartLine);
            session.setAttribute("cart", map);


            totalOfCart += numproduct;
            totalPrice = (numproduct*map.get(id).getProduct().getPrice());
            totalPriceAfterApplyCoupon = totalPrice;
            cartLineRepository.save(cartLine);


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
                    totalPriceAfterApplyCoupon = totalPrice;

                }



            } else { // neu co sp trong map roi thi tang sl cua sp len
                cartLine.setQuantity(cartLine.getQuantity() + numproduct);

                Set<Integer> set = map.keySet();
                for(Integer key : set) {

                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().getPrice()*map.get(key).getQuantity();
                    totalPriceAfterApplyCoupon = totalPrice;

                }

            }
        }

        session.setAttribute("totalPriceAfterApplyCoupon",totalPriceAfterApplyCoupon);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("totalOfCart", totalOfCart);

        return "redirect:/product-detail?id=" + id;
    }

    @SuppressWarnings({"deprecation","unchecked","unused"})
    @GetMapping(value = "/delete-from-cart")
    public String deleteFromCart(HttpServletRequest req, @RequestParam(name = "key", required = true) int key){
        HttpSession session = req.getSession();
        Object object = session.getAttribute("cart");

        int totalOfCart = (int) session.getValue("totalOfCart");
        double totalPrice = (double) session.getValue("totalPrice");
        double totalPriceAfterApplyCoupon = (double) session.getValue("totalPriceAfterApplyCoupon");

        if(object != null) {
            Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;
            session.setAttribute("totalOfCart",totalOfCart - map.get(key).getQuantity());
            session.setAttribute("totalPrice", totalPrice - map.get(key).getQuantity()*map.get(key).getProduct().getPrice());
            session.setAttribute("totalPriceAfterApplyCoupon", totalPriceAfterApplyCoupon - map.get(key).getQuantity()*map.get(key).getProduct().getPrice());

            map.remove(key);
            session.setAttribute("cart",map);
        }

        return "redirect:/cart";

    }





    }












