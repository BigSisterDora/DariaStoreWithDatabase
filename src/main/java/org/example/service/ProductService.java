package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.example.tools.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Input input;

    @Autowired
    public ProductService(ProductRepository productRepository, Input input) {
        this.productRepository = productRepository;
        this.input = input;
    }

    public void addProduct(Product product) {
        try {
            productRepository.save(product);
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void updateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println("Current product details:");
        System.out.println(product);

        System.out.println("\nEnter new details (press Enter to keep current values):");

        System.out.println("New name (current: " + product.getName() + "):");
        String newName = input.nextLine();
        if (!newName.isEmpty()) {
            product.setName(newName);
        }

        System.out.println("New price (current: " + product.getPrice() + "):");
        String newPrice = input.nextLine();
        if (!newPrice.isEmpty()) {
            product.setPrice(Double.parseDouble(newPrice));
        }

        System.out.println("New quantity (current: " + product.getQuantity() + "):");
        String newQuantity = input.nextLine();
        if (!newQuantity.isEmpty()) {
            product.setQuantity(Integer.parseInt(newQuantity));
        }

        productRepository.save(product);
        System.out.println("Product updated successfully!");
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            System.out.println("Product deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    public boolean hasEnoughStock(Product product, int quantity) {
        return product.getQuantity() >= quantity;
    }

    public void updateStock(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}