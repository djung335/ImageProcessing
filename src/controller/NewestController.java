package src.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import src.model.LayeredImageEditorModel;
import src.model.imageprocessing.Downsize;
import src.model.imageprocessing.Mosaic;
import src.model.imageprocessing.filter.Blur;
import src.model.imageprocessing.filter.Sharpen;
import src.model.imageprocessing.transformation.Grayscale;
import src.model.imageprocessing.transformation.Sepia;
import src.model.imagerep.Image;
import src.model.imagerep.Pixel;
import src.view.ImageEditorGraphicalView;

/**
 * This class represents the controller that connects the graphical view with the model. It inherits
 * methods from the features interface.
 */
public class NewestController implements Features {

  private final LayeredImageEditorModel model;
  private final ImageEditorGraphicalView view;

  /**
   * Creates an instance of the newest controller, with a given model and view.
   *
   * @param m the layered image editor model.
   * @param v the image editor graphical view.
   * @throws IllegalArgumentException if either parameter is null.
   */
  public NewestController(LayeredImageEditorModel m, ImageEditorGraphicalView v)
      throws IllegalArgumentException {
    if (m == null || v == null) {
      throw new IllegalArgumentException("neither view nor model can be null");
    } else {
      this.model = m;
      view = v;
      view.addFeatures(this);

      refreshView();
    }
  }

  // done
  private void refreshView() {
    try {
      view.setImage(convertToBufferedImage(model.getTopmostVisibleImage()));
      if (model.getCurrentLayerName() == null) {
        view.setCurrentLayerName("no current layer set");
      } else {
        view.setCurrentLayerName("current layer name: " + model.getCurrentLayerName());
      }
      if (model.getTopmostVisibleLayerName() == null) {
        view.setTopmostVisibleLayerName("no topmost visible layer");
      } else {
        view.setTopmostVisibleLayerName(
            "topmost visible layer: " + model.getTopmostVisibleLayerName());
      }
    } catch (IllegalArgumentException e) {
      view.setImage(null);
    }

    ArrayList<String> layerNames = new ArrayList<String>(model.getLayeredImage().keySet());
    view.refreshMenu(layerNames, this);
  }

  // done
  @Override
  public void exitProgram() {
    System.exit(0);
  }

  // done
  @Override
  public void applyBlur() {
    try {
      model.applyImageProcessor(new Blur());
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }

  // done
  @Override
  public void applySharpen() {
    try {
      model.applyImageProcessor(new Sharpen());
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }

  // done
  @Override
  public void applySepia() {
    try {
      model.applyImageProcessor(new Sepia());
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }

  // done
  @Override
  public void applyGrayscale() {
    try {
      model.applyImageProcessor(new Grayscale());
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }


  // done
  @Override
  public void applyDownsize() {
    try {
      model.applyImageProcessor(new Downsize(0.5, 0.5));
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }

  // done
  @Override
  public void applyMosaic() {
    try {
      model.applyImageProcessor(new Mosaic(2000));
      refreshView();
      view.setStatusBarMessage("filter successfully applied");
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("no visible images to apply filter to");
    }
  }

  // done
  @Override
  public void addLayer() {
    model.addLayer(view.getLayerName());
    refreshView();
    view.setStatusBarMessage("layer successfully added");
  }

  @Override
  // done
  public void removeLayer(String name) {
    if (name == null) {
      view.setStatusBarMessage("layer name cannot be null");
    } else {
      try {
        model.removeLayer(name);
        refreshView();
        view.setStatusBarMessage("layer successfully removed");
      } catch (IllegalArgumentException e) {
        view.setStatusBarMessage("no layers have given name");
      }
    }
  }

  @Override
  // done
  public void setCurrentLayer(String name) {
    if (name == null) {
      view.setStatusBarMessage("layer name cannot be null");
    } else {
      try {
        model.setCurrentLayer(name);
        refreshView();
        view.setStatusBarMessage("current layer successfully set");
      } catch (IllegalArgumentException e) {
        view.setStatusBarMessage("no layers have given name");
      }
    }
  }

  @Override
  // done
  public void setLayerVisibility(String name, boolean visible) {
    if (name == null) {
      throw new IllegalArgumentException("layer name cannot be null");
    } else {
      try {
        model.changeVisibility(name, visible);
        refreshView();
        view.setStatusBarMessage("visibility successfully set");
      } catch (IllegalArgumentException e) {
        view.setStatusBarMessage("no layers have given name");
      }
    }
  }

  @Override
  public void saveImage() {
    try {
      try {
        // placeholder name for file; should be topmost visible layer name when done
        new src.controller.imageeditorcommands.WriteImage(
            view.getFilePath(null, null) + "/" + model.getTopmostVisibleLayerName() + ".png", model)
            .run();
        view.setStatusBarMessage("successfully saved image");
      } catch (Exception e) {
        view.setStatusBarMessage("cannot write to given file destination");
      }
    } catch (IllegalArgumentException e) {
      view.setStatusBarMessage("image has no visible layers");
    }
  }

  @Override
  public void saveLayers(String filePath) {
    if (filePath == null) {
      view.setStatusBarMessage("file path cannot be null");
    } else {
      try {
        new src.controller.imageeditorcommands.WriteImage(filePath, model).run();
        view.setStatusBarMessage("successfully saved layers");
      } catch (Exception e) {
        view.setStatusBarMessage("cannot write to given file destination");
      }
    }
  }

  @Override
  public void loadImage() {
    if (model.getCurrentLayerName() == null) {
      view.setStatusBarMessage("current layer not yet set");
    } else {
      try {
        new src.controller.imageeditorcommands.ReadImage(
            view.getFilePath("Jpeg, Png, Ppm images", "jpeg", "png", "ppm"), model).run();
        refreshView();
        view.setStatusBarMessage("successfully loaded image");
      } catch (IllegalArgumentException e) {
        view.setStatusBarMessage("current layer not yet set");
      } catch (IllegalStateException e1) {
        view.setStatusBarMessage("error reading image");
      }
    }
  }

  @Override
  public void loadLayers(String filePath) {
    if (filePath == null) {
      view.setStatusBarMessage("file path cannot be null");
    } else {
      try {
        new src.controller.imageeditorcommands.ReadImage(filePath, model).run();
        refreshView();
        view.setStatusBarMessage("successfully loaded layers");
      } catch (IllegalStateException e) {
        view.setStatusBarMessage("failed to load layers");
      }
    }
  }

  @Override
  public void loadAndExecuteScript(String filePath) {
    try {
      new InteractiveLayeredImageEditorController(model, new StringReader(
          Files.readString(Path.of(filePath), StandardCharsets.US_ASCII)),
          System.out).startProgram();
      refreshView();
      view.setStatusBarMessage("successfully loaded and executed script");
    } catch (Exception e) {
      view.setStatusBarMessage("error reading script from given file");
    }
  }

  private BufferedImage convertToBufferedImage(Image image) {
    if (image == null) {
      return null;
    }

    List<List<Pixel>> pixelsToWrite = image.getPixels();
    BufferedImage img = new BufferedImage(pixelsToWrite.get(0).size(), pixelsToWrite.size(),
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < pixelsToWrite.size(); i++) {
      for (int j = 0; j < pixelsToWrite.get(0).size(); j++) {
        Pixel current = pixelsToWrite.get(i).get(j);
        Color c = new Color(current.getRed(), current.getGreen(), current.getBlue());
        img.setRGB(j, i, c.getRGB());
      }
    }

    return img;
  }
}
