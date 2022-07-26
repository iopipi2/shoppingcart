package com.FIS.shoppingcart.service.impl;

import com.FIS.shoppingcart.dao.CartRepository;
import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.model.UserDTO;
import com.FIS.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Cart findCart() {
        // TODO Auto-generated method stub
        return ((UserDTO) httpSession.getAttribute("userModel")).getCart();
//        return null;
    }
}
