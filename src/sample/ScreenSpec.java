package sample;

/**
 * The basic info of screen resolution, response time, and refresh rate.
 *
 * @author Jose Silvestre
 */
public interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
