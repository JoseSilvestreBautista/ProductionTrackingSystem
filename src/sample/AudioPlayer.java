package sample;

/**
 * Sets and describes the multimedia accepted
 *
 * @author Jose Silvestre-Bautista
 */
public class AudioPlayer extends Product implements MultimediaControl {

  /** Describes the supported audio formats. */
  private final String audioSpecification;
  /** Describes the supported playlist formats. */
  private final String mediaType;

  AudioPlayer(String name, String manufacturer, String audioSpecification, String mediaType) {
    super(name, manufacturer, "AUDIO");
    this.audioSpecification = audioSpecification;
    this.mediaType = mediaType;
  }

  /**
   * This formulates details of the media device into a string.
   *
   * @return audioSpecification, mediaType, super.toString()
   */
  public String toString() {
    return super.toString()
        + "\n"
        + "Supported Audio Formats: "
        + audioSpecification
        + "\n"
        + "Supported Playlist Formats: "
        + mediaType;
  }

  /** @return */
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
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }
}
