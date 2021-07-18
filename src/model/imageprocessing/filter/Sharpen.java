package src.model.imageprocessing.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to represent the Sharpen filter functionality. It extends the AbstractFilter class, which
 * has methods to apply a filter onto an image.
 */
public class Sharpen extends AbstractFilter {

  @Override
  protected List<List<Double>> getFilterMatrix() {
    List<List<Double>> sharpenMatrix = new ArrayList<List<Double>>();
    sharpenMatrix
        .add(new ArrayList<Double>(
            Arrays.asList(-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0)));
    sharpenMatrix
        .add(new ArrayList<Double>(
            Arrays.asList(-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0)));
    sharpenMatrix.add(
        new ArrayList<Double>(Arrays.asList(-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0)));
    sharpenMatrix
        .add(new ArrayList<Double>(
            Arrays.asList(-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0)));
    sharpenMatrix
        .add(new ArrayList<Double>(
            Arrays.asList(-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0)));

    return sharpenMatrix;
  }
}
