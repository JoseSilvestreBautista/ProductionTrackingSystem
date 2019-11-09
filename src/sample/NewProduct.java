package sample;

/**
 * Describes the basic info of a product: name, manufacturer, and Item type and places it into the
 * table view.
 *
 * @author Jose Silvestre-Bautista *
 */
public class NewProduct {
  /** Holds the product name. */
  private String product_Name;
  /** Holds the product manufacturer name. */
  private String product_Manufacturer;
  /** Holds the product item type. */
  private String product_Type;

  private String product_ID;

  NewProduct(
      String product_ID, String product_Name, String product_Manufacturer, String product_Type) {
    this.product_ID = product_ID;
    this.product_Name = product_Name;
    this.product_Manufacturer = product_Manufacturer;
    this.product_Type = product_Type;
  }

  public String getProduct_ID() {
    return product_ID;
  }

  public void setProduct_ID(String product_ID) {
    this.product_ID = product_ID;
  }

  public String getProduct_Name() {
    return product_Name;
  }

  public void setProduct_Name(String product_Name) {
    this.product_Name = product_Name;
  }

  public String getProduct_Manufacturer() {
    return product_Manufacturer;
  }

  public void setProduct_Manufacturer(String product_Manufacturer) {
    this.product_Manufacturer = product_Manufacturer;
  }

  public String getProduct_Type() {
    return product_Type;
  }

  public void setProduct_Type(String product_Type) {
    this.product_Type = product_Type;
  }
}
