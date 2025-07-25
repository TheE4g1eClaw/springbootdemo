package com.solera.bootcamp.springbootdemo.Contracts;

import java.util.List;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;

// CRUD operations for Product
public interface IProduct {
    //CRUD operations for Product
    Product createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<ShoppingCart> getInWhichShoppingCart(Long productId);
}
