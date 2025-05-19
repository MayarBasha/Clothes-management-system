/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author soft zone
 */
public class SignInView {
  HBox root;
   UserFileHandler userFileHandler = new UserFileHandler("users.txt");
   SignUpView signUpView = new SignUpView();

   AdminDashBoard admin = new AdminDashBoard(this);
   CustomerDashBoard customer = new CustomerDashBoard();
   SignUpView signUp = new SignUpView();
   
         public HBox getSignInView(){
         FlowPane content = new FlowPane();
        content.setOrientation(Orientation.VERTICAL);
        content.setAlignment(Pos.CENTER);
    
        Text txtLogo = new Text("FOR YOU");
txtLogo.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 50));

        VBox rootImg = new VBox(txtLogo);
        rootImg.setAlignment(Pos.CENTER);
        rootImg.setStyle("-fx-background-color: #659EC7;");
        
        Text txtWelcome = new Text("This For You");
        txtWelcome.setFont(Font.font("Times New Roman",20));
        
        Label lblEmail = new Label("Email");
        Label lblPassword = new Label("Password");
        
        TextField txtEmail =  Utils.createTextField("", 200);
        PasswordField password = Utils.createPasswordField("", 200);
       
        Button btnSignIn = Utils.createButton("Sign In","-fx-background-radius: 30; -fx-font-size: 14px; -fx-padding: 10 ; -fx-background-color: #659EC7; -fx-text-fill: white;" ,100);
        Label lblSignUp = Utils.createLabel("Sign Up"," -fx-font-size: 14px; -fx-text-fill: #659EC7;");
        
        lblSignUp.setUnderline(true);
        Label lblAlert = Utils.createLabel("", "-fx-font-size: 13px");
        Label lblChoose = Utils.createLabel("If you don't have an account:", "-fx-font-size: 14px");
        HBox sign = new HBox(10,lblChoose,lblSignUp);
        
        btnSignIn.setOnAction(e->{
        if( (txtEmail.getText().isEmpty()) || (password.getText().isEmpty())){ lblAlert.setText("Please Fill all Fields ");
        }
        else {
       User matchedUser = userFileHandler.checkSignIn(txtEmail.getText(), password.getText());
        if (matchedUser != null) {
            lblAlert.setText("Login successful!");  
            if(matchedUser.getDescription().equalsIgnoreCase("admin")){
               Scene adminScene= new Scene(admin.getAdminView(matchedUser.getName()),900,600);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                stage.setScene(adminScene); 
                stage.show();
            }else if(matchedUser.getDescription().equalsIgnoreCase("customer")){
                Scene customerScene= new Scene(customer.getCustomerView(matchedUser.getName(),matchedUser.getId()),900,600);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(customerScene); 
                  stage.show();
            
            }
        } else {
            lblAlert.setText("can't Find User");
        }
    }
        });
        
        lblSignUp.setOnMouseClicked(e->{
            Scene signUpScene = new Scene(signUpView.getSignUpScene(),900,600);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(signUpScene); 
                  stage.show();
        });
        
        content.getChildren().addAll(txtWelcome,lblEmail, txtEmail, lblPassword, password, btnSignIn,lblAlert,sign);
        content.setVgap(10);
        content.setPadding(new Insets(10));
        FlowPane.setMargin(txtWelcome, new Insets(0,0,30,0));
        FlowPane.setMargin(sign, new Insets(20,0,0,0));
        content.setStyle("-fx-background-color: White;");
         root = new HBox(rootImg, content);
      rootImg.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
     content.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
     return root;
    }

    
}