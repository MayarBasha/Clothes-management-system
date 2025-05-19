/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import java.util.ArrayList;


public class Cart {
      private String userID;
    private ArrayList<CartItems> cartProducts;
User user =new User(); 
    public Cart(ArrayList<CartItems> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Cart() {
        this.userID = user.getId();
        this.cartProducts = new ArrayList<>();
    }
    
   public void addItem(CartItems item) {
    cartProducts.add(item); 
}


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
 
  
    public void addProduct(Product product) {
        for (CartItems item : cartProducts) {
            if (item.getProduct().getProductID().equals(product.getProductID())) {
                item.increaseQuantity();
                return;
            }
        }
        cartProducts.add(new CartItems(product));
    }

   
    public void removeProduct(Product product) {
        cartProducts.removeIf(item -> item.getProduct().getProductID().equals(product.getProductID()));
    }

   
    public double getTotal() {
        double total = 0;
        
        for (CartItems item : cartProducts) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        
        return total;
    }

    public double getDiscount() {
        return getTotal() > 2000 ? getTotal() * 0.1 : 0;
    }

    // الإجمالي بعد الخصم
    public double getTotalAfterDiscount() {
        return getTotal() - getDiscount();
    }

  
    public ArrayList<CartItems> getItems() {
        return cartProducts;
    }

     public void setItems(ArrayList<CartItems> items) {
        this.cartProducts = items;
    }

   
    public void clear() {
        cartProducts.clear();
    }

  
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(userID).append("\n");  
        for (CartItems item : cartProducts) {
            Product p = item.getProduct();
            
            sb.append(p.getProductID()).append(",").append(item.getQuantity()).append("\n");
        }
        sb.append("END_CART\n"); 
        return sb.toString();
    }

    boolean contains(Product product) {
        return true;
    }


}