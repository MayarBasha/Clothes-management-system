
package javafinalproject;

import java.util.UUID;

public class User {
       private String name,email,password,description,id;
    public User(){}
    public User(String name,String email,String password,String description){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.description=description;
    }
    public User(String name,String email,String password,String id,String description){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.description=description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
         if (password.length() >= 6) {
        this.password = password;
    } else {
        throw new IllegalArgumentException("Password must be at least 6 characters.");
    }
    }
    
    
     @Override
    public String toString() {
        return  name + "," + email + "," + password + "," + id + "," + description;
    }
    

}