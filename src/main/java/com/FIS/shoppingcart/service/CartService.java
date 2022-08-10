package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.CartLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Page<Cart> listAll(int id,int pageNumber);

    public Page<Cart> listAllByAdmin(int pageNumber);

    Optional<Cart> findCartById(int id);


}
