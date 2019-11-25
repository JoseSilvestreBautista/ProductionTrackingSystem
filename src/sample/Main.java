package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This project keeps track of products created by employees using a database. The stored products
 * can be used mass produce the same product. Each product created will also receive its own serial
 * code.
 *
 * @author Jose Silvestre-Bautista
 */
public class Main extends Application {

  private static Stage primaryStage;

  /**
   * This method sets the display for written code in the other files.
   *
   * @param primaryStage Sets the size of display and shows the display.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    setPrimaryStage(primaryStage);
    Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
    primaryStage.setScene(new Scene(root, 640, 480));
    primaryStage.show();
  }

  private void setPrimaryStage(Stage stage) {
    Main.primaryStage = stage;
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
