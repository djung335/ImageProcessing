package src.model.imagerep;


/**
 * A class to represent an image pixel.
 */
public class PixelImpl implements Pixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Creates a Pixel object with a red, green, and blue value.
   *
   * @param red   an integer representing the red color value.
   * @param green an integer representing the green color value.
   * @param blue  an integer representing the blue color value.
   * @throws IllegalArgumentException if any of the red, green, or blue values is less than zero or
   *                                  greater than 255.
   */
  public PixelImpl(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("red, green, or blue value is invalid");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Gets the red color value of this Pixel.
   *
   * @return the red color value.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green color value of this Pixel.
   *
   * @return the green color value.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue color value of this Pixel.
   *
   * @return the blue color value.
   */
  public int getBlue() {
    return this.blue;
  }


}
