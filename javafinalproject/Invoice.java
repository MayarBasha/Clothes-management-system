
package javafinalproject;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class Invoice {
       public VBox getInvoiceView(Order order)  {
       Cashier c = new Cashier();
       

  
        
        Label title = new Label("INVOICE");
        title.setFont(new Font("Times New Roman", 32));


  
         Label InfoLabel = new Label("For You");
        InfoLabel.setFont(Font.font("Times New Roman",FontWeight.BOLD ,24)); 
        InfoLabel.setTextFill(Color.DARKGOLDENROD); 
        
        VBox shopInfo = new VBox(
           InfoLabel,
            new Label("2025 Al-sharqia"),
            new Label("Egypt")
        );
        
        

       
        Label billToLabel = new Label("Cashier");
        billToLabel.setFont(Font.font("Times New Roman",FontWeight.BOLD ,18)); 
        billToLabel.setTextFill(Color.DARKBLUE); 
        
        VBox cashier = new VBox(
            billToLabel,
            new Label(c.getCashier()),
            new Label("Alsharqia, Zag 44511")
        );


        
        Label shipToLabel = new Label("SHIP TO");
       shipToLabel.setFont(Font.font("Times New Roman",FontWeight.BOLD ,18)); 
       shipToLabel.setTextFill(Color.DARKBLUE); 
        
        VBox shipTo = new VBox(
            shipToLabel,
           new Label(order.getCustomerName()),
           new Label(order.getCustomerAddress()),

            new Label("Egypt")
        );


  
        VBox invoiceDetails = new VBox(20,
            new Label("INVOICE #:Egy-"+order.generateOrderID()),
            new Label("INVOICE DATE: "+order.getOrderDate())
        );

        HBox topSection = new HBox(60, shopInfo, cashier, shipTo, invoiceDetails);
        topSection.setPadding(new Insets(20));

      TableView<CartItems> table = new TableView<>();
table.setPrefHeight(200);

TableColumn<CartItems, Integer> qtyCol = new TableColumn<>("Quantity");
qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

TableColumn<CartItems, String> descCol = new TableColumn<>("DESCRIPTION");
descCol.setCellValueFactory(cellData -> 
    new SimpleStringProperty(cellData.getValue().getProduct().getName())
);

TableColumn<CartItems, Double> unitPriceCol = new TableColumn<>("UNIT PRICE");
unitPriceCol.setCellValueFactory(cellData -> 
    new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject()
);

TableColumn<CartItems, Double> amountCol = new TableColumn<>("AMOUNT");
amountCol.setCellValueFactory(cellData -> 
    new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject()
);

table.getColumns().addAll(qtyCol, descCol, unitPriceCol, amountCol);

ObservableList<CartItems> data = FXCollections.observableArrayList(order.getOrderItems());
table.setItems(data);

 
  
        VBox totalsBox = new VBox(
            new Label("Subtotal: " + order.getTotalBeforeDiscount()),
            new Label("Sales Tax"+order.getDiscount()),
            new Label("TOTAL: "+order.getFinalTotal())
        );
        totalsBox.setAlignment(Pos.CENTER_RIGHT);
        totalsBox.setPadding(new Insets(10));


  
        Label signature = new Label(c.getCashier());
        signature.setFont(new Font("Arial", 20));
        signature.setPadding(new Insets(10));


  
        Label thankYouLabel = new Label("Thank you");
        thankYouLabel.setTextFill(Color.DARKBLUE);  
        thankYouLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        
        HBox footer = new HBox(
           thankYouLabel,
            new VBox(
                new Label("TERMS & CONDITIONS"),
                new Label("Returns can be made within 3 days"),
                new Label("Please make checks payable to: For You.com")
            )
        );
        footer.setSpacing(20);
        footer.setPadding(new Insets(20));


  
        VBox root = new VBox(10, title, topSection, table, totalsBox, signature, footer);
        root.setPadding(new Insets(20));
        return root;
    }


    
 
}
