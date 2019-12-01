package sample;

/**
 * The basic skeleton of product info: name, item type, and manufacturer
 *
 * @author Jose Silvestre-Bautista
 */
public abstract class Product implements Item {

  private int id;
  private final String type;
  private final String manufacturer;
  private final String name;

  Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public void setName(String name) {}

  public String getName() {
    return name;
  }

  @Override
  public void setManufacturer(String manufacturer) {}

  public String getManufacturer() {
    return manufacturer;
  }

  public String getType() {
    return type;
  }
}
