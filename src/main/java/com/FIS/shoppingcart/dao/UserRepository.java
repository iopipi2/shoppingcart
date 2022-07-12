package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    //UserRepository
}
