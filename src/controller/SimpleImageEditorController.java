package src.controller;

import src.controller.imageio.PpmImageFileConverter;
import src.model.imagerep.Image;

/**
 * Controller that allows for reading and writing images.
 */
public class SimpleImageEditorController implements ImageEditorController {

  @Override
  public Image readImageFrom(String src) {
    return new PpmImageFileConverter().readImage(src);
  }

  @Override
  public void writeImageTo(Image img, String dest) {
    new PpmImageFileConverter().writeImage(img, dest, "ppm");
  }
}
