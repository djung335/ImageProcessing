package src.controller.imageeditorcommands;

import src.controller.imageio.LayeredImageFolderConverter;
import src.controller.imageio.PpmImageFileConverter;
import src.controller.imageio.StandardImageFileConverter;
import src.model.LayeredImageEditorModel;

/**
 * Runnable to write images/layered images to a destination.
 */
public class WriteImage implements Runnable {

  private final String sourcePath;
  private final LayeredImageEditorModel model;

  /**
   * Constructs a WriteIamage object.
   *
   * @param sourcePath file path to write to
   * @param model      model to retrieve image from
   */
  public WriteImage(String sourcePath, LayeredImageEditorModel model) {
    if (sourcePath == null || model == null) {
      throw new IllegalArgumentException("arguments passed into WriteImage runnable are null");
    }

    this.sourcePath = sourcePath;
    this.model = model;
  }

  @Override
  public void run() {
    if (sourcePath.endsWith(".layers")) {
      new LayeredImageFolderConverter().writeImage(model.getLayeredImage(), sourcePath, "layers");
    } else if (sourcePath.endsWith(".png")) {
      new StandardImageFileConverter()
          .writeImage(model.getTopmostVisibleImage(), sourcePath, "png");
    } else if (sourcePath.endsWith(".ppm")) {
      new PpmImageFileConverter().writeImage(model.getTopmostVisibleImage(), sourcePath, "ppm");
    } else if (sourcePath.endsWith(".jpeg")) {
      new StandardImageFileConverter()
          .writeImage(model.getTopmostVisibleImage(), sourcePath, "jpeg");
    } else {
      throw new IllegalStateException("cannot write to this file/folder type");
    }
  }

}
