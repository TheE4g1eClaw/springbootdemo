package com.solera.bootcamp.springbootdemo.Controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solera.bootcamp.springbootdemo.Models.Cart;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Service.ProductService;

@RestController
@RequestMapping("/API/v1/product")
/* Only ADMIN can access edit products */
@PreAuthorize("hasRole('ADMIN')")
public class ProductController{
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return (productService.getProductById(id) != null) ?
                ResponseEntity.ok(productService.getProductById(id)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return (updatedProduct != null) ?
                ResponseEntity.ok(updatedProduct) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}/carts")
    public ResponseEntity<Set<Cart>> getInWhichCart(@PathVariable Long productId) {
        Set<Cart> carts = productService.getInWhichCart(productId);
        return ResponseEntity.ok(carts);
    }
}
