package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Cart;

public interface CartService {
    //BillService
    boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);

//    Cart findCart();
}
