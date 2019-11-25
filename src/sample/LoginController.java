package sample;

import static java.util.jar.Pack200.Packer.PASS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

  @FXML TextField UsernameBox;
  @FXML TextField PasswordBox;

  private final String JDBC_DRIVER = "org.h2.Driver";
  /** Database location */
  private final String DB_URL = "jdbc:h2:./res/ProductionTables";
  //  Database credentials
  private final String USER = "";
  private final String PASS = "";
  private Connection conn;
  private Statement stmt;

  private final ArrayList<String> UserInfoFromDataBase = new ArrayList<>();

  public void LoginPressed(ActionEvent event) throws IOException {

    String usernameFromTextBox = UsernameBox.getText();
    String passwordFromTextBox = PasswordBox.getText();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql = "SELECT LOGINUSERNAME, LOGINPASSWORD FROM USERINFO";
      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        UserInfoFromDataBase.add(rs.getString(1));
        UserInfoFromDataBase.add(rs.getString(2));
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    boolean compareUserCredentials = false;
    if (UserInfoFromDataBase.contains(usernameFromTextBox)
        && UserInfoFromDataBase.contains(passwordFromTextBox)) {
      compareUserCredentials = true;
    } else {
      System.out.println("Nothing matches");
    }

    if (compareUserCredentials) {
      Stage stage = Main.getPrimaryStage();
      Parent root = FXMLLoader.load(getClass().getResource("MainProduction.fxml"));
      stage.setScene(new Scene(root, 640, 480));
      stage.show();
    } else {
      System.out.println("Try again lair");
    }
  }
}
