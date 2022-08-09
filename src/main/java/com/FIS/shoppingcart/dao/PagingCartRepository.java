package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingCartRepository extends PagingAndSortingRepository<Cart, Long> {
}
