package src.controller;

import src.model.imagerep.Image;

/**
 * Basic controller than only allows for read and writing images.
 */
public interface ImageEditorController {

  /**
   * Returns image read from given input source.
   *
   * @param inp input source to read from
   * @return image read from input source
   * @throws IAE if there is an error reading from the input source
   */
  Image readImageFrom(String inp);

  /**
   * Writes given image to given destination.
   *
   * @param img image to write
   * @param out destinatio to write image to
   * @throws IAE if either argument is null
   * @throws ISE if there is an error writing to the output destination
   */
  void writeImageTo(Image img, String out);
}
