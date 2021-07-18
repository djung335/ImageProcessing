package src.model.imagerep;

import java.util.List;

/**
 * An interface to represent an Image. It holds methods relevant to image processes.
 */
public interface Image {


  /**
   * Returns the image's pixels.
   *
   * @return the image's pixels as a list of lists.
   */
  List<List<Pixel>> getPixels();
}
