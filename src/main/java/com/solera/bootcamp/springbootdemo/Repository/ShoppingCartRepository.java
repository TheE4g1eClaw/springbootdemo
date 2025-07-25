package com.solera.bootcamp.springbootdemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    
}
