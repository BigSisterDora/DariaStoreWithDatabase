package org.example.interfaces;

import org.example.model.Product;
import java.util.List;

public interface ProductServiceInterface {
    void addProduct(Product product);
    void updateProduct(Long id);
    List<Product> getAllProducts();
    Product findById(Long id);
    void deleteProduct(Long id);
    boolean hasEnoughStock(Product product, int quantity);
    void updateStock(Product product, int quantity);
}