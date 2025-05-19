/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import java.util.ArrayList;
import java.util.UUID;


public class Product  {
   private String productID;
    private String name;
    private double price;
    private int quantity;
    private String supplierName;
    private String imagePath;

    public Product(String name, double price, int quantity, String supplierName) {
        this.productID = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
    }
    public Product(String productID,String name, double price, int quantity, String supplierName) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getSupplierName() { return supplierName; }
    public String getImagePath() { return imagePath; }
   

    public void setProductID(String productID) { this.productID = productID; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
     public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return name + "," + price + "," + quantity + "," + productID + "," + supplierName+"," + imagePath;
    }

}