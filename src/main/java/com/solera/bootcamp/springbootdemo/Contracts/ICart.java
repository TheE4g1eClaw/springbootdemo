package com.solera.bootcamp.springbootdemo.Contracts;

import java.util.List;
import java.util.Set;

import com.solera.bootcamp.springbootdemo.Models.Cart;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ProductDTO;

//Crud operations for Cart
public interface ICart {
    Cart createCart(Cart cart);
    Cart getCartById(Long id);
    List<Cart> getAllCarts();
    Cart updateCart(Long id, Cart cart);
    Boolean deleteCart(Long id);
    String addProductsToCart(Long cartId, ProductDTO productIds);
    Cart removeProductFromCart(Long cartId, Long productId);
    Set<Product> getProductsInCart(Long cartId);
}
