package sample;

/**
 * Holds the possible item types a product might be.
 *
 * @author Jose Silvestre-Bautista
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public final String code;

  /**
   * Returns the two letter code of associated to the item type.
   *
   * @param code Two letter code of item type.
   */
  ItemType(String code) {
    this.code = code;
  }
}
