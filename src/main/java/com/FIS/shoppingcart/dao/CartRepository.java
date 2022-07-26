package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query("SELECT a FROM Cart a WHERE a.buyer.id = :buyerId")
//    select * from user inner join cart where user.id= cart.buyer_id
    List<Cart> findAllCartByBuyerId(int buyerId);


}
