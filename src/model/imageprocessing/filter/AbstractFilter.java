package src.model.imageprocessing.filter;

import java.util.ArrayList;
import java.util.List;
import src.model.imageprocessing.AbstractImageProcessor;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * This abstract class represents the AbstractFilter functionality: it holds common methods that
 * filters share and extends the AbstractImageProcessor.
 */
public abstract class AbstractFilter extends AbstractImageProcessor {

  protected final List<List<Double>> kernel;

  /**
   * Constructs a <? extends AbstractFilter>.
   *
   * @throws IllegalArgumentException if kernel is null, empty or invalid
   */
  public AbstractFilter() throws IllegalArgumentException {
    List<List<Double>> kernel = getFilterMatrix();
    checkForValidKernel(kernel);

    this.kernel = kernel;
  }

  /**
   * Creates an instance of a AbstractFilter with a given kernel.
   *
   * @param kernel the given kernel as a 2d list of doubles.
   */
  public AbstractFilter(List<List<Double>> kernel) {
    // kernel has to be a m by n matrix, where m and n are odd (must have a center)
    checkForValidKernel(kernel);

    this.kernel = kernel;
  }

  private void checkForValidKernel(List<List<Double>> kernel) {
    // kernel has to be a m by n matrix, where m and n are odd (must have a center)
    if (kernel == null) {
      throw new IllegalArgumentException("kernel matrix cannot be null.");
    }

    if (kernel.size() == 0) {
      throw new IllegalArgumentException("kernel matrix cannot be empty.");
    }

    if (!isKernelValid(kernel)) {
      throw new IllegalArgumentException("kernel matrix is not valid.");
    }

  }

  /**
   * Gets filter matrix for constructor.
   *
   * @return a filter matrix
   */
  protected abstract List<List<Double>> getFilterMatrix();

  /**
   * Determines if a kernel is valid for filtering use.
   *
   * @param kernel a list of list of doubles that is not null or empty.
   * @return true if kernel is valid, false otherwise.
   */
  private boolean isKernelValid(List<List<Double>> kernel) {
    if (kernel.size() % 2 != 1) {
      return false;
    }
    for (List<Double> l : kernel) {
      if (l.size() != kernel.size() || l.contains(null)) {
        return false;
      }

    }
    return true;
  }

  @Override
  public Image processImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null.");
    }
    List<List<Pixel>> filteredPixels = new ArrayList<List<Pixel>>();
    List<List<Pixel>> copyOfOriginalPixels = image.getPixels();
    for (List<Pixel> l : copyOfOriginalPixels) {
      filteredPixels.add(new ArrayList<Pixel>());
    }
    // traverse the image array first to find the center
    for (int i = 0; i < copyOfOriginalPixels.size(); i++) {
      for (int j = 0; j < copyOfOriginalPixels.get(i).size(); j++) {
        double redCounter = 0;
        double greenCounter = 0;
        double blueCounter = 0;
        int topLeftX = this.kernel.get(0).size() / 2;
        int topLeftY = this.kernel.size() / 2;
        // we have the center of the image, so we can traverse the kernel and multiply
        // numbers now
        for (int x = 0; x < this.kernel.size(); x++) {
          for (int y = 0; y < this.kernel.get(x).size(); y++) {
            int xPos = i + x - topLeftX;
            int yPos = j + y - topLeftY;
            if (xPos >= 0 && yPos >= 0 && xPos < copyOfOriginalPixels.size()
                && yPos < copyOfOriginalPixels.get(xPos).size()) {
              redCounter =
                  redCounter + this.kernel.get(x).get(y) * copyOfOriginalPixels.get(xPos).get(yPos)
                      .getRed();
              greenCounter = greenCounter
                  + this.kernel.get(x).get(y) * copyOfOriginalPixels.get(xPos).get(yPos).getGreen();
              blueCounter = blueCounter
                  + this.kernel.get(x).get(y) * copyOfOriginalPixels.get(xPos).get(yPos).getBlue();
            }
          }
        }
        filteredPixels.get(i)
            .add(new PixelImpl(this.clamp(redCounter), this.clamp(greenCounter),
                this.clamp(blueCounter)));
      }
    }
    return new ImageImpl(filteredPixels);
  }
}
