
package javafinalproject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public class Order {
    private String orderID;
    private String userID;
    private String customerName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;

    private LocalDateTime orderDate;
    private double totalBeforeDiscount;
    private double discount;
    private double finalTotal;
    private String payment;
    private String status;

    private ArrayList<CartItems> orderItems;


    public Order() {
        this.orderItems = new ArrayList<>();
    }

    
    public Order(Cart cart) {
        this.orderID = generateOrderID();
       

        this.orderItems = new ArrayList<>();
        for (CartItems item : cart.getItems()) {
            Product p = item.getProduct();

            Product productCopy = new Product(
                p.getProductID(),
                p.getName(),
                p.getPrice(),
                p.getQuantity(),
                p.getSupplierName()
            );
            productCopy.setImagePath(p.getImagePath());

            CartItems copyItem = new CartItems(productCopy);
            for (int i = 1; i < item.getQuantity(); i++) {
                copyItem.increaseQuantity();
            }
            this.orderItems.add(copyItem);
        }

        this.totalBeforeDiscount = cart.getTotal();
        this.discount = cart.getDiscount();
        this.finalTotal = cart.getTotalAfterDiscount();
        this.orderDate = LocalDateTime.now();

        this.payment = "";
        this.status = "Pending";
    }

  
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }


    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

  
    public ArrayList<CartItems> getOrderItems() {
        return orderItems;
    }

  
    public String generateOrderID() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

  
    public ArrayList<String> getProductNames() {
        ArrayList<String> names = new ArrayList<>();
        for (CartItems item : orderItems) {
            names.add(item.getProduct().getName());
        }
        return names;
    }

    public ArrayList<Integer> getQuantities() {
        ArrayList<Integer> quantities = new ArrayList<>();
        for (CartItems item : orderItems) {
            quantities.add(item.getQuantity());
        }
        return quantities;
    }

    public ArrayList<Double> getItemTotals() {
        ArrayList<Double> totals = new ArrayList<>();
        for (CartItems item : orderItems) {
            totals.add(item.getTotalPrice());
        }
        return totals;
    }

}