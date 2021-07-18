package src.model.imageprocessing;

import java.util.ArrayList;
import java.util.List;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * A class to represent the downsizing functionality. It inherits the downsize method from the image
 * processor interface.
 */
public class Downsize implements ImageProcessor {

  private final double widthScale;
  private final double heightScale;

  /**
   * Constructs an instance of a Downsize.
   *
   * @param widthScale  the width scaling, from 0 to 1.
   * @param heightScale the height scaling, from 0 to 1.
   * @throws IllegalArgumentException if either parameters are not between zero(exclusive) and
   *                                  one(inclusive).
   */
  public Downsize(double widthScale, double heightScale) throws IllegalArgumentException {
    if (widthScale <= 0 || widthScale > 1 || heightScale <= 0 || heightScale > 1) {
      throw new IllegalArgumentException("scaling is not appropriate");
    }
    this.widthScale = widthScale;
    this.heightScale = heightScale;
  }

  @Override
  public Image processImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");

    }

    List<List<Pixel>> copy = image.getPixels();
    List<List<Pixel>> downsizedPixels = new ArrayList<List<Pixel>>();

    for (double i = 0; i < copy.size(); i = i + 1 / heightScale) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (double j = 0; j < copy.get(0).size(); j = j + 1 / widthScale) {
        ArrayList<Pixel> pixels = new ArrayList<Pixel>();
        Pixel topLeft = copy.get((int) Math.floor(i)).get((int) Math.floor(j));
        pixels.add(topLeft);
        if (Math.ceil(j) < copy.get(0).size() && Math.ceil(i) < copy.size()) {
          Pixel bottomRight = copy.get((int) Math.ceil(i)).get((int) Math.ceil(j));
          pixels.add(bottomRight);
        }

        if (Math.ceil(i) < copy.size()) {
          Pixel bottomLeft = copy.get((int) Math.ceil(i)).get((int) Math.floor(j));
          pixels.add(bottomLeft);
        }

        if (Math.ceil(j) < copy.get(0).size()) {
          Pixel topRight = copy.get((int) Math.floor(i)).get((int) Math.ceil(j));
          pixels.add(topRight);

        }

        Pixel averagePixel = calculateAverage(pixels);
        row.add(
            new PixelImpl(averagePixel.getRed(), averagePixel.getGreen(), averagePixel.getBlue()));
      }
      downsizedPixels.add(row);
    }

    return new ImageImpl(downsizedPixels);
  }

  /**
   * Calculates the pixel rgb average values of a list of pixels and returns that pixel.
   *
   * @param pixels the pixels to take the average of
   * @return the Pixel holding the average rgb values.
   * @throws IllegalArgumentException if there are no pixels, or if the paramter is null.
   */
  private Pixel calculateAverage(List<Pixel> pixels) throws IllegalArgumentException {
    double red = 0;
    double green = 0;
    double blue = 0;
    if (pixels == null) {
      throw new IllegalArgumentException("pixels cannot be null");
    }
    if (pixels.size() == 0) {
      throw new IllegalArgumentException("cannot take average of zero pixels");
    }
    for (Pixel p : pixels) {
      red = red + p.getRed();
      green = green + p.getGreen();
      blue = blue + p.getBlue();
    }

    red = red / pixels.size();
    green = green / pixels.size();
    blue = blue / pixels.size();
    return new PixelImpl((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));

  }

}
