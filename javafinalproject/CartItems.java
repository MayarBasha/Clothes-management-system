/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

public class CartItems {
     private Product product;
    private int quantity;

    public CartItems(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public CartItems(Product product , int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if (quantity > 1) quantity--;
    }
    public Product getProduct(){
    return product;
    }

    public void setQuantity(int quantity) {
         if (quantity < 1) {
        this.quantity = 1; 
    } else {
        this.quantity = quantity;
    }
    }

    public int getQuantity() {
        return quantity;
    }
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}