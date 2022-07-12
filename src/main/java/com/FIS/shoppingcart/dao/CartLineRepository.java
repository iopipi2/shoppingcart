package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartLineRepository extends JpaRepository<CartLine,Integer> {

    List<CartLine> findCartLineByCartId(int cartId);

    CartLine findCartLineByCartIdAndProductId(int cartId, int id);


}

