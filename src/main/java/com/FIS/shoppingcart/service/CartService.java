package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Cart;

import java.util.List;

public interface CartService {
    //BillService
    boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);
    List<Cart> findAllCart();
    Cart findCart();
}
