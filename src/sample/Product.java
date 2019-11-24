package sample;

/**
 * The basic skeleton of product info: name, item type, and manufacturer
 *
 * @author Jose Silvestre-Bautista
 */
public abstract class Product implements Item {

  private int id;
  final String type;
  String manufacturer;
  final String name;

  Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {}

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }
}
