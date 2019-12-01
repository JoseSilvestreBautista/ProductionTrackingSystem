package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Jose Silvestre-Bautista
 *     <p>This will include all calls to the databse for the sake of grouping.
 */
class DataBaseExecutions {
  // database info
  private final String USER = "";
  private String PASS = "";
  private Connection conn;
  private Statement stmt;
  private final String JDBC_DRIVER = "org.h2.Driver";
  private final String DB_URL = "jdbc:h2:./res/ProductionTables";

  /**
   * The method takes the new product info from the user and process and entry to the database.
   *
   * @param product The new product info the user enters is being passed to the database
   */
  public DataBaseExecutions(Product product) {
    productsBeingAdded(product);
  }

  public void productsBeingAdded(Product product) {

    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream("res/properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PASS = prop.getProperty("password");

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
              + product.getType()
              + " ', '"
              + product.getManufacturer()
              + "', '"
              + product.getName()
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

  //  public void retrieveFromAllProductInfo() {
  //
  //    Controller objectToFillListView = new Controller();
  //    Properties prop = new Properties();
  //    try {
  //      prop.load(new FileInputStream("res/properties"));
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //    PASS = prop.getProperty("password");
  //
  //    try {
  //      // STEP 1: Register JDBC driver
  //      Class.forName(JDBC_DRIVER);
  //
  //      // STEP 2: Open a connection
  //      conn = DriverManager.getConnection(DB_URL, USER, PASS);
  //
  //      // STEP 3: Execute a query
  //      stmt = conn.createStatement();
  //      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
  //      String sql = "SELECT id, name, type, manufacturer FROM PRODUCT";
  //      System.out.println(sql);
  //
  //      Controller clearLists = new Controller();
  //
  //      clearLists.getProductLine().clear();
  //      clearLists.getChooseProductListView().getItems().clear();
  //
  //      ResultSet rs = stmt.executeQuery(sql);
  //      while (rs.next()) {
  ////        allProducts.clear();
  //        String id = rs.getString(1);
  //        String name =  rs.getString(2);
  //        String type = rs.getString(3);
  //        String manufacturer =rs.getString(4);
  //
  //        ItemType twoLetterTypeCode = null;
  //        switch (type) {
  //          case ("Audio"):
  //            twoLetterTypeCode = ItemType.AUDIO;
  //            break;
  //          case ("Visual"):
  //            twoLetterTypeCode = ItemType.VISUAL;
  //            break;
  //          case ("AudioMobile"):
  //            twoLetterTypeCode = ItemType.AUDIO_MOBILE;
  //            break;
  //          case ("VisualMobile"):
  //            twoLetterTypeCode = ItemType.VISUAL_MOBILE;
  //            break;
  //          default:
  //            System.out.println("Something freaky happened in the loadProductionLog method.");
  //        }
  //
  //        Product productUsedForTableView = new Product(Integer.parseInt(id), name,
  // manufacturer,twoLetterTypeCode ){};
  //        ObservableList<String> products =
  //            FXCollections.observableArrayList(id, name, manufacturer, type);
  //
  //        objectToFillListView.setProductLine;
  //        objectToFillListView.setChooseProductListView(products);
  //      }
  //
  //      // STEP 4: Clean-up environment
  //      stmt.close();
  //      conn.close();
  //    } catch (ClassNotFoundException | SQLException e) {
  //      e.printStackTrace();
  //    }
  //  }
}
