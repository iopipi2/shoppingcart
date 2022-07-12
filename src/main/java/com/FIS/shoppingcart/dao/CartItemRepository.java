package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    //CartItemRepository
}
