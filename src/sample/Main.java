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

  /**
   * This method sets the display for written code in the other files.
   *
   * @param primaryStage Sets the size of display and shows the display.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setScene(new Scene(root, 600, 600));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
