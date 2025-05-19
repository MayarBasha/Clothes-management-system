
package javafinalproject;


public class CheckTextFields {
    public String checkUserField(String userName,String email,String password){
         if( (userName.isEmpty())||(email.isEmpty()) || (password.isEmpty())){ 
               return "Please Fill all Fields ";
            }
         else if(!userName.matches("^[a-zA-Z0-9]{3,}$")){
             return "UserName must be at least 3 characters and not contain any symbol";
    }else if(!email.matches("^[^@\\s,]+@[^@\\s,]+\\.[^@\\s,]+$")){
             return "Email must be at least 3 characters and not contain any symbol Except @";
             }else if(!password.matches("^[a-zA-Z0-9]{3,}$")){
             return "Password must be at least 3 characters and not contain any symbol";
             }   
         return "Ok";
         
    }
}

