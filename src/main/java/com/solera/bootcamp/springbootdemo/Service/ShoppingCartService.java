package com.solera.bootcamp.springbootdemo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.bootcamp.springbootdemo.Contracts.IShoppingCart;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;
import com.solera.bootcamp.springbootdemo.Repository.ShoppingCartRepository;
import com.solera.bootcamp.springbootdemo.Service.ProductService;
import java.util.ArrayList;

@Service
public class ShoppingCartService implements IShoppingCart {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(null); // Ensure the ID is null for creation
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found with id: " + id));
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return (List<ShoppingCart>) shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart updateShoppingCart(Long id, ShoppingCart shoppingCart) {
        // Check if the shopping cart exists
        if (!shoppingCartRepository.existsById(id)) {
            throw new RuntimeException("ShoppingCart not found with id: " + id);
        }
        shoppingCart.setId(id); // Ensure the ID is set for the update
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Boolean deleteShoppingCart(Long id) {
        // Check if the shopping cart exists
        if (!shoppingCartRepository.existsById(id)) {
            throw new RuntimeException("ShoppingCart not found with id: " + id);
        }
        shoppingCartRepository.deleteById(id);
        return true;
    }

    @Override
    public ShoppingCart addProductToShoppingCart(Long shoppingCartId, Long productId) {
        // Check if the shopping cart exists
        ShoppingCart shoppingCart = getShoppingCartById(shoppingCartId);
        if (shoppingCart == null) {
            throw new RuntimeException("ShoppingCart not found with id: " + shoppingCartId);
        }
        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        shoppingCart.getProducts().add(product);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeProductFromShoppingCart(Long shoppingCartId, Long productId) {
        // Check if the shopping cart exists
        ShoppingCart shoppingCart = getShoppingCartById(shoppingCartId);
        if (shoppingCart == null) {
            throw new RuntimeException("ShoppingCart not found with id: " + shoppingCartId);
        }
        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        shoppingCart.getProducts().remove(product);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<Product> getProductsInShoppingCart(Long shoppingCartId) {
        // Check if the shopping cart exists
        ShoppingCart shoppingCart = getShoppingCartById(shoppingCartId);
        if (shoppingCart == null) {
            throw new RuntimeException("ShoppingCart not found with id: " + shoppingCartId);
        }
        return shoppingCart.getProducts();
    }
    
}
