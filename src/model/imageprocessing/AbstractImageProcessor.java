package src.model.imageprocessing;

import src.model.imagerep.Image;

/**
 * This abstract class represents the AbstractImageProcessor, which implements methods from the
 * ImageProcessor interface as well as introducing and implementing helpful methods for image
 * processors to use.
 */
public abstract class AbstractImageProcessor implements ImageProcessor {

  /**
   * Clamps a color value, represented as an integer from 0 to 255.
   *
   * @param value a double representing the color value.
   * @return the clamped color value.
   */
  protected int clamp(double value) {
    if (value > 255) {
      return 255;
    } else if (value < 0) {
      return 0;
    } else {
      return (int) Math.round(value);
    }
  }

  @Override
  public abstract Image processImage(Image image) throws IllegalArgumentException;
}
