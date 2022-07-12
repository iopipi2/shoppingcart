package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {
    //CategoryRepository
}
