package com.FIS.shoppingcart.service.impl;

import com.FIS.shoppingcart.dao.CartRepository;
import com.FIS.shoppingcart.entities.Cart;
import com.FIS.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


@Service("cartService")
public class CartServiceImpl implements CartService {

}
