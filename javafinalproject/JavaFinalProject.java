
package javafinalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JavaFinalProject extends Application {
    

  
 SignInView signInView = new SignInView();
    @Override
    public void start(Stage primaryStage) {
         
         Text welcome = new Text("Welcome To Our WEb");
         welcome.setFont(Font.font("Times New Roman",20));
         Button btnEnter = new Button("SignIn");
         btnEnter.setStyle("-fx-background-radius: 30; -fx-font-size: 14px; -fx-padding: 10 ; -fx-background-color: #659EC7; -fx-text-fill: white;");
         VBox root = new VBox(20,welcome,btnEnter);
         root.setAlignment(Pos.CENTER);
         btnEnter.setOnAction(e->{
            Scene signInScene = new Scene(signInView.getSignInView(),900,600);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(signInScene); 
                  stage.show();
         });
         
         

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }


    
}