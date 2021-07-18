package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.imageprocessing.transformation.AbstractLinearColorTransformation;

/**
 * Tests for classes that extends LinearColorTransformations.
 */
public class LinearColorTransformationTest {

  class DummyClass1 extends AbstractLinearColorTransformation {

    @Override
    protected List<List<Double>> getTransformationMatrix() {
      return new ArrayList<List<Double>>();
    }
  }

  class DummyClass2 extends AbstractLinearColorTransformation {

    @Override
    protected List<List<Double>> getTransformationMatrix() {
      return new ArrayList<List<Double>>(
          Arrays.asList(new ArrayList<Double>(), new ArrayList<Double>(), new ArrayList<Double>()));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLinearColorTransformationConstructorExceptionLessThanThreeRows() {
    new DummyClass1();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLinearColorTransformationConstructorExceptionLessThanThreeColumns() {
    new DummyClass2();
  }
}
