package sample;

import java.util.Date;

/**
 * Creates a serial number and id for products once created.
 *
 * @author Jose Silvestre
 */
public class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNum;
  private Date dateProduced;

  public ProductionRecord(int productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNum = "0";
    this.dateProduced = new Date();
  }

  public ProductionRecord(
      int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productID = productID;
    this.productionNumber = productionNumber;
    this.serialNum = serialNumber;
    this.dateProduced = dateProduced;
  }

  // constructor for serial number
  public ProductionRecord(Product productProduced, int count) {
    String intFormat = String.format("%010d", count);
    this.serialNum =
        productProduced.manufacturer.substring(0, 3) + productProduced.type + intFormat;
  }

  public String printSerialNumber() {
    return serialNum;
  }

  public String toString() {

    return "Prod. Num: "
        + productionNumber
        + " Product ID: "
        + productID
        + " Serial Num: "
        + serialNum
        + " Date: "
        + dateProduced;
  }

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNum = serialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }
}
