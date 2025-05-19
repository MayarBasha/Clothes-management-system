/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OrderView  {
       private String selectedPaymentMethod = "";
    private User user = new User();
    private TextField lastTextField;
    private Cart cart = new Cart();
    private String userId;

    public VBox getOrderView(Cart cart, String userId) {
        this.cart = cart;
        this.userId = userId;


        VBox vroot = new VBox(10);
        vroot.setPadding(new Insets(10));

        for (CartItems item : cart.getItems()) {
            Product product = item.getProduct();

            ImageView productImage = new ImageView("file:"); 
            productImage.setFitWidth(80);
            productImage.setFitHeight(80);

            Label name = new Label("Name: " + product.getName());
            Label price = new Label("Price: " + product.getPrice());
            Label quantity = new Label("Quantity: " + item.getQuantity());

            VBox infoBox = new VBox(5, name, price, quantity);
            HBox itemBox = new HBox(10, productImage, infoBox);
            itemBox.setPadding(new Insets(10));
            itemBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-background-radius: 6;");

            vroot.getChildren().add(itemBox);
        }


        ScrollPane scrollPaneLeft = new ScrollPane(vroot);
        scrollPaneLeft.setFitToWidth(true);
        scrollPaneLeft.setPrefWidth(400);

       
        VBox sideVBox = new VBox(10);
        sideVBox.setPadding(new Insets(5, 30, 5, 20));

        GridPane orderGrid = new GridPane();
        orderGrid.setVgap(10);
        orderGrid.setHgap(15);

        Label titleLabel = new Label("Order summary:");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        orderGrid.add(titleLabel, 0, 0, 2, 1);

        int quantity = cart.getItems().stream().mapToInt(CartItems::getQuantity).sum();
        double subtotal = cart.getTotal();
        double shipping = 50;
        double discount = cart.getDiscount();
        double total = subtotal - discount + shipping;

        TextField quantityField = new TextField(String.valueOf(quantity));
        TextField subtotalField = new TextField(String.format("%.2f", subtotal));
        TextField shippingField = new TextField(String.format("%.2f", shipping));
        TextField discountField = new TextField(String.format("%.2f", discount));
        TextField totalField = new TextField(String.format("%.2f", total));


        quantityField.setEditable(false);
        subtotalField.setEditable(false);
        shippingField.setEditable(false);
        discountField.setEditable(false);
        totalField.setEditable(false);

        orderGrid.addRow(1, new Label("Quantity:"), quantityField);
        orderGrid.addRow(2, new Label("SubTotal:"), subtotalField);
        orderGrid.addRow(3, new Label("Shipping:"), shippingField);
        orderGrid.addRow(4, new Label("Discount:"), discountField);
        orderGrid.addRow(5, new Label("Total:"), totalField);

        sideVBox.getChildren().add(orderGrid);


        HBox topRow = new HBox(10, scrollPaneLeft, sideVBox);
        topRow.setPadding(new Insets(10));
        topRow.setPrefHeight(300);
        HBox.setHgrow(scrollPaneLeft, Priority.ALWAYS);
        HBox.setHgrow(sideVBox, Priority.ALWAYS);

       
        VBox vbox3 = new VBox(10);
        Label customerLabel = new Label("Contact information:");
        customerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        vbox3.getChildren().add(customerLabel);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        TextField txtEmail = new TextField();
        TextField txtUsername = new TextField();
        TextField txtPhone = new TextField();
        TextField txtAddress = new TextField();

        String[] labels = {"Email", "Username", "Phone Number", "Address"};
        TextField[] fields = {txtEmail, txtUsername, txtPhone, txtAddress};

        for (int i = 0; i < labels.length; i++) {
            formGrid.addRow(i, new Label(labels[i] + ":"), fields[i]);
        }

        vbox3.getChildren().add(formGrid);

        VBox vbox4 = new VBox(10);
        Label paymentLabel = new Label("Payment method:");
        paymentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        vbox4.getChildren().add(paymentLabel);

        HBox btnsB = new HBox(15);
        Button btnCash = new Button("CashOnDelivery");
        Button btnCard = new Button("CreditCardPayment");
        Button btnWallet = new Button("Wallet");
        btnsB.getChildren().addAll(btnCash, btnCard, btnWallet);
        btnsB.setAlignment(Pos.CENTER);
        vbox4.getChildren().add(btnsB);

       
        btnCard.setOnAction(e -> {
            TextField tf = new TextField();
            tf.setPromptText("Enter your Card ID");
            handlePaymentInput(tf, vbox4);
        });

        btnWallet.setOnAction(e -> {
            TextField tf = new TextField();
            tf.setPromptText("Enter number or Wallet ID");
            handlePaymentInput(tf, vbox4);
        });

        btnCash.setOnAction(e -> selectedPaymentMethod = "Cash On Delivery");

        
        HBox bottomRow = new HBox(10, vbox3, vbox4);
        bottomRow.setPadding(new Insets(10));
        HBox.setHgrow(vbox3, Priority.ALWAYS);
        HBox.setHgrow(vbox4, Priority.ALWAYS);
        vbox4.setPadding(new Insets(10, 20, 10, 50));

       
        HBox additionalHBox = new HBox(10);
        additionalHBox.setPadding(new Insets(5, 50, 5, 50));
        Button leftButton = new Button("Back");
        leftButton.setOnAction(e->{
            CustomerDashBoard customerDashBoard = new CustomerDashBoard();
            Scene scene = new Scene(customerDashBoard.getCustomerView(user.getName(), userId));
              Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(scene); 
                  stage.show();
            
        });
        Button rightButton = new Button("Complete order");

        rightButton.setOnAction(e -> {
            Order order = new Order(cart);
            order.setCustomerName(txtUsername.getText());
            order.setCustomerEmail(txtEmail.getText());
            order.setCustomerPhone(txtPhone.getText());
            order.setCustomerAddress(txtAddress.getText());
            order.setPayment(selectedPaymentMethod);
            order.setUserID(userId);
            order.setStatus("Paid");
            OrderFileHandler.saveOrder(order);
            Invoice invoice = new Invoice();
            Scene scene = new Scene(invoice.getInvoiceView(order));
             Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
                  stage.setScene(scene); 
                  stage.show();
            
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        additionalHBox.getChildren().addAll(leftButton, spacer, rightButton);

    
        VBox root = new VBox(10, topRow, bottomRow, additionalHBox);
        root.setPadding(new Insets(10));
        VBox.setVgrow(topRow, Priority.ALWAYS);
        VBox.setVgrow(bottomRow, Priority.ALWAYS);

        return root;
    }

    
    private void handlePaymentInput(TextField tf, VBox parentBox) {
        tf.setPrefWidth(200);
        HBox centerBox = new HBox(tf);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(0, 30, 0, 30));
        parentBox.getChildren().add(centerBox);

        if (lastTextField != null && lastTextField != tf) {
            lastTextField.clear();
            lastTextField.setVisible(false);
        }

        lastTextField = tf;
        selectedPaymentMethod = tf.getPromptText();
    }
}

    


