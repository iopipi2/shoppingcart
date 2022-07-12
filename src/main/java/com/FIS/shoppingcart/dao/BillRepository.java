package com.FIS.shoppingcart.dao;

import com.FIS.shoppingcart.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    //BillRepository
}
