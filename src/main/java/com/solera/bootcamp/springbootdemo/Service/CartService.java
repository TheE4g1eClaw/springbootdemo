package com.solera.bootcamp.springbootdemo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.bootcamp.springbootdemo.Contracts.ICart;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.Cart;
import com.solera.bootcamp.springbootdemo.Repository.CartRepository;
import com.solera.bootcamp.springbootdemo.Service.ProductService;
import java.util.ArrayList;

@Service
public class CartService implements ICart {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart createCart(Cart cart) {
        try {
            cart.setId(null); // Ensure the ID is null for creation
            // Don't initialize products list - let JPA handle it
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("----------------------Failed to create cart: " + e.getMessage(), e);
        }
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        // Check if the cart exists
        if (!cartRepository.existsById(id)) {
            throw new RuntimeException("Cart not found with id: " + id);
        }
        cart.setId(id); // Ensure the ID is set for the update
        return cartRepository.save(cart);
    }

    @Override
    public Boolean deleteCart(Long id) {
        // Check if the cart exists
        if (!cartRepository.existsById(id)) {
            throw new RuntimeException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public Cart addProductToCart(Long cartId, Long productId) {
        // Check if the cart exists
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long cartId, Long productId) {
        // Check if the cart exists
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }

    @Override
    public List<Product> getProductsInCart(Long cartId) {
        // Check if the cart exists
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        return cart.getProducts();
    }
    
}
