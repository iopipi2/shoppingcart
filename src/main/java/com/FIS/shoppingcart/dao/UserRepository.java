package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Account,Integer> {

//    public List<Product> getProductPriceLowtoHigh(String sort);


    @Query("SELECT a FROM Account a where a.username = ?1")
    Account findUserByEmail(String email);

    @Query("SELECT a FROM Account a where a.username LIKE %?1%"
            + "OR a.username LIKE %?1%"
            + "OR a.name LIKE %?1%")
    public List<Account> findUser(String keyword);

}
