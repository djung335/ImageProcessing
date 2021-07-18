package src.model.imageprocessing.transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A sepia color transformation that adds a reddish brown tone to given images.
 */
public class Sepia extends AbstractLinearColorTransformation {

  @Override
  protected List<List<Double>> getTransformationMatrix() {
    List<Double> row1 = new ArrayList<Double>(Arrays.asList(0.393, 0.769, 0.189));
    List<Double> row2 = new ArrayList<Double>(Arrays.asList(0.349, 0.686, 0.168));
    List<Double> row3 = new ArrayList<Double>(Arrays.asList(0.272, 0.534, 0.131));

    return new ArrayList<List<Double>>(Arrays.asList(row1, row2, row3));
  }
}
