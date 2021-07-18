package src.model.imageprocessing.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to represent the Blur filter functionality. It extends the AbstractFilter class, which
 * has methods to apply a filter onto an image.
 */
public class Blur extends AbstractFilter {

  @Override
  protected List<List<Double>> getFilterMatrix() {
    List<List<Double>> blurMatrix = new ArrayList<List<Double>>();
    blurMatrix.add(new ArrayList<Double>(Arrays.asList(1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0)));
    blurMatrix.add(new ArrayList<Double>(Arrays.asList(1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0)));
    blurMatrix.add(new ArrayList<Double>(Arrays.asList(1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0)));

    return blurMatrix;
  }
}
