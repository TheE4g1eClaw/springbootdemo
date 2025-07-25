package com.solera.bootcamp.springbootdemo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;
import com.solera.bootcamp.springbootdemo.Service.ShoppingCartService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/API/v1/shoppingcart")
public class ShoppingCartController{
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart createdCart = shoppingCartService.createShoppingCart(shoppingCart);
        return ResponseEntity.ok(createdCart);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        ShoppingCart cart = shoppingCartService.getShoppingCartById(id);
        return (cart != null) ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> carts = shoppingCartService.getAllShoppingCarts();
        return ResponseEntity.ok(carts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        ShoppingCart updatedCart = shoppingCartService.updateShoppingCart(id, shoppingCart);
        return (updatedCart != null) ? ResponseEntity.ok(updatedCart) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        boolean deleted = shoppingCartService.deleteShoppingCart(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{shoppingCartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> addProductToShoppingCart(Long shoppingCartId, Long productId) {
        ShoppingCart updatedCart = shoppingCartService.addProductToShoppingCart(shoppingCartId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{shoppingCartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> removeProductFromShoppingCart(@PathVariable Long shoppingCartId, @PathVariable Long productId) {
        ShoppingCart updatedCart = shoppingCartService.removeProductFromShoppingCart(shoppingCartId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{shoppingCartId}/products")
    public ResponseEntity<List<Product>> getProductsInShoppingCart(@PathVariable Long shoppingCartId) {
        List<Product> products = shoppingCartService.getProductsInShoppingCart(shoppingCartId);
        return ResponseEntity.ok(products);
}
    
    
}
