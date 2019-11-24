package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
  @FXML private TextArea ProductionLogTextArea;
  /** Displays all products available */
  private ObservableList<NewProduct> productLine = FXCollections.observableArrayList();
  private ArrayList<String> allProducts = new ArrayList<>();
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
  private String infoFromListView;

  Product produceProduct;
  private String[] productInfoFromListView;
  /**
   * This method initializes text in TextFields named manufacturer and productName, ChoiceBox named
   * itemType, and Combobox named selectionChooseQuantity.
   */
  public void initialize() {
    populateItemTypeChoiceBox();
    populateChooseQuantityChoiceBox();
    loadProductsToProductLine();
    populateExistingProductsTableView();
    populateProductionLog();
  }

  public void populateProductionLog() {

    ProductionLogTextArea.setText("Production Log :\n");
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql = "SELECT PRODUCTION_NUM, PRODUCT_ID,SERIAL_NUM, DATE_PRODUCED FROM PRODUCTIONRECORD;";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String localProductionNum = rs.getString(1);
        String localProductId = rs.getString(2);
        String localSerialNum = rs.getString(3);
        String localDateProduced = rs.getString(4);
        ProductionLogTextArea.appendText("Prod. Num: "
            + localProductionNum
            + " Product ID: "
            + localProductId
            + " Serial Num: "
            + localSerialNum
            + " Date: "
            + localDateProduced+"\n");
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

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
    ObservableList<Integer> numbers = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7);
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
        allProducts.clear();
//        String localVariableForProductID = rs.getString(1);
//        String localVariableForProductName = rs.getString(2);
//        String localVariableForProductManufacturer = rs.getString(3);
//        String localVariableForProductType = rs.getString(4);
        allProducts.add(rs.getString(1));
        allProducts.add(rs.getString(2));
        allProducts.add(rs.getString(3));
        allProducts.add(rs.getString(4));

        NewProduct formdbProduct =
            new NewProduct(
                allProducts.get(0),
                allProducts.get(1),
                allProducts.get(2),
                allProducts.get(3));
        productLine.add(formdbProduct);
        chooseProductListView
            .getItems()
            .add(
                allProducts.get(0)
                    + "\t"
                    + allProducts.get(1)
                    + "\t"
                    + allProducts.get(2)
                    + "\t"
                    + allProducts.get(3));
        populateProductionLog();
      }


      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
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

  /** Reads the selected row from the ListView */
  public void listViewProduct(MouseEvent mouseEvent) {
    System.out.println(chooseProductListView.getSelectionModel().getSelectedItems());
    infoFromListView = chooseProductListView.getSelectionModel().getSelectedItems().toString();
    productInfoFromListView = infoFromListView.split("\\t");
    System.out.println(productInfoFromListView[0]);
    System.out.println(productInfoFromListView[1]);
    System.out.println(productInfoFromListView[2]);
    System.out.println(productInfoFromListView[3]);
    loadProductionLog();
  }

  public void loadProductionLog() {
    //    itemType.getItems().addAll("Audio", "Visual", "AudioMobile", "VisualMobile");
    String twoLetterTypeCode = null;

    switch (productInfoFromListView[2].trim()) {
      case ("Audio"):
        twoLetterTypeCode = "AU";
        break;
      case ("Visual"):
        twoLetterTypeCode = "VI";
        break;
      case ("AudioMobile"):
        twoLetterTypeCode = "AM";
        break;
      case ("VisualMobile"):
        twoLetterTypeCode = "VM";
        break;
      default:
        System.out.println("Something freaky happened in the loadProductionLog method.");
    }

    produceProduct =
        new Product(
            productInfoFromListView[1].trim(),
            productInfoFromListView[3].replace("]", "").trim(),
            twoLetterTypeCode) {
          @Override
          public int getId() {
            return super.getId();
          }

          @Override
          public void setName(String name) {
            super.setName(name);
          }

          @Override
          public String getName() {
            return super.getName();
          }

          @Override
          public void setManufacturer(String manufacturer) {
            super.setManufacturer(manufacturer);
          }

          @Override
          public String getManufacturer() {
            return super.getManufacturer();
          }

          @Override
          public String toString() {
            return super.toString();
          }
        };
  }

  /** In the future this method will show production records. */
  public void recordProduction() {

    int localSerialNum = 0;
    int prodId = Integer.parseInt(productInfoFromListView[0].replace("[", "").trim());

    //    System.out.println(prodId);
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();

      String sql = "SELECT SERIAL_NUM from PRODUCTIONRECORD WHERE PRODUCT_ID=' " + prodId + "'";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        localSerialNum = Integer.parseInt(rs.getString(1).substring(5, 15));
        //        System.out.println(localSerialNum);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    int simplified =
        Integer.parseInt(
            String.valueOf(selectionChooseQuantity.getSelectionModel().getSelectedItem()));

    for (int i = localSerialNum + 1; i <= simplified + localSerialNum; i++) {
      ProductionRecord createTheSerialNumbersForProduceProduct =
          new ProductionRecord(produceProduct, i);

      try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        Timestamp time = new Timestamp(new Date().getTime());

        String sql =
            "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES ('"
                + productInfoFromListView[0].replace("[", "").trim()
                + "', '"
                + createTheSerialNumbersForProduceProduct.printSerialNumber()
                + "', '"
                + time
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
    populateProductionLog();
  }
}
