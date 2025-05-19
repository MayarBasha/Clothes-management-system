
package javafinalproject;

import java.time.LocalTime;

public class Cashier extends User {
    public Cashier (){}
      public Cashier(String name, String email, String password,String description) {
        super(name, email, password,description);
    }
    public String getCashier() {
     
      LocalTime now = LocalTime.now();

        if (now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(16, 0))) {
            return "Mohamed Reda";
        } else if (now.isAfter(LocalTime.of(16, 0)) && now.isBefore(LocalTime.MIDNIGHT)) {
            return "Mona Ahmed";
        } else if ((now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.of(8, 0))) 
               || now.equals(LocalTime.MIDNIGHT)) {
            return "Youssef Rami";
    }

        return null;
}

}