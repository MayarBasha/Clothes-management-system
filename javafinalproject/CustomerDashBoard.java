
package javafinalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.TOP_CENTER;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class CustomerDashBoard  {
    private String userId, userName;
    OrderView orderView = new OrderView();

    ProductFileHandler productFileHandler = new ProductFileHandler("products.txt");

    private VBox contentArea;
    List<Product> allProducts;
    Cart cart = new Cart();

    public BorderPane getCustomerView(String userName, String userId) {
        this.userId = userId;
        this.userName = userName;

        BorderPane root = new BorderPane();
        contentArea = Utils.createVBoxView(10, 20, Pos.TOP_CENTER);
        VBox sidebar = createSidebar();
        HBox topBar = Utils.createHBoxView(10, 15, Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #2c3e50;");
        Label lblTitle = Utils.createLabel("Customer Dashboard",  "-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        topBar.getChildren().add(lblTitle);
        Label lblWelcome = Utils.createLabel("Welcome, " + userName + "!", "-fx-font-size: 20px");
        contentArea.getChildren().addAll(lblWelcome);
        root.setLeft(sidebar);
        root.setTop(topBar);
        root.setCenter(contentArea);
        return root;
    }

    private VBox createSidebar() {
        VBox sidebar = Utils.createVBoxView(20, 20, Pos.TOP_CENTER);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(200);

        Button btnHome = Utils.createButton("🏠 Home", "-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;",
                Double.MAX_VALUE);
        Button btnCart = Utils.createButton("🛒 View Cart", "-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;",
                Double.MAX_VALUE);
        Button btnOrders = Utils.createButton("📦 My Orders", "-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;",
                Double.MAX_VALUE);
        Button btnLogout = Utils.createButton("🚪 Log Out", "-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;",
                Double.MAX_VALUE);
        btnHome.setOnAction(e -> showProducts());
        btnCart.setOnAction(e -> showCart());
        btnOrders.setOnAction(e -> showOrders());
        btnLogout.setOnAction(e -> {
        SignInView signInView = new SignInView();
            Scene signInScene = new Scene(signInView.getSignInView(),900,600);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                  stage.setScene(signInScene); 
                  stage.show();
        });
        sidebar.getChildren().addAll(btnHome, btnCart, btnOrders, btnLogout);
        return sidebar;
    }

    private void showProducts() {
        contentArea.getChildren().clear();
        TextField searchField = Utils.createTextField("Search for products...", 200);
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e-> showSearchView());
        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        FlowPane productContainer = new FlowPane();
        productContainer.setHgap(15);
        productContainer.setVgap(20);
        productContainer.setPadding(new Insets(10));
        productContainer.setPrefWrapLength(800);
        productContainer.getChildren().clear();

        allProducts = productFileHandler.showProducts();
        for (Product product : allProducts) {
            VBox productCard = Utils.createVBoxView(5, 10, Pos.CENTER_LEFT);
            productCard.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8;");
            productCard.setPrefSize(200, 300);

            ImageView imageView;
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                imageView = new ImageView(new Image(product.getImagePath()));
            } else {
                imageView = new ImageView(new Image("file:/Users/soft zone/Downloads/T-shirt.jpg"));
            }
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            Label lblName = new Label(product.getName());
            Label lblPrice = new Label("Price: $" + product.getPrice());
            Label lblAvailable = new Label("Available: " + product.getQuantity());


            int[] selectedQuantity = {1};

            TextField txtSelectedQuantity = new TextField(String.valueOf(selectedQuantity[0]));
            txtSelectedQuantity.setPrefWidth(40);
            txtSelectedQuantity.setAlignment(Pos.CENTER);
            txtSelectedQuantity.setEditable(false);

            Button ProdInc = new Button("+");
            ProdInc.setOnAction(e -> {
                selectedQuantity[0]++;
                txtSelectedQuantity.setText(String.valueOf(selectedQuantity[0]));
            });

            Button ProdDec = new Button("-");
            ProdDec.setOnAction(e -> {
                if (selectedQuantity[0] > 1) {
                    selectedQuantity[0]--;
                    txtSelectedQuantity.setText(String.valueOf(selectedQuantity[0]));
                }
            });

            HBox changeQuantity = new HBox(10, ProdInc, txtSelectedQuantity, ProdDec);
            changeQuantity.setAlignment(Pos.CENTER);

            Button btnAddToCart = new Button("Add to cart");
            btnAddToCart.setOnAction(e -> {
                boolean found = false;
                for (CartItems item : cart.getItems()) {
                    if (item.getProduct().getProductID().equals(product.getProductID())) {
                        for (int i = 0; i < selectedQuantity[0]; i++) {
                            item.increaseQuantity();
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    cart.getItems().add(new CartItems(product, selectedQuantity[0]));
                }
            });

            productCard.getChildren().addAll(imageView, lblName, lblPrice, lblAvailable, changeQuantity, btnAddToCart);
            productCard.setAlignment(Pos.CENTER);
            productContainer.getChildren().add(productCard);
        }

        ScrollPane scroll = new ScrollPane(productContainer);
        scroll.setFitToWidth(true);

        contentArea.getChildren().addAll(searchBox, scroll);
    }

    private void showCart() {
        contentArea.getChildren().clear();

        Label title = new Label("🛒 Your Cart");
        title.setFont(Font.font("Arial", 20));

        VBox cartItems = new VBox(15);
        cartItems.setPadding(new Insets(10));

        List<CheckBox> selectedCheckBoxes = new ArrayList<>();

        if (cart.getItems().isEmpty()) {
            cartItems.getChildren().add(new Label("Your cart is empty."));
        } else {
            List<CartItems> items = cart.getItems();
            for (int i = 0; i < items.size(); i++) {
                CartItems item = items.get(i);
                Product p = item.getProduct();

                HBox itemCard = new HBox(10);
                itemCard.setAlignment(Pos.CENTER_LEFT);
                itemCard.setPadding(new Insets(10));
                itemCard.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");

                ImageView imageView;
                if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
                    imageView = new ImageView(new Image(p.getImagePath()));
                } else {
                    imageView = new ImageView(new Image("file:/Users/soft zone/Downloads/T-shirt.jpg"));
                }
                imageView.setFitWidth(60);
                imageView.setFitHeight(60);

                VBox info = new VBox(5);
                Label name = new Label(p.getName());
                name.setStyle("-fx-font-weight: bold;");
                Label price = new Label("Price: $" + p.getPrice());
                Label quantityLbl = new Label("Quantity: " + item.getQuantity()); 
                info.getChildren().addAll(name, price, quantityLbl);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                CheckBox select = new CheckBox();
                selectedCheckBoxes.add(select);

                Button removeBtn = new Button("❌");
                removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 14px;");
                int index = i; 
                removeBtn.setOnAction(e -> {
                    cart.removeProduct(p);
                    showCart();
                });

                itemCard.getChildren().addAll(imageView, info, spacer, select, removeBtn);
                cartItems.getChildren().add(itemCard);
            }

            Button btnViewOrder = new Button("✅ Go to cashier");
            btnViewOrder.setOnAction(e -> {
                List<CartItems> selectedItems = new ArrayList<>();
                for (int i = 0; i < selectedCheckBoxes.size(); i++) {
                    if (selectedCheckBoxes.get(i).isSelected()) {
                        selectedItems.add(cart.getItems().get(i));
                    }
                }

                if (!selectedItems.isEmpty()) {
                    Cart tempCart = new Cart();
                    tempCart.getItems().addAll(selectedItems);
                    OrderView orderView = new OrderView();
                    Scene scene = new Scene(orderView.getOrderView(tempCart, userId),900,600);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            });

            cartItems.getChildren().add(btnViewOrder);
        }

        ScrollPane scrollPane = new ScrollPane(cartItems);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: white;");

        contentArea.getChildren().addAll(title, scrollPane);
    }

    
   private void showOrders() {
    contentArea.getChildren().clear();

    Label title = new Label("📦 My Orders");
    title.setFont(Font.font("Arial", 20));
    contentArea.getChildren().add(title);

    List<String> orders = OrderFileHandler.readOrdersForUser(userId);

    if (orders.isEmpty()) {
        contentArea.getChildren().add(new Label("No orders found."));
        return;
    }

    VBox ordersBox = new VBox(20);
    ordersBox.setPadding(new Insets(10));

    for (String orderText : orders) {
        VBox orderContainer = new VBox(10);
        orderContainer.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        
        Label orderLabel = new Label(orderText);
        orderLabel.setWrapText(true);
        orderLabel.setStyle("-fx-background-color: #ffffff; -fx-padding: 5;");
        orderLabel.setMaxWidth(Double.MAX_VALUE);
        orderLabel.setMaxHeight(Double.MAX_VALUE);

      
        HBox starsBox = new HBox(5);
        starsBox.setAlignment(Pos.CENTER_LEFT);


        Label[] stars = new Label[5];
        for (int i = 0; i < 5; i++) {
            Label star = new Label("☆"); 
            star.setStyle("-fx-font-size: 24px; -fx-text-fill: gray;");
            final int starIndex = i;
            star.setOnMouseClicked(e -> {
                for (int j = 0; j < 5; j++) {
                    if (j <= starIndex) {
                        stars[j].setText("★");
                        stars[j].setStyle("-fx-font-size: 24px; -fx-text-fill: gold;");
                    } else {
                        stars[j].setText("☆");
                        stars[j].setStyle("-fx-font-size: 24px; -fx-text-fill: gray;");
                    }
                }
                System.out.println("Rated order with " + (starIndex + 1) + " stars");
            });
            stars[i] = star;
            starsBox.getChildren().add(star);
        }

        orderContainer.getChildren().addAll(orderLabel, starsBox);
        ordersBox.getChildren().add(orderContainer);
    }

    ScrollPane scrollPane = new ScrollPane(ordersBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(400);

    contentArea.getChildren().add(scrollPane);
}
  private void showSearchView(){
     contentArea.getChildren().clear();

    Label lblTitle = new Label("Search Products by Name");
    lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    TextField searchField = new TextField();
    searchField.setPromptText("Enter product name...");

    Button searchButton = new Button("Search");

    FlowPane resultContainer = new FlowPane();
    resultContainer.setHgap(15);
    resultContainer.setVgap(20);
    resultContainer.setPadding(new Insets(10));
    resultContainer.setPrefWrapLength(800);

    searchButton.setOnAction(e -> {
        String keyword = searchField.getText().trim().toLowerCase();
        List<Product> results = productFileHandler.searchProductByName(keyword);
        resultContainer.getChildren().clear();

        if (results.isEmpty()) {
            resultContainer.getChildren().add(new Label("No matching products found."));
        } else {
            for (Product product : results) {
                VBox card = new VBox(5);
                card.setAlignment(Pos.CENTER);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
                card.setPrefSize(180, 250);

                ImageView imageView;
                if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                    imageView = new ImageView(new Image(product.getImagePath()));
                } else {
                    imageView = new ImageView(new Image("file:/path/to/default/image.jpg"));
                }
                imageView.setFitWidth(160);
                imageView.setFitHeight(150);

                Label name = new Label(product.getName());
                Label price = new Label("Price: $" + product.getPrice());
                Label quantity = new Label("Available: " + product.getQuantity());

                card.getChildren().addAll(imageView, name, price, quantity);
                resultContainer.getChildren().add(card);
            }
        }
    });

    VBox layout = new VBox(15, lblTitle, new HBox(10, searchField, searchButton), resultContainer);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.TOP_CENTER);

    contentArea.getChildren().add(layout);
}

}