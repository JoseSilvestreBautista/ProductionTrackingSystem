package sample;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/** @author Jose Silvestre-Bautista */
public class Controller {

  /** The productName is used to receive text from the user. */
  @FXML private TextField productName;
  /** The manufacturer is used to receive text from user */
  @FXML private TextField manufacturer;
  /** The itemType allows the user to choose from four possible itemType choices */
  @FXML private ChoiceBox<String> itemType;
  /** The selectionChooseQuantity allows the user to choose from 1 to 10 of certain product. */
  @FXML private ComboBox<Integer> selectionChooseQuantity;
  /** The table that contains all products. */
  @FXML private TableView<NewProduct> productLineTable;
  /** Holds the product id number. */
  @FXML private TableColumn idColumn;
  /** Holds the name of the product. */
  @FXML private TableColumn<String, String> nameColumn;
  /** Holds the item type of the product. */
  @FXML private TableColumn<?, ?> typeColumn;
  /** Holds the manufacturer name of the product. */
  @FXML private TableColumn<?, ?> manufacturerColumn;
  /** Displays all products available to be chosen */
  @FXML private ListView chooseProductListView;
  /** Displays all products available */
  private ObservableList<NewProduct> productLine = FXCollections.observableArrayList();
  /** Database Connection */
  private String JDBC_DRIVER = "org.h2.Driver";
  /** Database location */
  private String DB_URL = "jdbc:h2:./res/ProductionTables";
  //  Database credentials
  private String USER = "";
  private String PASS = "";
  private Connection conn;
  private Statement stmt;
  /** Row info from list view */
  String infoFromListView;

  /**
   * This method initializes text in TextFields named manufacturer and productName, ChoiceBox named
   * itemType, and Combobox named selectionChooseQuantity.
   */
  public void initialize() {
    populateItemTypeChoiceBox();
    populateChooseQuantityChoiceBox();
    testMultimedia();
    loadProductsToProductLine();
    populateExistingProductsTableView();
  }

  public void populateExistingProductsTableView() {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("product_ID"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_Name"));
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("product_Manufacturer"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("product_Type"));
    productLineTable.setItems(productLine);
  }

  public void populateItemTypeChoiceBox() {
    itemType.getItems().addAll("Audio", "Visual", "AudioMobile", "VisualMobile");
  }

  public void populateChooseQuantityChoiceBox() {
    ObservableList<Integer> numbers =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    selectionChooseQuantity.getItems().addAll(numbers);
    selectionChooseQuantity.getSelectionModel().selectFirst();
    selectionChooseQuantity.setEditable(true);
    selectionChooseQuantity.setValue(5);
  }

  public void loadProductsToProductLine() {
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql = "SELECT id, name, type, manufacturer FROM PRODUCT;";
      System.out.println(sql);

      productLine.clear();
      chooseProductListView.getItems().clear();

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String localVariableForProductID = rs.getString(1);
        String localVariableForProductName = rs.getString(2);
        String localVariableForProductManufacturer = rs.getString(3);
        String localVariableForProductType = rs.getString(4);
        NewProduct formdbProduct =
            new NewProduct(
                localVariableForProductID,
                localVariableForProductName,
                localVariableForProductManufacturer,
                localVariableForProductType);
        productLine.add(formdbProduct);
        chooseProductListView
            .getItems()
            .add(
                localVariableForProductID
                    + "\t"
                    + localVariableForProductName
                    + "\t"
                    + localVariableForProductManufacturer
                    + "\t"
                    + localVariableForProductType);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /** This is a method that tests the class Multimedia. */
  private static void testMultimedia() {
    AudioPlayer newAudioProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct =
        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
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

    // populate the Table view when the addProduct button is clicked

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
    loadProductsToProductLine();
  }

  /** In the future this method will show production records. */
  public void recordProduction() {

    System.out.println("This is the Record Production!");
  }
  /** Reads the selected row from the ListView */
  public void listViewProduct(MouseEvent mouseEvent) {
    System.out.println(chooseProductListView.getSelectionModel().getSelectedItems());
    infoFromListView = chooseProductListView.getSelectionModel().getSelectedItems().toString();
  }
}
