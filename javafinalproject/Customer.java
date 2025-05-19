
package javafinalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Customer extends User{
       private List<Order> myOrders = new ArrayList<>();

    public   Customer  (String name,String email,String password,String description){
        super(name , email,password,description);
    }
    
    @Override
    public  boolean  equals(Object  obj ){
    if (this ==obj){
        return   true;
    }
    if (obj ==null || getClass() !=obj.getClass()){
        return   false;
    }
    Customer  otherCustomer  = (Customer )  obj;
  return this.getEmail().equalsIgnoreCase(otherCustomer.getEmail());
    }
    
 public void addOrder(Order o) {
        myOrders.add(o);
    }

    public List<Order> getMyOrders() {
        return myOrders;
    }

    public void loadOrders(Map<String, List<Order>> allOrders) {
        String idStr = String.valueOf(this.getId());
        if (allOrders.containsKey(idStr)) {
            myOrders = allOrders.get(idStr);
        }
    }
    @Override
    public String toString() {
        return "Customer{" + super.toString() + "}";
    }

}