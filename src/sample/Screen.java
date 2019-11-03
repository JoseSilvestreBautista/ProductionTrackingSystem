package sample;

/**
 * Describes the basic info of a screen: refresh rate, resolution, and response rate.
 *
 * @author Jose Silvestre
 */
public class Screen implements ScreenSpec {

  /** Describes the pixel detail of a display. */
  private final String resolution;
  /** The speed a which a displays refreshes usually in hertz. */
  private final int refreshRate;
  /** Screen response to an event. */
  private final int responseTime;

  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
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
    return "\n"
        + "Resolution: "
        + resolution
        + "\n"
        + "Refresh rate: "
        + refreshRate
        + "\n"
        + "Response Time: "
        + responseTime;
  }
}
