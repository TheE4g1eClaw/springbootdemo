package com.solera.bootcamp.springbootdemo.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solera.bootcamp.springbootdemo.Models.Product;



@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    // Define custom query methods if needed
    @Query("SELECT p FROM Product p WHERE p.name = ?1 AND p.price = ?2")
    Product findByNameAndPrice(String name, Double price);
    

    
} 