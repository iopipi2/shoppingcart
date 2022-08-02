package com.FIS.shoppingcart.service.impl;

import com.FIS.shoppingcart.dao.CartRepository;
import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.entities.UserModel;
import com.FIS.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CartService cartService;
    @Qualifier("cartRepository")
    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean saveCart(Cart cart) {
        // TODO Auto-generated method stub
        cartRepository.saveAndFlush(cart);
        return true;
    }

    @Override
    public boolean updateCart(Cart cart) {
        // TODO Auto-generated method stub
        cartRepository.saveAndFlush(cart);
        return true;
    }

    @Override
    public List<Cart> findAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findCartByBuyerId(int buyerId) {
        return cartRepository.findAllCartByBuyerId(buyerId);
    }

    @Override
    public List<Cart> findByBuyer(Account buyer) {
        return cartRepository.findByBuyer(buyer);
    }



    @Override
    public Cart findCart() {
        // TODO Auto-generated method stub
        return ((UserModel) httpSession.getAttribute("userModel")).getCart();
//        return null;
    }

    //Cua Hoang -----------------------------------------------------
    @Override
    public List<Cart> findCartDone(int buyerId,String status) {
        return cartRepository.findCartDone(buyerId,status);
    }



}
