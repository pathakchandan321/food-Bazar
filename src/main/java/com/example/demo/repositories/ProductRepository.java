package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByPnameIgnoreCase(String name);

    List<Product> findByPnameContainingIgnoreCase(String keyword);
}
