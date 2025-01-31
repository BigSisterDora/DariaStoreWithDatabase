package org.example.interfaces;

import org.example.model.Product;

public interface ProductDataInputInterface {
    Product createProduct();
    void addProduct();
    void listProducts();
    void editProduct();
    void deleteProduct();
}