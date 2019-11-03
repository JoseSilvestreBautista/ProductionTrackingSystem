package sample;

public class MoviePlayer extends Product implements MultimediaControl {

  private final Screen screen;
  private final MonitorType monitorType;

  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  public String toString() {
    return super.toString() + "\n" + "screen:  " + screen + "\n" + "monitor type: " + monitorType;
  }

  @Override
  public int getId() {
    return 0;
  }

  @Override
  public void setName(String name) {}

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void setManufacturer(String manufacturer) {}

  @Override
  public String getManufacturer() {
    return null;
  }

  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  @Override
  public void next() {
    System.out.println("Next movie");
  }
}
