package src.model;

import src.model.imageprocessing.ImageProcessor;
import src.model.imagerep.Image;

/**
 * Model for an image editor application. Implements all functionality required to edit, import and
 * export image files.
 */
public interface ImageEditorModel {

  /**
   * Applies given image processor to image current being edited.
   *
   * @param ip image processor to apply to current image
   * @throws IllegalArgumentException if processor is null or image is null
   */
  void applyImageProcessor(ImageProcessor ip);

  /**
   * Generate an Image with a checkerboard pattern.
   *
   * @param tileSideLength the tile side length.
   * @param numberOfTiles  the number of tiles.
   * @throws IllegalArgumentException if either width or height is less than 1
   */
  Image generateCheckerboard(int tileSideLength, int numberOfTiles);

  /**
   * Retrieves image currently being edited.
   *
   * @return image being edited.
   */
  Image getImage();

  /**
   * Sets the image.
   *
   * @param img the image to be set.
   */
  void setImage(Image img);
}
