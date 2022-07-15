package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

//    public List<Product> getProductPriceLowtoHigh(String sort);


//    User findUserByEmail(String mail);

}
