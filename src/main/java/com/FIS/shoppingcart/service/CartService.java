package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Cart;

import java.util.Collection;
import java.util.List;

public interface CartService {

    List<Cart> findAllCart();
    Cart findCart();

    List<Cart> findCartByBuyerId(int buyerId);


    List<Cart> findCartDone(String status);
}
