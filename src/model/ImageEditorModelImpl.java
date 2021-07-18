package src.model;

import java.util.ArrayList;
import java.util.List;
import src.model.imageprocessing.ImageProcessor;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * An image editor model that allows for imports, exports and creating checkerboard patterned
 * images.
 */
public class ImageEditorModelImpl implements ImageEditorModel {

  protected Image image;

  /**
   * Constructs a SimpleImageEditorModel.
   */
  public ImageEditorModelImpl() {
    image = generateCheckerboard(2, 4);
  }

  @Override
  public void applyImageProcessor(ImageProcessor ip) throws IllegalArgumentException {
    if (ip == null) {
      throw new IllegalArgumentException("processor is null");
    }

    image = ip.processImage(image);
  }

  @Override
  public Image generateCheckerboard(int tileSideLength, int numberOfTiles) {
    List<List<Pixel>> pixels = new ArrayList<List<Pixel>>();
    boolean blackFirst = true;
    if (tileSideLength < 1) {
      throw new IllegalArgumentException("side length must be positive");
    } else if (numberOfTiles < 1) {
      throw new IllegalArgumentException("number of tiles must be positive");
    } else if (!isPerfectSquare(numberOfTiles)) {
      throw new IllegalArgumentException("number of tiles cannot form square board");
    }

    for (int j = 0; j < (int) Math.sqrt(numberOfTiles); j++) {
      for (int i = 0; i < tileSideLength; i++) {
        pixels
            .add(createCheckerboardRow(blackFirst, tileSideLength, (int) Math.sqrt(numberOfTiles)));
      }
      blackFirst = !blackFirst;
    }

    return new ImageImpl(pixels);
  }

  private boolean isPerfectSquare(int num) {
    if (num >= 0) {
      int sr = (int) Math.sqrt(num);
      return ((sr * sr) == num);
    }
    return false;
  }

  /**
   * Creates a row of a checkerboard image.
   *
   * @param blackFirst whether or not a black is the color of the first tile in this row
   * @param width      number of tiles in this row
   * @return a row in a checkerboard image
   */
  private List<Pixel> createCheckerboardRow(boolean blackFirst, int tileSideLength,
      int checkerboardSideLength) {
    List<Pixel> row = new ArrayList<Pixel>();
    for (int i = 0; i < checkerboardSideLength; i++) {
      for (int j = 0; j < tileSideLength; j++) {
        if (blackFirst) {
          row.add(new PixelImpl(0, 0, 0));
        } else {
          row.add(new PixelImpl(255, 255, 255));
        }
      }
      blackFirst = !blackFirst;
    }

    return row;
  }

  @Override
  public Image getImage() {
    return image; // create copy first
  }

  @Override
  public void setImage(Image image) {
    this.image = image; // should copy input then set
  }
}
