package com.example.demo.repositories;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByUserOrderByOrderDateDesc(User user);
}
