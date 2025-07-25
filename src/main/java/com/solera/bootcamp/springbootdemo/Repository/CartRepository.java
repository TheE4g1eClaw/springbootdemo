package com.solera.bootcamp.springbootdemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.solera.bootcamp.springbootdemo.Models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
