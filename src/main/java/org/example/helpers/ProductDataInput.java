package org.example.helpers;

import org.example.model.Product;
import org.example.tools.Input;

public class ProductDataInput {
    public Product createProduct(Input input) {
        Product product = new Product();
        System.out.println("Enter product name:");
        product.setName(input.nextLine());
        System.out.println("Enter product price:");
        product.setPrice(Double.parseDouble(input.nextLine()));
        System.out.println("Enter product quantity:");
        product.setQuantity(Integer.parseInt(input.nextLine()));
        return product;
    }
}