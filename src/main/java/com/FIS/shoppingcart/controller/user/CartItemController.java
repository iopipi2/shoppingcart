package com.FIS.shoppingcart.controller.user;

import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.model.CartItemDTO;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class CartItemController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @PostMapping("/add-to-cart")
    public String AddToCart(@RequestParam(name = "id") int id, HttpSession session, HttpServletRequest request, Model model,
                            @RequestParam(name = "num-product") int numproduct) throws IOException {

        Optional<Product> product = productService.findProductById(id); // lay thong tin san pham
        Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart
        int totalOfCart = 0;
        double totalPrice = 0;
        double totalPriceAfterApplyCoupon = 0;

        if (object == null) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setProduct(product);
            cartItemDTO.setQuantity(numproduct);
            Map<Integer, CartItemDTO> map = new HashMap<>();
            map.put(id, cartItemDTO);
            session.setAttribute("cart", map);

            totalOfCart += numproduct;
            totalPrice = (numproduct*map.get(id).getProduct().get().getPrice());
            totalPriceAfterApplyCoupon = totalPrice;


        } else {
            Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;// lay ra map
            CartItemDTO cartItemDTO = map.get(id);

            if (cartItemDTO == null) {  //neu chua co sp trong map thi lay tt sp va sl sp =1
                cartItemDTO = new CartItemDTO();
                cartItemDTO.setProduct(product);
                cartItemDTO.setQuantity(numproduct);
                map.put(id, cartItemDTO);

                Set<Integer> set = map.keySet();
                for(Integer key : set) {

                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().get().getPrice()*map.get(key).getQuantity();
                    totalPriceAfterApplyCoupon = totalPrice;

                }


            } else { // neu co sp trong map roi thi tang sl cua sp len
                cartItemDTO.setQuantity(cartItemDTO.getQuantity() + numproduct);

                Set<Integer> set = map.keySet();
                for(Integer key : set) {

                    totalOfCart += map.get(key).getQuantity();
                    totalPrice += map.get(key).getProduct().get().getPrice()*map.get(key).getQuantity();
                    totalPriceAfterApplyCoupon = totalPrice;

                }
            }
        }

        session.setAttribute("totalPriceAfterApplyCoupon",totalPriceAfterApplyCoupon);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("totalOfCart", totalOfCart);

        return "redirect:/product-detail?id=" + id;
    }






}
