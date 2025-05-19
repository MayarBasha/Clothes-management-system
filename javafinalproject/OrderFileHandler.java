/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class OrderFileHandler {
        private static final String FILE_PATH = "order.txt";

   public static void saveOrder(Order order) {
    try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
        out.println("----- Order -----");
        out.println("UserId: " + order.getUserID());  
        out.println("Date: " + order.getOrderDate());
        out.println("Name: " + order.getCustomerName());
        out.println("Email: " + order.getCustomerEmail());
        out.println("Phone: " + order.getCustomerPhone());
        out.println("Address: " + order.getCustomerAddress());
        out.println("Payment: " + order.getPayment());
        out.println("Status: " + order.getStatus());
        out.println("Total: " + order.getTotalBeforeDiscount());
        out.println("Discount: " + order.getDiscount());
        out.println("Final Total: " + order.getFinalTotal());

        out.println("Items:");
        for (CartItems item : order.getOrderItems()) {
            out.println("- " + item.getProduct().getName() + " x" + item.getQuantity() +
                        " = " + item.getTotalPrice());
        }
        out.println();
    } catch (IOException e) {
        System.out.println("Error saving order: " + e.getMessage());
    }
}

    public static List<String> readOrdersForUser(String userId) {
    List<String> userOrders = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        StringBuilder currentOrder = new StringBuilder();
        boolean isUserOrder = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("----- Order -----")) {
           
                if (isUserOrder && currentOrder.length() > 0) {
                    userOrders.add(currentOrder.toString());
                }
                currentOrder = new StringBuilder();
                isUserOrder = false;
            }

            currentOrder.append(line).append("\n");

            if (line.startsWith("UserId: ")) {
                String orderUserId = line.substring(8).trim();
                if (orderUserId.equals(userId)) {
                    isUserOrder = true;
                }
            }
        }
    
        if (isUserOrder && currentOrder.length() > 0) {
            userOrders.add(currentOrder.toString());
        }

    } catch (IOException e) {
        System.out.println("Error reading orders: " + e.getMessage());
    }
    return userOrders;
}

}