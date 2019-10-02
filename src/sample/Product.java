package sample;

public abstract class Product implements Item {

  int id;
  String type;
  String manufacturer;
  String name;

  Product(String name, String manufacturer, String type){
    this.name=name;
    this.manufacturer=manufacturer;
    this.type=type;
  }

//  Product(String manufacturer){
//    this.manufacturer=manufacturer;
//  }

  public String toString(){
    return "Name: "+name+"\n"+"Manufacturer: "+manufacturer+"\n"+"Type: "+type;
  }



}

