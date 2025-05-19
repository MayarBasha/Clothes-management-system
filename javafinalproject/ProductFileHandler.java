/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProductFileHandler {
  private String filePath;
    private List<Product> products;
    
    public ProductFileHandler(String filePath) {
        this.filePath = filePath;
        this.products = loadProducts();
    }

    public void saveProducts(List<Product> products) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            for (Product p : products) {
                writer.println(p.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> loadProducts() {
        List<Product> productsList = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(filePath));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split(","); 
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                String id = parts[3];
                String supplier = parts[4];
                String imagePath = parts.length > 5 ? parts[5] : "";
                Product p = new Product(id, name, price, quantity, supplier);
                p.setImagePath(imagePath);
                productsList.add(p);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public void addProduct(Product p) {
        boolean alreadyExists = false;
        for (Product prod : products) {
            if (prod.getProductID().equals(p.getProductID())) {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            products.add(p);
            saveProducts(products);
        } else {
           System.out.println("⚠️ The product already exists and will not be added again: " + p.getProductID());
        }
    }

    public void editProduct(String id, String newName, double newPrice, int newQuantity, String newSupplier) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(id) ) {
                Product updated = new Product(id, newName, newPrice, newQuantity, newSupplier);
                products.set(i, updated);
                break;
            }
        }
        saveProducts(products);
    }

    public void removeProduct(String id) {
        products.removeIf(p -> p.getProductID().equals(id));
        saveProducts(products);
    }

    public List<Product> showProducts() {
        loadProducts();
        return products;
    }
    public  String getSupplierNameByProductName(String productName) {
    List<Product> products = loadProducts(); 
    for (Product p : products) {
        if (p.getName().equalsIgnoreCase(productName)) {
            return p.getSupplierName();
        }
    }
    return "Unknown";
}
    public List<Product> searchProductByName(String name) {
    List<Product> matchedProducts = new ArrayList<>();
    for (Product p : products) {
        if (p.getName().equalsIgnoreCase(name)) {
            matchedProducts.add(p);
        }
    }
    return matchedProducts;
}


}