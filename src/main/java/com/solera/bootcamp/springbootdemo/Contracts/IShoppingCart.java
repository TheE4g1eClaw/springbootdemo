package com.solera.bootcamp.springbootdemo.Contracts;

import java.util.List;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;

//Crud operations for ShoppingCart
public interface IShoppingCart {
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
    ShoppingCart getShoppingCartById(Long id);
    List<ShoppingCart> getAllShoppingCarts();
    ShoppingCart updateShoppingCart(Long id, ShoppingCart shoppingCart);
    Boolean deleteShoppingCart(Long id);
    ShoppingCart addProductToShoppingCart(Long shoppingCartId, Long productId);
    ShoppingCart removeProductFromShoppingCart(Long shoppingCartId, Long productId);
    List<Product> getProductsInShoppingCart(Long shoppingCartId);
}
