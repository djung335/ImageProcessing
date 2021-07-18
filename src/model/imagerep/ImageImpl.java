package src.model.imagerep;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the image implementation, and it implements the image interface as well as
 * the methods relevant to that interface.
 */
public class ImageImpl implements Image {

  private final List<List<Pixel>> pixels;

  /**
   * Constructs an ImageImpl object.
   *
   * @param pixels pixels making up image
   * @throws IllegalArgumentException if list of pixels is null, list of pixels is empty, rows are
   *                                  not the same length, or pixels in list are null
   */
  public ImageImpl(List<List<Pixel>> pixels) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("pixels cannot be null.");
    }

    if (pixels.size() == 0) {
      throw new IllegalArgumentException("no pixels in list");
    }

    int expectedLength = pixels.get(0).size();
    for (List<Pixel> listOfPixels : pixels) {
      if (listOfPixels.size() != expectedLength) {
        throw new IllegalArgumentException("pixels are improperly formatted");
      }

      for (Pixel p : listOfPixels) {
        if (p == null) {
          throw new IllegalArgumentException("pixel cannot be null");
        }
      }
    }

    this.pixels = createCopy(pixels);
  }

  /**
   * Creates copy of given list of pixels.
   *
   * @param pixels list to create copy for
   * @return copy of list of pixels
   */
  private List<List<Pixel>> createCopy(List<List<Pixel>> pixels) {
    List<List<Pixel>> copyPixels = new ArrayList<List<Pixel>>();
    for (List<Pixel> row : pixels) {
      List<Pixel> copyRow = new ArrayList<Pixel>();
      for (Pixel p : row) {
        copyRow.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
      }
      copyPixels.add(copyRow);
    }

    return copyPixels;
  }

  @Override
  public List<List<Pixel>> getPixels() {
    List<List<Pixel>> copy = new ArrayList<List<Pixel>>();
    for (int i = 0; i < pixels.size(); i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < pixels.get(0).size(); j++) {
        row.add(pixels.get(i).get(j));
      }
      copy.add(row);
    }

    return copy;
  }
}
