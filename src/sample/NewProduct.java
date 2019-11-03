package sample;

class NewProduct {

  private String product_Name;
  private String product_Manufacturer;
  private String product_Type;

  NewProduct(String product_Name, String product_Manufacturer, String product_Type) {
    this.product_Name = product_Name;
    this.product_Manufacturer = product_Manufacturer;
    this.product_Type = product_Type;
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
