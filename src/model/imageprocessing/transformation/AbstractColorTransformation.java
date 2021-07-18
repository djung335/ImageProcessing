package src.model.imageprocessing.transformation;

import src.model.imageprocessing.AbstractImageProcessor;
import src.model.imagerep.Image;

/**
 * A color transformation that applies a transformation matrix to the RGB value of each pixel in an
 * image.
 */
public abstract class AbstractColorTransformation extends AbstractImageProcessor {

  @Override
  public abstract Image processImage(Image image) throws IllegalArgumentException;
}
