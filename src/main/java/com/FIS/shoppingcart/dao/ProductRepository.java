package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    //ProductRepository
}
