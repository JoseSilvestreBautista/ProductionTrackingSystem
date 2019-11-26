package sample;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This process the login for the employee.
 *
 * @author Jose Silvestre-Bautista
 */
public class LoginController {

  /** The text field where the user enters their username. */
  @FXML TextField UsernameBox;
  /** The text field where the user enters their password. */
  @FXML TextField PasswordBox;
  /** Tells what type of database is being used. */
  private final String JDBC_DRIVER = "org.h2.Driver";
  /** Database location */
  private final String DB_URL = "jdbc:h2:./res/ProductionTables";
  //  Database credentials
  private final String USER = "";
  private final String PASS = "dbpw";
  private Connection conn;
  private Statement stmt;
  /** Holds the username entered from TextField. */
  private String usernameFromTextBox;
  /** Holds the password from the TextBox. */
  private String passwordFromTextBox;
  /** Hold all user names in the data base. */
  private ArrayList<String> usernameList = new ArrayList<>();
  /** Holds the password associated with username entered by the user. */
  private ArrayList<String> passwordList = new ArrayList<>();

  /** Fills the username array list. */
  public void initialize() {
    getUsernameFromTheDataBase();
  }

  /**
   * Check of the username and password entered match an existing account and decides whether to
   * allow the user to enter.
   *
   * @param event When user clicks the sign in button.
   * @throws IOException Unsure of what it does at the moment.
   */
  public void LoginPressed(ActionEvent event) throws IOException {

    Alert emptyTextBox = new Alert(AlertType.ERROR);
    Alert incorrectCredentials = new Alert(AlertType.ERROR);
    usernameFromTextBox = UsernameBox.getText();
    passwordFromTextBox = PasswordBox.getText();

    if (usernameFromTextBox.isEmpty() || passwordFromTextBox.isEmpty()) {
      emptyTextBox.show();
      emptyTextBox.setContentText("Password or Username is empty. ");
    }

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      // STEP 3: Execute a query
      stmt = conn.createStatement();
      String sql =
          "SELECT LOGINPASSWORD from USERINFO WHERE LOGINUSERNAME ='" + usernameFromTextBox + "'";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        passwordList.add(rs.getString(1));
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    boolean compareUserCredentials = false;
    if (usernameList.contains(usernameFromTextBox) && passwordList.contains(passwordFromTextBox)) {
      compareUserCredentials = true;
    } else {
      incorrectCredentials.show();
      incorrectCredentials.setContentText("The Username or password is incorrect.");
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

  /** Retrieves the password associated with username entered. */
  public void getUsernameFromTheDataBase() {
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      // STEP 3: Execute a query
      stmt = conn.createStatement();
      // INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );
      String sql = "SELECT LOGINUSERNAME FROM USERINFO";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        usernameList.add(rs.getString(1));
      }
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
