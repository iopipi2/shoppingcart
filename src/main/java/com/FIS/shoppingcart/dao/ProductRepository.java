package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.category.type LIKE %?2%")
    public List<Product> findAll(String keyword, String category, Pageable pageable);

    List<Product> findProductByCategoryId(Integer categoryId);
}

