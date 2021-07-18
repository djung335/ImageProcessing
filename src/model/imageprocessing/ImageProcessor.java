package src.model.imageprocessing;

import src.model.imagerep.Image;

/**
 * This interface represents the ImageProcessor, for which other image processors like filters and
 * transformations can implement.
 */
public interface ImageProcessor {

  /**
   * Applies this image processor to given image.
   *
   * @param image image to apply image processor to
   * @return resulting image after applying image processor
   * @throws IllegalArgumentException if image given is null
   */
  Image processImage(Image image) throws IllegalArgumentException;
}
