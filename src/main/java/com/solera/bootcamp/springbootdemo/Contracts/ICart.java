package com.solera.bootcamp.springbootdemo.Contracts;

import java.util.List;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.Cart;

//Crud operations for Cart
public interface ICart {
    Cart createCart(Cart cart);
    Cart getCartById(Long id);
    List<Cart> getAllCarts();
    Cart updateCart(Long id, Cart cart);
    Boolean deleteCart(Long id);
    Cart addProductToCart(Long cartId, Long productId);
    Cart removeProductFromCart(Long cartId, Long productId);
    List<Product> getProductsInCart(Long cartId);
}
