package sample;

public class AudioPlayer extends Product implements MultimediaControl {

  String audioSpecification;
  String mediaType;

  AudioPlayer(
      String name, String manufacturer, String audioSpecification, String mediaType) {
    super(name, manufacturer, "AUDIO");
    this.audioSpecification = audioSpecification;
    this.mediaType = mediaType;
  }

  public String toString() {
    return super.toString()
        + "\n"
        + "Supported Audio Formats: "
        + audioSpecification
        + "\n"
        + "Supported Playlist Formats: "
        + mediaType;
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
