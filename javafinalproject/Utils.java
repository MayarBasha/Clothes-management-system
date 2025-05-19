
package javafinalproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Utils {
   static TextField createTextField(String promptText, double maxWidth) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFocusTraversable(false);
        textField.setMaxWidth(maxWidth);
        return textField;
    }
    //===============================================================================================================
    static PasswordField createPasswordField(String promptText, double maxWidth) {
        PasswordField password = new PasswordField();
        password.setPromptText(promptText);
        password.setFocusTraversable(false);
        password.setMaxWidth(maxWidth);
        return password;
    }
    //=========================================================================================================
   static  Button createButton(String text, String style, double maxWidth) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setMaxWidth(maxWidth);
        return button;
    }
//==============================================================================================================================
     static VBox createVBoxView(int spacing,int padding,Pos alignment){
        VBox node = new VBox(spacing);
        node.setPadding(new Insets(padding));
        node.setAlignment(alignment);
        return node;
    }
    //===========================================================================================================================
     static HBox createHBoxView(int spacing,int padding,Pos alignment){
        HBox node = new HBox(spacing);
        node.setPadding(new Insets(padding));
        node.setAlignment(alignment);
        return node;
    }
    //================================================================================================================================
     static Label createLabel(String text,String style){
        Label lbl = new Label(text);
        lbl.setStyle(style);
        return lbl;
    }
}
