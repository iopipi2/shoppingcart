package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
