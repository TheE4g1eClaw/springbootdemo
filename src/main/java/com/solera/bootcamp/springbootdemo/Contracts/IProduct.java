package com.solera.bootcamp.springbootdemo.Contracts;

import java.util.List;
import java.util.Set;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.Cart;

// CRUD operations for Product
public interface IProduct {
    //CRUD operations for Product
    Product createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Set<Cart> getInWhichCart(Long productId);
}
