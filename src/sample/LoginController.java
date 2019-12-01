package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
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
  /** The password field where the user enters their password. */
  @FXML PasswordField PasswordBox;
  /** Holds the password associated with username entered by the user. */
  private final ArrayList<String> passwordList = new ArrayList<>();

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
    String usernameFromTextBox = UsernameBox.getText().toLowerCase();
    String passwordFromTextBox = PasswordBox.getText();

    Properties prop = new Properties();
    prop.load(new FileInputStream("res/properties"));
    String PASS = prop.getProperty("password");

    if (usernameFromTextBox.isEmpty() || passwordFromTextBox.isEmpty()) {
      emptyTextBox.show();
      emptyTextBox.setContentText("Password or Username is empty. ");
    }

    try {

      // STEP 1: Register JDBC driver
      String JDBC_DRIVER = "org.h2.Driver";
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      //  Database credentials
      String USER = "";
      String DB_URL = "jdbc:h2:./res/ProductionTables";
      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
      // STEP 3: Execute a query
      Statement stmt = conn.createStatement();
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
    if (passwordList.contains(passwordFromTextBox)) {
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
}
