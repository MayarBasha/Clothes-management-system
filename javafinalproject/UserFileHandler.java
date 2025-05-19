/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafinalproject.User;


public class UserFileHandler {
     private String filePath;
    private List<User> users;
     public UserFileHandler(String filePath) {
        this.filePath = filePath;
        this.users = loadUsers();
    }
    public void saveUsers(List<User> users) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath,false));
            for (User u : users) {
                writer.println(u.toString());
            }
            writer.close();
        } catch (Exception e) {   e.printStackTrace(); }
    }
    public List<User> loadUsers() {
         List<User> usersList = new ArrayList<>();
    try  {
        Scanner input = new Scanner(new File(filePath));
        
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            String name = parts[0];
            String email = parts[1];
            String password = parts[2];
            String id = parts[3];
            String description = parts[4];
            User u = new User(name, email, password, id,description);
            usersList.add(u); 
        }
        input.close();
    } catch (Exception e) {    e.printStackTrace();   }
      return usersList;
    }
     public  void addUser(User user) {
        users.add(user);
        saveUsers(users);
    }
     
     public void editUser( String newUsername, String newEmail, String newPassword,String id, String newDescription) {
    
    for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getId().equals(id)) {
            User updatedUser = new User(newUsername, newEmail, newPassword, id, newDescription);
            users.set(i, updatedUser);
            break;
        }
    }
    saveUsers(users);
}
       public void removeUser(String id) {
        users.removeIf(u -> u.getId().equals(id));
        saveUsers(users);
    }
       public List<User> showUsers() {
        return users;
       }
       public List<User> searchOnUserByName(String username) {
        List<User> matchedUsers = new ArrayList<>();
        for (User u : users) {
        if (u.getName().equalsIgnoreCase(username)) {
            matchedUsers.add(u);
        }
    }
    return  matchedUsers;
}
       public List<User> searchOnUserByEmail(String email) {
         List<User> matchedUsers = new ArrayList<>();
    for (User u : users) {
        if (u.getEmail().equalsIgnoreCase(email)) {
            matchedUsers.add(u);
        }
    }
    return matchedUsers;
}
    public List<User> searchOnUserByPassword(String password) {
         List<User> matchedUsers = new ArrayList<>();
    for (User u : users) {
        if (u.getPassword().equals(password)) {
            matchedUsers.add(u);
        }
    }
    return matchedUsers;
}
  public String checkSignUp(String username, String email, String password) {
    for (User u : users) {
        if (u.getName().equalsIgnoreCase(username)) {
            return "UserName Already Used";
        }
        if (u.getEmail().equalsIgnoreCase(email)) {
            return "Email Already Used";
        }
    }
    return "Ok";
}
   
         
         
         
   
     public User checkSignIn(String email,String password){
         for (User u : users) {
        if ( (u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equals(password)) ) {
           return u;
        }
    }
         return null;  
     }
       
       

}