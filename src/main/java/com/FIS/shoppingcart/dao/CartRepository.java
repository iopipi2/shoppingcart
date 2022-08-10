package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Account;
import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    //BillRepository
    @Query("SELECT a FROM Cart a WHERE a.buyer.id = :buyerId")
//    select * from user inner join cart where user.id= cart.buyer_id
    List<Cart> findAllCartByBuyerId(int buyerId);


     public List<Cart> findByBuyer(Account buyer);

     //Cua Hoang
     @Query("SELECT a FROM Cart a WHERE a.buyer.id= :buyerID AND a.status = :status ")
     List<Cart> findCartDone(int buyerID,String status);

    @Query("SELECT p FROM Cart p WHERE p.buyer.id= :buyerID AND p.status = :status")
    public List<Cart> findAll(String buyerID,String status ,Pageable pageable);



}
