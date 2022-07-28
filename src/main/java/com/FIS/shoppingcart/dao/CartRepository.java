package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query("SELECT a FROM Cart a WHERE a.buyer.id = :buyerId")
    List<Cart> findAllCartByBuyerId(int buyerId);

    @Query("SELECT a FROM Cart a WHERE a.status = :status ")
    List<Cart> findCartDone(String status);


}
