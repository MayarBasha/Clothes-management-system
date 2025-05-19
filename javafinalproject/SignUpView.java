
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
import javafx.scene.control.ComboBox;
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

public class SignUpView {
    
  TextField txtUserName,txtEmail,password;
 Button btnSignUp;
 UserFileHandler userFileHandler = new UserFileHandler("users.txt");
   CustomerDashBoard customer = new CustomerDashBoard();
   CheckTextFields checkUserField = new CheckTextFields();

 public HBox getSignUpScene(){
        FlowPane root1 = new FlowPane();
        root1.setOrientation(Orientation.VERTICAL);
        root1.setAlignment(Pos.CENTER);
      
        Text txtLogo = new Text("FOR YOU");
        txtLogo.setFont(Font.font("Magneto",FontWeight.LIGHT,FontPosture.ITALIC,50));
        VBox rootImg = new VBox(txtLogo);
        rootImg.setAlignment(Pos.CENTER);
        rootImg.setStyle("-fx-background-color: #659EC7;");
        
        Text txtWelcome = new Text("This For You");
        txtWelcome.setFont(Font.font("Times New Roman",20));
        
        Label lblUserName = new Label("User name");
        Label lblEmail = new Label("Email");
        Label lblPassword = new Label("Password");
        Label lblAlert = Utils.createLabel("", "-fx-font-size: 13px");
        
         txtUserName = Utils.createTextField("", 200);
        txtEmail =  Utils.createTextField("", 200);
        password = Utils.createPasswordField("", 200);
        
      
        btnSignUp = Utils.createButton("Sign Up","-fx-background-radius: 30; -fx-font-size: 14px; -fx-padding: 10 ; -fx-background-color: #659EC7; -fx-text-fill: white;" ,100);
         root1.getChildren().addAll(txtWelcome,lblUserName,txtUserName,lblEmail, txtEmail, lblPassword, password, btnSignUp,lblAlert);
         
         btnSignUp.setOnAction(e->{
             String checkField = checkUserField.checkUserField(txtUserName.getText(),txtEmail.getText(), password.getText());
             if(checkField.equals("Ok")){
                String checkUser = userFileHandler.checkSignUp(txtUserName.getText(),txtEmail.getText(),password.getText());
                if(checkUser.equals("Ok")){
                User u =new User(  txtUserName.getText(),txtEmail.getText(),password.getText(),"customer");
                userFileHandler.addUser(u);
                Scene customerScene= new Scene(customer.getCustomerView(u.getName(),u.getId()),900,600);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(customerScene); 
                  stage.show();
            }else{ lblAlert.setText(checkUser);}
            }
             else{lblAlert.setText(checkField);}
         });
        root1.setVgap(10);
        root1.setPadding(new Insets(10));
        FlowPane.setMargin(txtWelcome, new Insets(0,0,30,0));
        root1.setStyle("-fx-background-color: White;");

        HBox root = new HBox(rootImg, root1);
      rootImg.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
     root1.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
     return root;


       
    }

}
