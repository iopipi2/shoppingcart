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

    @GetMapping("/admin/cart")
    public String viewallCart(Model model) {
        //Test find by user id
        //List<Cart> allcart=cartService.findAllCart();
        List<Cart> allcart= cartService.findCartByBuyerId(52);
        System.out.println(allcart);
        model.addAttribute("carts", allcart);

        return "/admin/viewCart";
    }




    @GetMapping("/admin/cart/cartline")
    public String viewCartLine(Model model,
                               HttpSession session)
    {
        int id=1;
        List<CartLine> cartLines= cartLineService.findCartLineByCartId(id);
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
@RequestMapping("/add-to-cart")
public String AddToCart(@RequestParam(name = "id") int id, HttpSession session, HttpServletRequest request, Model model,
                        @RequestParam(name = "num-product") int numproduct) throws IOException {

    Optional<Product> product = productService.findProductById(id); // lay thong tin san pham
    Object object = session.getAttribute("cart"); //lay session neu co , neu chua co tao 1 session moi la cart
    int totalOfCart = 0;
    double totalPrice = 0;
    double totalPriceAfterApplyCoupon = 0;

    if (object == null) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProduct(product.get());
        cartItemDTO.setQuantity(numproduct);
        Map<Integer, CartItemDTO> map = new HashMap<>();// gio hang
        map.put(id, cartItemDTO);
        session.setAttribute("cart", map);

        totalOfCart += numproduct;
        totalPrice = (numproduct*map.get(id).getProduct().getPrice());
        totalPriceAfterApplyCoupon = totalPrice;


    } else {
        Map<Integer, CartItemDTO> map = (Map<Integer, CartItemDTO>) object;// lay ra map
        CartItemDTO cartItemDTO = map.get(id);

        if (cartItemDTO == null) {  //neu chua co sp trong map thi lay tt sp va sl sp =1
            cartItemDTO = new CartItemDTO();
            cartItemDTO.setProduct(product.get());
            cartItemDTO.setQuantity(numproduct);
            map.put(id, cartItemDTO);

            Set<Integer> set = map.keySet();
            for(Integer key : set) {

                totalOfCart += map.get(key).getQuantity();
                totalPrice += map.get(key).getProduct().getPrice()*map.get(key).getQuantity();
                totalPriceAfterApplyCoupon = totalPrice;

            }


        } else { // neu co sp trong map roi thi tang sl cua sp len
            cartItemDTO.setQuantity(cartItemDTO.getQuantity() + numproduct);

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


}
