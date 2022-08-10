package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PagingCartRepository extends PagingAndSortingRepository<Cart, Long> {

    @Query("SELECT p FROM Cart p WHERE p.buyer.id= :buyerID")
    public Page<Cart> findAll(int buyerID, Pageable pageable);

    @Query("SELECT p FROM Cart p ")
    public Page<Cart> findAllByAdmin( Pageable pageable);
}