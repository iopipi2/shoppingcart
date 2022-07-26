package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account,Integer> {

}
