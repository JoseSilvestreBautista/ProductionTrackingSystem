package sample;

/**
 * This is class to test the creation of serial codes for products.
 *
 * @author Jose Silvestre
 */
public class Widget extends Product {

  Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, type);
  }

  @Override
  public int getId() {
    return 0;
  }

  @Override
  public void setName(String name) {}

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setManufacturer(String manufacturer) {}

  @Override
  public String getManufacturer() {
    return manufacturer;
  }
}
