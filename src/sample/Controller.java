package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * @author Jose Silvestre-Bautista
 *     <p>This controls the process of creating products and storing the info into a databse.
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
  /** Connects the TextArea with its java code. */
  @FXML private TextArea ProductionLogTextArea;
  /** Displays all products available */
  private final ObservableList<NewProduct> productLine = FXCollections.observableArrayList();
  /** Holds all the products in the database. */
  private final ArrayList<String> allProducts = new ArrayList<>();
  /** The TextField where the user enters their full name. */
  @FXML private TextField EmployeeName;
  /** The TextField where the user enters their password. */
  @FXML private PasswordField EmployeePassword;
  /** Database Connection */
  private final String JDBC_DRIVER = "org.h2.Driver";
  /** Database location */
  private final String DB_URL = "jdbc:h2:./res/ProductionTables";
  //  Database credentials
  private final String USER = "";
  private String PASS = "";
  private Connection conn;
  private Statement stmt;
  /** An object of Product that used to create serial numbers */
  private Product produceProduct;

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

  /** Populate the ComboBox that holds all possible item types for a product. */
  private void populateItemTypeChoiceBox() {
    itemType
        .getItems()
        .addAll(
            "Audio",
            "Visual",
            "AudioMobile",
            "VisualMobile",
            MonitorType.LCD.toString(),
            MonitorType.LED.toString());
  }

  /** Populates the choice box in in produce with a set of positive integers. */
  private void populateChooseQuantityChoiceBox() {
    ObservableList<Integer> numbers =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    selectionChooseQuantity.getItems().addAll(numbers);
    selectionChooseQuantity.getSelectionModel().selectFirst();
    selectionChooseQuantity.setEditable(true);
    selectionChooseQuantity.setValue(0);
  }

  /**
   * This method receives the input from productName, manufacturer, and itemType then sets them
   * equal to String variables. The string variables are used in SQL command to store user input
   * into a database.
   */
  public void addProduct() {

    Product produceProduct =
        new Product(productName.getText(), manufacturer.getText(), itemType.getValue()) {};

    DataBaseExecutions newProduct = new DataBaseExecutions(produceProduct);
    loadProductsToProductLine();
  }

  /**
   * Loads all products in the database into the TableView. It also loads all possible products to
   * chose from in the ListView.
   */
  private void loadProductsToProductLine() {

    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream("res/properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PASS = prop.getProperty("password");

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql = "SELECT id, name, type, manufacturer FROM PRODUCT";
      System.out.println(sql);

      productLine.clear();
      chooseProductListView.getItems().clear();

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        allProducts.clear();
        allProducts.add(rs.getString(1));
        allProducts.add(rs.getString(2));
        allProducts.add(rs.getString(3));
        allProducts.add(rs.getString(4));

        NewProduct productUsedForTableView =
            new NewProduct(
                allProducts.get(0), allProducts.get(1), allProducts.get(2), allProducts.get(3));
        productLine.add(productUsedForTableView);
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
   * Fills the TextArea in the production log tab with serial numbers from the database. It is also
   * capable of refreshing while the program runs.
   */
  private void populateProductionLog() {

    ProductionLogTextArea.setText("Production Log :\n");

    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream("res/properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PASS = prop.getProperty("password");

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      // STEP 3: Execute a query
      stmt = conn.createStatement();
      String sql =
          "SELECT PRODUCTION_NUM, PRODUCT_ID,SERIAL_NUM, DATE_PRODUCED FROM PRODUCTIONRECORD";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        int localProductionNum = Integer.parseInt(rs.getString(1));
        int localProductId = Integer.parseInt(rs.getString(2));
        String localSerialNum = rs.getString(3);
        String localDateProduced = rs.getString(4);
        ProductionRecord productionRecordToProductionLog =
            new ProductionRecord(
                localProductionNum, localProductId, localSerialNum, localDateProduced);
        ProductionLogTextArea.appendText(productionRecordToProductionLog + "\n");
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /** Sets the columns of the TableView to receive certain NewProduct fields. */
  private void populateExistingProductsTableView() {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("product_ID"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_Name"));
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("product_Manufacturer"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("product_Type"));
    productLineTable.setItems(productLine);
  }

  /**
   * Reads the selected row from the ListView and slits product id, product name, manufacturer, and
   * item type. Then saves it into an array.
   */
  public void listViewProduct(MouseEvent mouseEvent) {
    Alert loginCredentials = new Alert(AlertType.WARNING);
    loginCredentials.setTitle("Selection");
    loginCredentials.setHeaderText("Improper Selection");
    loginCredentials.setContentText("Select a non-empty row.");

    if (chooseProductListView.getSelectionModel().isEmpty()) {
      loginCredentials.show();

    } else {

      String infoFromListView =
          chooseProductListView.getSelectionModel().getSelectedItems().toString();

      productInfoFromListView = infoFromListView.split("\\t");
      createProductionObject();
    }
  }

  /** Creates an object of Product and fills it. The object is used to make serial numbers. */
  private void createProductionObject() {

    produceProduct =
        new Product(
            productInfoFromListView[1].trim(),
            productInfoFromListView[3].replace("]", "").trim(),
            productInfoFromListView[2].trim()) {};
  }

  /**
   * This function use the object produceProduct to produce products. This means creating serial
   * numbers and recording the creation time and product amount. Lastly, all the info is stored into
   * the database.
   */
  public void recordProduction() {
    int localSerialNum = 0;
    int prodId = 0;
    try {
      prodId = Integer.parseInt(productInfoFromListView[0].replace("[", "").trim());
    } catch (RuntimeException e) {
      Alert SubmitError = new Alert(AlertType.ERROR);
      SubmitError.setTitle("Submission Error");
      SubmitError.setHeaderText("Submission Error");
      SubmitError.setContentText("Select a non-empty row before submitting.");
      SubmitError.show();
    }
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream("res/properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PASS = prop.getProperty("password");

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();

      String sql = "SELECT SERIAL_NUM from PRODUCTIONRECORD WHERE PRODUCT_ID=' " + prodId + "'";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        localSerialNum = Integer.parseInt(rs.getString(1).substring(5, 15));
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    int simplified = 0;
    try {
      simplified =
          Integer.parseInt(
              String.valueOf(selectionChooseQuantity.getSelectionModel().getSelectedItem()));
      if (simplified < 0) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      Alert completionError = new Alert(AlertType.ERROR);
      completionError.setHeaderText("Incorrect input for choose quantity.");
      completionError.setContentText("Please enter an integer\n" + "ex. 1,2,3,...");
      completionError.show();
    } catch (Exception e) {
      Alert completionError = new Alert(AlertType.ERROR);
      completionError.setHeaderText("Incorrect input for choose quantity.");
      completionError.setContentText("Please enter an integer\n" + "ex. 1,2,3,...");
      completionError.show();
    }

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
      Alert completion = new Alert(AlertType.INFORMATION);
    }
    if (simplified > 0) {
      Alert completion = new Alert(AlertType.INFORMATION);
      completion.setHeaderText("Complete!");
      completion.setContentText("Items Were Produced");
      completion.show();
    }

    populateProductionLog();
  }

  public void CreateAccountPressed(ActionEvent event) {
    Alert loginCredentials = new Alert(AlertType.INFORMATION);
    String name = EmployeeName.getText();
    String password = EmployeePassword.getText();
    Employee newEmployeesCredentials = new Employee(name, password);
    loginCredentials.setTitle("Credentials");
    loginCredentials.setHeaderText("Your Login Credentials");
    loginCredentials.setContentText(newEmployeesCredentials.toString());
    loginCredentials.show();

    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream("res/properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PASS = prop.getProperty("password");

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      Timestamp time = new Timestamp(new Date().getTime());

      String sql =
          "INSERT INTO USERINFO(fullName,companyEmail, loginUsername, loginPassword ) VALUES ('"
              + newEmployeesCredentials.getName()
              + "', '"
              + newEmployeesCredentials.getEmail()
              + "', '"
              + newEmployeesCredentials.getUsername()
              + "', '"
              + newEmployeesCredentials.getPassword()
              + "')";
      stmt.executeUpdate(sql);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  //   public ObservableList<Product> getProductLine() {
  //    return productLine;
  //  }
  //
  //  public void setProductLine(ObservableList<Product> productLine){
  //    this.productLine = productLine;
  //  }
  //
  //   public ListView getChooseProductListView() {
  //    return chooseProductListView;
  //  }
  //
  //  public void setChooseProductListView(ListView<String> chooseProductListView) {
  //    this.chooseProductListView = chooseProductListView;
  //  }
}
