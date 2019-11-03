package sample;

public class Screen implements ScreenSpec {

   private final String resolution;
   private final int refreshRate;
   private final int responseTime;

   public Screen(String resolution, int refreshRate, int responseTime){
     this.resolution=resolution;
     this.refreshRate=refreshRate;
     this.responseTime=responseTime;
   }

  @Override
  public String getResolution() {
    return null;
  }

  @Override
  public int getRefreshRate() {
    return 0;
  }

  @Override
  public int getResponseTime() {
    return 0;
  }

  public String toString() {
    return "\n"+"Resolution: "
        + resolution
        + "\n"
        + "Refresh rate: "
        + refreshRate
        + "\n"
        + "Response Time: "
        + responseTime;
  }
}
