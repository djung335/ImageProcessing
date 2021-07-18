package src.model.imageprocessing.transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A grayscale color transformation that converts images to their black and white equivalent.
 */
public class Grayscale extends AbstractLinearColorTransformation {

  @Override
  protected List<List<Double>> getTransformationMatrix() {
    List<Double> row = new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722));

    return new ArrayList<List<Double>>(Arrays.asList(row, row, row));
  }
}
