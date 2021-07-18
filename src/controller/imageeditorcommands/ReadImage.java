package src.controller.imageeditorcommands;

import src.controller.imageio.LayeredImageFolderConverter;
import src.controller.imageio.PpmImageFileConverter;
import src.controller.imageio.StandardImageFileConverter;
import src.model.LayeredImageEditorModel;

/**
 * Runnable to read images or layered images into image editor.
 */
public class ReadImage implements Runnable {

  private final String sourcePath;
  private final LayeredImageEditorModel model;

  /**
   * Constructs a ReadImage object.
   *
   * @param sourcePath path to read image from
   * @param model      image editor model to add image to
   */
  public ReadImage(String sourcePath, LayeredImageEditorModel model) {
    if (sourcePath == null || model == null) {
      throw new IllegalArgumentException("arguments passed into ReadImage runnable are null");
    }

    this.sourcePath = sourcePath;
    this.model = model;
  }

  @Override
  public void run() {
    if (sourcePath.endsWith(".layers")) {
      model.setLayers(new LayeredImageFolderConverter().readImage(sourcePath));
    } else if (sourcePath.endsWith(".png") || sourcePath.endsWith(".jpeg")) {
      model.setImage(new StandardImageFileConverter().readImage(sourcePath));
    } else if (sourcePath.endsWith(".ppm")) {
      model.setImage(new PpmImageFileConverter().readImage(sourcePath));
    } else {
      throw new IllegalStateException("image type not supported");
    }
  }

}
