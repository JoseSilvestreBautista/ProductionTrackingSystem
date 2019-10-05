package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Jose Silvestre-Bautista
 * @bug None known at time.
 */
public class Controller {

  /** The productName is used to receive text from the user. */
  @FXML private TextField productName;

  /** The manufacturer is used to receive text from user */
  @FXML private TextField manufacturer;
  /** The itemType allows the user to choose from four possible itemType choices */
  @FXML private ChoiceBox<String> itemType;
  /** The selectionChooseQuantity allows the user to choose from 1 to 10 of certain product. */
  @FXML private ComboBox<Integer> selectionChooseQuantity;

  /**
   * This method initializes text in TextFields named manufacturer and productName, ChoiceBox named
   * itemType, and Combobox named selectionChooseQuantity.
   */
  public void initialize() {
    itemType.getItems().addAll("Audio", "Visual", "AudioMobile", "VisualMobile");
    ObservableList<Integer> numbers =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    selectionChooseQuantity.getItems().addAll(numbers);
    selectionChooseQuantity.getSelectionModel().selectFirst();
    selectionChooseQuantity.setEditable(true);
    selectionChooseQuantity.setValue(5);
    System.out.print("Stuff was initialized.");
  }

  /**
   * This method receives the input from productName, manufacturer, and itemType then sets them
   * equal to String variables. The string variables are used in SQL command to store user input
   * into a database.
   */
  public void addProduct() {
    /*
     inputFromProductName sets text from productName to a sting.
     inputFromManufacturer sets text from manufacturer to a sting.
     inputFromItemType sets text from itemType to a sting.
    */
    String inputFromProductName = productName.getText();
    String inputFromManufacturer = manufacturer.getText();
    String inputFromItemType = itemType.getValue();

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProductionRecord";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql =
          "INSERT INTO Product(type, manufacturer, name) VALUES ( ' "
              + inputFromItemType
              + " ', '"
              + inputFromManufacturer
              + "', '"
              + inputFromProductName
              + "')";
      System.out.println(sql);
      stmt.executeUpdate(sql);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /** In the future this method will show production records. */
  public void recordProduction() {
    System.out.println("This is the Record Production!");
  }
}
