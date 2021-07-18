package src.controller.imageio;

import java.io.IOException;

/**
 * Interface to represent a ImageConverter.
 *
 * @param <T> the type of image.
 */
public interface ImageConverter<T> {

  /**
   * Writes image to this destination.
   *
   * @throws IllegalArgumentException if supplied arguments are invalid
   * @throws IOException              if there is an error writing to the destination
   */
  void writeImage(T image, String dest, String fileExtension)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns image from this source in format of type T.
   *
   * @return image
   * @throws IllegalArgumentException if supplied arguments are invalid
   * @throws IOException              if there is an error reading from source
   */
  T readImage(String src) throws IllegalArgumentException, IllegalStateException;
}
