package com.solera.bootcamp.springbootdemo.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.solera.bootcamp.springbootdemo.Contracts.ICart;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ProductDTO;
import com.solera.bootcamp.springbootdemo.Models.Cart;
import com.solera.bootcamp.springbootdemo.Repository.CartRepository;

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
            cart.setId(null); // Ensure the ID is null for creation
            cart.setProducts(null); // Clear products to avoid issues with null references
            return cartRepository.save(cart);
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
    public String addProductsToCart(Long cartId, ProductDTO productIds) {
        // Check if the cart exists
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        // Get products
        Set<Product> products = new HashSet<>();
        for (Long productId : productIds.getId()) {
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + productId);
            }
            products.add(product);
        }
        cart.getProducts().addAll(products);
        cartRepository.save(cart);
        String response = products.size() +
                " products added to cart." +
                "The total price of the cart is: " +
                cart.getTotalPrice();
        return response;
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
    public Set<Product> getProductsInCart(Long cartId) {
        // Check if the cart exists
        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        return cart.getProducts();
    }
    
}
