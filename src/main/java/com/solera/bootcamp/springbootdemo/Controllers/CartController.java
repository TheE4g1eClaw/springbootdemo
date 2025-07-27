package com.solera.bootcamp.springbootdemo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.Cart;
import com.solera.bootcamp.springbootdemo.Service.CartService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.solera.bootcamp.springbootdemo.Models.ProductDTO;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/API/v1/cart")
@PreAuthorize("hasRole('ADMIN')")
public class CartController{
    private final CartService cartService;
    public HashMap<String, Long> cartMap = new HashMap<>();

    public CartController(CartService cartService) {
        this.cartService = cartService;
        /* Get the cart ID for each user */
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/users.csv"))) {
            String line = br.readLine(); // Skip header line
            //System.out.println("\n\n\n\n\nLoading user carts from CSV file...");
            while ((line = br.readLine()) != null) {
                //System.out.println("Processing line: " + line);
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[0];
                    Long cartId = Long.parseLong(parts[3]);
                    cartMap.put(username, cartId);
                    //System.out.println("User: " + username + " has cart ID: " + cartId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Only ADMIN can create a cart */
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    /* ADMIN and USER can view a cart */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id, Authentication authentication) {
        if (isUserCartOwner(id, authentication)) {
            Cart cart = cartService.getCartById(id);
            return (cart != null) ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build(); // Forbidden if not the owner
    }

    /* Only ADMIN can view all carts */
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    /* Only ADMIN can completely update a cart */
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(id, cart);
        return (updatedCart != null) ? ResponseEntity.ok(updatedCart) : ResponseEntity.notFound().build();
    }

    /* Only ADMIN can delete a cart */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        boolean deleted = cartService.deleteCart(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /* ADMIN and USER can add products to a cart */
    @PutMapping("/{cartId}/products")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> addProductsToCart(@PathVariable Long cartId, Authentication authentication, @RequestBody ProductDTO productIds) {
        if (isUserCartOwner(cartId, authentication)) {
            String response = cartService.addProductsToCart(cartId, productIds);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(403).build(); // Forbidden if not the owner
    }

    /* ADMIN and USER can remove products from a cart */
    @DeleteMapping("/{cartId}/product/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId, Authentication authentication) {
        if (isUserCartOwner(cartId, authentication)) {
            Cart updatedCart = cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok(updatedCart);
        }
        return ResponseEntity.status(403).build(); // Forbidden if not the owner
    }

    /* ADMIN and USER can view products in a cart */
    @GetMapping("/{cartId}/products")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Set<Product>> getProductsInCart(@PathVariable Long cartId, Authentication authentication) {
        if (isUserCartOwner(cartId, authentication)) {
            Set<Product> products = cartService.getProductsInCart(cartId);
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.status(403).build(); // Forbidden if not the owner
    }
    
    private boolean isUserCartOwner(Long cartId, Authentication authentication) {
        Long userCartId = cartMap.get(authentication.getName());
        return userCartId != null && userCartId.equals(cartId);
    }
}
