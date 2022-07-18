package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartLineRepository extends JpaRepository<CartLine,Integer> {

    @Query("SELECT a FROM CartLine a where a.cart.id = :cartId")
    List<CartLine> findCartLineByCartId(int cartId);

    CartLine findCartLineByCartIdAndProductId(int cartId, int id);


}

