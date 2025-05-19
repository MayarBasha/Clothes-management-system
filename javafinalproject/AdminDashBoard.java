
package javafinalproject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.beans.binding.Bindings.add;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.geometry.Pos.TOP_LEFT;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import static javafx.scene.layout.Border.stroke;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafinalproject.User;
import javafinalproject.UserFileHandler;

public class AdminDashBoard  {
  private ReportsManager reportManager;
   private SignInView signIn;
    UserFileHandler userFileHandler = new UserFileHandler("users.txt");
    ProductFileHandler productFileHandler = new ProductFileHandler("products.txt");

    
    TableView tableView = new TableView();
    TableView tableView2 = new TableView();
    User selectedUser;
    Product selectedProduct;
    BorderPane root; 
    VBox contentArea; 
    Button btnUserAdd;
    Button btnUserEdit;
    Button btnUserRemove;
    String imagePath;
    Scene scene;
    public AdminDashBoard(SignInView signIn) {
        this.signIn = signIn;
    }

         public BorderPane getAdminView(String userName) {
        root = new BorderPane();
        contentArea = Utils.createVBoxView(10,20,TOP_CENTER);
        VBox sidebar = createSidebar();
        HBox topBar = Utils.createHBoxView(10,15,CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #659EC7");    
        Label lblTitle = Utils.createLabel("Admin Dashboard","-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        topBar.getChildren().add(lblTitle);
        Label lblWelcome = Utils.createLabel("Welcome,"+userName,"-fx-font-size: 20px");
        contentArea.getChildren().addAll(lblWelcome);
        root.setLeft(sidebar);
        root.setTop(topBar);
        root.setCenter(contentArea);
        return root;

       
    }
//=============================================================================================================================================================
    private VBox createSidebar() {
        VBox sidebar = Utils.createVBoxView(20,20,TOP_CENTER);
        sidebar.setStyle("-fx-background-color: #EDEDED");
        sidebar.setBorder(new Border(new BorderStroke( Color.rgb(101, 158, 199), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 0, 0))));
        sidebar.setPrefWidth(200);
        Button btnProduct = Utils.createButton("🛍 Manage Products", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        Button btnUser = Utils.createButton(" Manage users", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        Button btnReport = Utils.createButton("📊 View Reports", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        Button btnLogout = Utils.createButton("🚪 Log Out", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        
      
        
        
        btnProduct.setOnAction(e -> {  showProductView(); });
        btnUser.setOnAction(e -> {    showUserView();});
        btnReport.setOnAction(e -> {   AdminReportsView ();});
        btnLogout.setOnAction(e -> { 
            SignInView signInView = new SignInView();
            Scene signInScene = new Scene(signInView.getSignInView(),900,600);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                  stage.setScene(signInScene); 
                  stage.show();
        }); 
        
        
        
        
        sidebar.getChildren().addAll(btnUser, btnProduct, btnReport, btnLogout);
        return sidebar;
    }
      //  ====================================================================================================================================================================
    private void showUserView() {
        tableView.getColumns().clear();
        TableColumn colUsername = new TableColumn("Username");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn colPass = new TableColumn("Password");
        colPass.setCellValueFactory(new PropertyValueFactory<>("Password"));
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn colDesc = new TableColumn("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ObservableList<User> data = FXCollections.observableArrayList(userFileHandler.showUsers());
        tableView.setItems(data);
        tableView.getColumns().addAll(colUsername, colEmail, colPass, colId, colDesc);
        btnUserAdd = Utils.createButton("➕ Add", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        btnUserEdit = Utils.createButton("✏️ Edit", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        btnUserRemove = Utils.createButton("🗑 Remove", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        HBox changes = new HBox(20, btnUserAdd, btnUserEdit, btnUserRemove);
        changes.setAlignment(Pos.CENTER);
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(tableView, changes);
        
        btnUserAdd.setOnAction(e->{
        contentArea.getChildren().clear();
        contentArea.getChildren().setAll(addUserView()); });
        
        HBox confirmation = Utils.createHBoxView(10,0,CENTER);
        Label lblAlert = Utils.createLabel("","-fx-font-size: 14px;");
        btnUserEdit.setOnAction(e->{
           selectedUser = (User)tableView.getSelectionModel().getSelectedItem();
           if(selectedUser == null){
               lblAlert.setText("Please Choose An User First");
               confirmation.getChildren().add(lblAlert);
               contentArea.getChildren().add(confirmation);
           }else{
               contentArea.getChildren().clear();
               contentArea.getChildren().setAll(editUserView()); 
             }
        });
        btnUserRemove.setOnAction(e->{
           selectedUser = (User)tableView.getSelectionModel().getSelectedItem();
           if(selectedUser == null){
               lblAlert.setText("Please Choose An User First");
               confirmation.getChildren().clear();
               confirmation.getChildren().add(lblAlert);
               contentArea.getChildren().add(confirmation);
           }else{
                lblAlert.setText("Are You Sure?");
                Button btnYes = Utils.createButton("Yes","-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", 200);
                Button btnNo = Utils.createButton("No","-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", 200);
                confirmation.getChildren().clear();
                confirmation.getChildren().addAll(lblAlert,btnYes,btnNo);
               contentArea.getChildren().add(confirmation);
               btnYes.setOnAction(ex->{
                   userFileHandler.removeUser(selectedUser.getId());
                   showUserView();
               });
               btnNo.setOnAction(exe->{
                   contentArea.getChildren().remove(confirmation);
                   showUserView();
               });
                
             }
        });
        
        
    }
    //===========================================================================================================================
    private VBox addUserView() {
        VBox root = Utils.createVBoxView(15,20,TOP_CENTER);
        Label lblTitle = Utils.createLabel("Add New User","-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField txtUsername = Utils.createTextField("Username", 250);
        TextField txtEmail = Utils.createTextField("Email", 250);
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setMaxWidth(250);
        Label lblDescription = new Label("Select Description:");
        ComboBox<String> comboDescription = new ComboBox<>();
        comboDescription.getItems().addAll("Admin", "Customer", "cashier");
        HBox description = new HBox(15,lblDescription,comboDescription);
        description.setAlignment(CENTER);
        Button btnSave = Utils.createButton("Save User", "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;", 100);
        btnSave.setOnAction(e -> {
            
            User u = new User(txtUsername.getText(), txtEmail.getText(), txtPassword.getText(), comboDescription.getValue());
            userFileHandler.addUser(u);
            txtUsername.clear();
            txtEmail.clear();
            txtPassword.clear();
            comboDescription.getSelectionModel().clearSelection();  
            showUserView();
        });
        root.getChildren().addAll(lblTitle, txtUsername, txtEmail, txtPassword, description, btnSave);
        return root;
    }
    // =========================================================================================================================================================
    private VBox editUserView() {
        VBox root = Utils.createVBoxView(15,20,TOP_CENTER);
        Label lblTitle = Utils.createLabel("Edit User","-fx-font-size: 20px; -fx-font-weight: bold;");
        Label lblAlert = Utils.createLabel("","-fx-font-size: 14px;");
        VBox prevUserInfo = Utils.createVBoxView(15,20,CENTER);
        Label lblPrev = new Label("Before: ");
        TextField txtName = new TextField(selectedUser.getName());
        txtName.setMaxWidth(250);
        TextField txtEmail = new TextField(selectedUser.getEmail());
        txtName.setMaxWidth(250);
        PasswordField password = new PasswordField();
        password.setText(selectedUser.getPassword());
        password.setMaxWidth(250);
        TextField txtDescription = new TextField(selectedUser.getDescription());
        txtDescription.setMaxWidth(250);
        prevUserInfo.getChildren().addAll(lblPrev, txtName, txtEmail, password, txtDescription);
        
        VBox currUserInfo = Utils.createVBoxView(15,20,TOP_CENTER);
        Label lblCurr = new Label("After: ");
        TextField txtName2 = Utils.createTextField("User New Name", 250);
        TextField txtEmail2 = Utils.createTextField("User New Email", 250);
        PasswordField password2 = new PasswordField();
        password2.setPromptText("User New Password");
        password2.setMaxWidth(250);
        ComboBox<String> comboDescription = new ComboBox<>();
        comboDescription.getItems().addAll("Admin", "Customer", "cashier");
        currUserInfo.getChildren().addAll(lblCurr, txtName2, txtEmail2, password2, comboDescription);
        Button btnSave = Utils.createButton("Save Changes", "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;", 100);
        btnSave.setOnAction(e -> {
            if(txtName2.getText().isEmpty() || txtEmail2.getText().isEmpty() || password2.getText().isEmpty() || comboDescription == null ){
                lblAlert.setText("Please Fill All the Fields");
            }else{
            userFileHandler.editUser(txtName2.getText(),txtEmail2.getText(),password2.getText(),selectedUser.getId(),comboDescription.getValue());
            showUserView();
            }
        });
        HBox changeProduct = new HBox(prevUserInfo, currUserInfo);
        changeProduct.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblTitle, changeProduct, btnSave,lblAlert);
        return root;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    private void showProductView() {
       tableView2.getColumns().clear();
        TableColumn colProdName = new TableColumn("Name");
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn colProdPrice = new TableColumn("Price");
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn colProdQuantity = new TableColumn("Quantity");
        colProdQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn colProdId = new TableColumn("Id");
        colProdId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        TableColumn colProdSupplier = new TableColumn("Supplier");
        colProdSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        ObservableList<Product> prodData =  FXCollections.observableArrayList(productFileHandler.showProducts());
        tableView2.setItems(prodData);
        tableView2.getColumns().addAll(colProdName, colProdPrice, colProdQuantity, colProdId,colProdSupplier);
        Button btnProdAdd = Utils.createButton("➕ Add", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        Button btnProdEdit = Utils.createButton("✏️ Edit", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        Button btnProdRemove = Utils.createButton("🗑 Remove", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", Double.MAX_VALUE);
        HBox changes = new HBox(20, btnProdAdd, btnProdEdit, btnProdRemove);
        changes.setAlignment(Pos.CENTER);
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(tableView2, changes);
        btnProdAdd.setOnAction(e->{
        contentArea.getChildren().clear();
        contentArea.getChildren().setAll(addProductView()); });
        HBox confirmation = Utils.createHBoxView(10,0,CENTER);
        Label lblAlert = Utils.createLabel("","-fx-font-size: 14px;");
        btnProdEdit.setOnAction(e->{
           selectedProduct = (Product)tableView2.getSelectionModel().getSelectedItem();
           if(selectedProduct == null){
               lblAlert.setText("Please Choose Product First");
               confirmation.getChildren().add(lblAlert);
               contentArea.getChildren().add(confirmation);
           }else{
               contentArea.getChildren().clear();
               contentArea.getChildren().setAll(editProductView()); 
             }
        });
        btnProdRemove.setOnAction(e->{
           selectedProduct = (Product)tableView2.getSelectionModel().getSelectedItem();
           if(selectedProduct == null){
               lblAlert.setText("Please Choose Product First");
               confirmation.getChildren().clear();
               confirmation.getChildren().add(lblAlert);
               contentArea.getChildren().add(confirmation);
           }else{
                lblAlert.setText("Are You Sure?");
                Button btnYes = Utils.createButton("Yes","-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", 200);
                Button btnNo = Utils.createButton("No","-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", 200);
                confirmation.getChildren().clear();
                confirmation.getChildren().addAll(lblAlert,btnYes,btnNo);
               contentArea.getChildren().add(confirmation);
               btnYes.setOnAction(ex->{
                   productFileHandler.removeProduct(selectedProduct.getProductID());
                   showProductView();
               });
               btnNo.setOnAction(exe->{
                   contentArea.getChildren().remove(confirmation);
                   showProductView();
               });
                
             }
        });
                }
        
       
      
    
      //=========================================================================================================================================================
    private VBox addProductView() {
        VBox root = Utils.createVBoxView(15,20,TOP_CENTER);
        Label lblTitle = Utils.createLabel("Add New Product","-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField txtName = Utils.createTextField("Product Name", 250);
        TextField txtPrice = Utils.createTextField("Product Price", 250);
        TextField txtQuantity = Utils.createTextField("Product Quantity", 250);
        TextField txtSupplier = Utils.createTextField("Supplier Name", 250);
        Button uploadImageButton = Utils.createButton("Upload Image", "-fx-background-color: #5B8EB3; -fx-text-fill: white; -fx-font-size: 14px;", 250);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        uploadImageButton.setOnAction(e -> {
         FileChooser fileChooser = new FileChooser();
         fileChooser.getExtensionFilters().addAll(  new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")  );
          File selectedFile = fileChooser.showOpenDialog(null);
          if (selectedFile != null) {
          imagePath = selectedFile.toURI().toString();
            }
            });
        Button btnSave = Utils.createButton("Save Product", "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;", 100);
        btnSave.setOnAction(e -> {
            Product p = new Product(txtName.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQuantity.getText()), txtSupplier.getText());
            p.setImagePath(imagePath);
            productFileHandler.addProduct(p);
            txtName.clear();
            txtPrice.clear();
            txtQuantity.clear();
            txtSupplier.clear();  
            showProductView();
        });
        root.getChildren().addAll(lblTitle, txtName, txtPrice, txtQuantity, txtSupplier, uploadImageButton, imageView, btnSave);
        return root;
    }
    //  ==================================================================================================================================================
    private VBox editProductView() {
        VBox root = Utils.createVBoxView(15,20,TOP_CENTER);
        Label lblTitle = Utils.createLabel("Edit Product","-fx-font-size: 20px; -fx-font-weight: bold;");
        Label lblAlert = Utils.createLabel("","-fx-font-size: 14px;");
        VBox prevProduct = Utils.createVBoxView(15,20,CENTER);
        Label lblFrom = Utils.createLabel("From: ","-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField txtName = new TextField(selectedProduct.getName());
        txtName.setMaxWidth(250);
        TextField txtPrice = new TextField(String.valueOf(selectedProduct.getPrice()));
        txtPrice.setMaxWidth(250);
        TextField txtQuantity = new TextField(String.valueOf(selectedProduct.getQuantity()));
        txtPrice.setMaxWidth(250);
        TextField txtSupplier = new TextField(selectedProduct.getSupplierName());
        txtSupplier.setMaxWidth(250);
        prevProduct.getChildren().addAll(lblFrom, txtName, txtPrice, txtQuantity,txtSupplier);
        VBox currProduct = Utils.createVBoxView(15,20,CENTER);
        Label lblTo = Utils.createLabel("To: ","-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField txtName2 = Utils.createTextField("Product New Name", 250);
        TextField txtPrice2 = Utils.createTextField("Product New Price", 250);
        TextField txtQuantity2 = Utils.createTextField("Product New Quantity", 250);
        TextField txtSupplier2 = Utils.createTextField("Product New Supplier", 250);
        currProduct.getChildren().addAll(lblTo, txtName2, txtPrice2, txtQuantity2,txtSupplier2);
        Button btnSave = Utils.createButton("Save Changes", "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;", 100);
            btnSave.setOnAction(e -> {
            if(txtName2.getText().isEmpty() || txtPrice2.getText().isEmpty() || txtQuantity2.getText().isEmpty() || txtSupplier2.getText().isEmpty()  ){
                lblAlert.setText("Please Fill All the Fields");
            }else{
            productFileHandler.editProduct(selectedProduct.getProductID(),txtName2.getText(),Double.parseDouble(txtPrice2.getText()),Integer.parseInt(txtQuantity2.getText()),
                    txtSupplier2.getText()
                    );
            showProductView();
            }
        });
        HBox changeProduct = new HBox(prevProduct, currProduct);
        changeProduct.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblTitle, changeProduct, btnSave);
        return root;
    }
    //  ======================================================================================================================================================


private void AdminReportsView() {
    contentArea.getChildren().clear();
    ProductFileHandler productHandler = new ProductFileHandler("products.txt");
    reportManager = new ReportsManager(productHandler);

    // UI elements
    TextArea reportArea = new TextArea();
    reportArea.setEditable(false);
    reportArea.setWrapText(true);
    reportArea.setPrefHeight(400);

    Button mostSoldProductBtn = new Button("📦 Most Sold Product");
    Button supplierSalesBtn = new Button("📊 Supplier Sales");
    Button totalRevenueBtn = new Button("💰 Total Revenue");
    Button userOrderCountBtn = new Button("🧑‍💼 Order Count Per User");
    Button topOrderingUserBtn = new Button("👑 Top Ordering User");
    Button topRevenueUserBtn = new Button("🏆 Top Revenue User");
    Button viewAllOrdersBtn = new Button("📄 All Orders");
    Button revenuePeriodBtn = new Button("📅 Revenue in Period");

    
    mostSoldProductBtn.setOnAction(e -> reportArea.setText(reportManager.getMostSoldProduct()));
    supplierSalesBtn.setOnAction(e -> reportArea.setText(reportManager.getSoldProductsPerSupplier()));
    totalRevenueBtn.setOnAction(e -> reportArea.setText(reportManager.getTotalRevenue()));
    userOrderCountBtn.setOnAction(e -> reportArea.setText(reportManager.getOrderCountPerUser()));
    topOrderingUserBtn.setOnAction(e -> reportArea.setText(reportManager.getTopOrderingUser()));
    topRevenueUserBtn.setOnAction(e -> reportArea.setText(reportManager.getTopRevenueUser()));
    viewAllOrdersBtn.setOnAction(e -> reportArea.setText(reportManager.viewAllOrders()));

    revenuePeriodBtn.setOnAction(e -> {
       
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now();
        reportArea.setText(reportManager.getRevenueInPeriod(start, end));
    });

  
    VBox buttonsBox = new VBox(10,
            mostSoldProductBtn,
            supplierSalesBtn,
            totalRevenueBtn,
            userOrderCountBtn,
            topOrderingUserBtn,
            topRevenueUserBtn,
            viewAllOrdersBtn,
            revenuePeriodBtn
    );
    buttonsBox.setPrefWidth(200);

    BorderPane root = new BorderPane();
    root.setLeft(buttonsBox);
    root.setCenter(reportArea);
    root.setPadding(new Insets(10));
    contentArea.getChildren().add(root);
}

}


  
