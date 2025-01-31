package org.example.helpers;

import org.example.model.Product;
import org.example.service.ProductService;
import org.example.tools.Input;
import org.springframework.stereotype.Component;

@Component
public class ProductDataInput {
    private final Input input;
    private final ProductService productService;

    public ProductDataInput(Input input, ProductService productService) {
        this.input = input;
        this.productService = productService;
    }

    public Product createProduct() {
        Product product = new Product();
        System.out.println("Enter product name:");
        product.setName(input.nextLine());
        System.out.println("Enter product price:");
        product.setPrice(Double.parseDouble(input.nextLine()));
        System.out.println("Enter product quantity:");
        product.setQuantity(Integer.parseInt(input.nextLine()));
        return product;
    }

    public void addProduct() {
        Product product = createProduct();
        productService.addProduct(product);
    }

    public void listProducts() {
        System.out.println("\n===== Product List =====");
        productService.getAllProducts().forEach(product ->
                System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d%n",
                        product.getId(), product.getName(),
                        product.getPrice(), product.getQuantity())
        );
    }

    public void editProduct() {
        System.out.println("Enter product ID to edit:");
        Long id = Long.parseLong(input.nextLine());
        productService.updateProduct(id);
    }

    public void deleteProduct() {
        System.out.println("Enter product ID to delete:");
        Long id = Long.parseLong(input.nextLine());
        productService.deleteProduct(id);
    }
}