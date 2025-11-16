package com.example.demo.services;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(Product p) {
        productRepository.save(p);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Product p, int id) {
        p.setPid(id);
        productRepository.save(p);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product getProductByName(String name) {
        return productRepository.findByPnameIgnoreCase(name);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByPnameContainingIgnoreCase(keyword);
    }

    public void updateproduct(Product product, int id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            Product old = optional.get();

            // Update fields
            old.setPname(product.getPname());
            old.setPprice(product.getPprice());
            old.setPdescription(product.getPdescription());

            // Save back to DB
            productRepository.save(old);
        }
    }
}
