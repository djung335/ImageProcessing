package src.model.imageprocessing.transformation;

import java.util.ArrayList;
import java.util.List;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Class that contains all fields and methods that linear color transformations have in common.
 */
public abstract class AbstractLinearColorTransformation extends AbstractColorTransformation {

  protected final List<List<Double>> transformationMatrix;

  /**
   * Creates a <? extends AbstractLinearColorTransformation>.
   *
   * @throws IllegalArgumentException if matrix is not 3x3
   */
  public AbstractLinearColorTransformation() throws IllegalArgumentException {
    List<List<Double>> transformationMatrix = getTransformationMatrix();
    checkForValidTransformationMatrix(transformationMatrix);

    this.transformationMatrix = transformationMatrix;
  }

  /**
   * Creates a <? extends AbstractLinearColorTransformation>.
   *
   * @throws IllegalArgumentException if matrix is not 3x3
   */
  public AbstractLinearColorTransformation(List<List<Double>> transformationMatrix)
      throws IllegalArgumentException {
    checkForValidTransformationMatrix(transformationMatrix);

    this.transformationMatrix = transformationMatrix;
  }

  private void checkForValidTransformationMatrix(List<List<Double>> transformationMatrix) {
    if (transformationMatrix.size() != 3) {
      throw new IllegalArgumentException("matrix must be 3x3");
    } else {
      for (List<Double> row : transformationMatrix) {
        if (row.size() != 3) {
          throw new IllegalArgumentException("matrix must be 3x3");
        }
      }
    }
  }

  /**
   * Gets transformation matrix to help set field in constructor.
   *
   * @return transformation matrix
   */
  protected abstract List<List<Double>> getTransformationMatrix();

  @Override
  public Image processImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image is null");
    }

    List<List<Pixel>> oldPixels = image.getPixels();
    List<List<Pixel>> newPixels = new ArrayList<List<Pixel>>();
    for (int i = 0; i < oldPixels.size(); i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < oldPixels.get(0).size(); j++) {
        row.add(applyColorTransformationToPixel(oldPixels.get(i).get(j)));
      }
      newPixels.add(row);
    }

    return new ImageImpl(newPixels);
  }

  /**
   * Applies color transformation to given pixel.
   *
   * @param p pixels to apply color transformation to
   * @return pixel with color transformation applied
   */
  private Pixel applyColorTransformationToPixel(Pixel p) {
    int oldRed = p.getRed();
    int oldGreen = p.getGreen();
    int oldBlue = p.getBlue();

    double newRed = getNewColor(oldRed, oldGreen, oldBlue, transformationMatrix.get(0));
    double newGreen = getNewColor(oldRed, oldGreen, oldBlue, transformationMatrix.get(1));
    double newBlue = getNewColor(oldRed, oldGreen, oldBlue, transformationMatrix.get(2));

    return new PixelImpl(clamp(newRed), clamp(newGreen), clamp(newBlue));
  }

  /**
   * Gets new color value given old color values and matrix row containing weights for linear
   * combination.
   *
   * @param oldRed                  old red channel value
   * @param oldGreen                old green channel value
   * @param oldBlue                 old blue channel value
   * @param transformationMatrixRow weights for new color calculation
   * @return new color value
   */
  private int getNewColor(int oldRed, int oldGreen, int oldBlue,
      List<Double> transformationMatrixRow) {
    return clamp(oldRed * transformationMatrixRow.get(0) + oldGreen * transformationMatrixRow.get(1)
        + oldBlue * transformationMatrixRow.get(2));
  }
}
