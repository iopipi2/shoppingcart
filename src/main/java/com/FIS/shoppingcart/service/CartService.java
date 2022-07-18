package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartService {
    //BillService
    boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);
    List<Cart> findAllCart();
    Cart findCart();

    List<Cart> findCartByBuyerId(int buyerId);
}
