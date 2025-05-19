
package javafinalproject;

import java.util.ArrayList;


public class Admin extends User {

     public Admin(String name, String email, String password,String description) {
        super(name, email, password,description);
    }
    public Admin(){}
    

    
    @Override
    public String toString() {
        return "Admin{" + super.toString() + "}";
    }

}


