package com.FIS.shoppingcart.service.impl;

import com.FIS.shoppingcart.dao.CartRepository;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("cartService")
public class CartServiceImpl implements CartService {

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

//    @Override
//    public Cart findCart() {
//        // TODO Auto-generated method stub
//        return ((UserModel) httpSession.getAttribute("userModel")).getCart();
//    }

}
