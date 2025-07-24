package com.solera.bootcamp.springbootdemo.Repository;

import org.springframework.data.repository.CrudRepository;

import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    // This interface will automatically provide CRUD operations for DesiredListByMonth entities
    // No additional methods are needed unless specific queries are required
    
}
