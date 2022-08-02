package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.CartLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CartService {
    //BillService
    boolean saveCart(Cart cart);

    boolean updateCart(Cart cart);
    List<Cart> findAllCart();
    Cart findCart();

    List<Cart> findCartByBuyerId(int buyerId);

    public List<Cart> findByBuyer(Account buyer);

    //Cua Hoang
    List<Cart> findCartDone(int buyerId,String status);
}
