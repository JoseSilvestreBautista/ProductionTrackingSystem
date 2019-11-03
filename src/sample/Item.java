package sample;

/**
 * This allows the variables to become private.
 *
 * @author Jose Silvestre-Bautista
 */
public interface Item {
  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
