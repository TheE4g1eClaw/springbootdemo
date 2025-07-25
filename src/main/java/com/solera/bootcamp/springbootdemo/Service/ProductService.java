package com.solera.bootcamp.springbootdemo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.bootcamp.springbootdemo.Contracts.IProduct;
import com.solera.bootcamp.springbootdemo.Models.Product;
import com.solera.bootcamp.springbootdemo.Models.ShoppingCart;
import com.solera.bootcamp.springbootdemo.Repository.ProductRepository;

@Service
public class ProductService implements IProduct{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(null); // Ensure the ID is null for creation
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        // Check if the product exists
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        product.setId(id); // Ensure the ID is set for the update
        return productRepository.save(product); 
    }

    @Override
    public void deleteProduct(Long id) {
        // Check if the product exists
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ShoppingCart> getInWhichShoppingCart(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
            
        }
        return productRepository.findById(productId).get()
                .getShoppingCarts();
    }
    
}
