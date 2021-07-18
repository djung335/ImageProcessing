package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.imageprocessing.filter.AbstractFilter;

/**
 * Test class for Filter: unit tests to ensure that filters are working as intended.
 */
public class FilterTest {

  class DummyClass1 extends AbstractFilter {

    @Override
    protected List<List<Double>> getFilterMatrix() {
      return null;
    }
  }

  class DummyClass2 extends AbstractFilter {

    @Override
    protected List<List<Double>> getFilterMatrix() {
      return new ArrayList<List<Double>>(
          Arrays.asList(new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0))));
    }
  }

  class DummyClass3 extends AbstractFilter {

    @Override
    protected List<List<Double>> getFilterMatrix() {
      return new ArrayList<List<Double>>();
    }
  }

  class DummyClass4 extends AbstractFilter {

    @Override
    protected List<List<Double>> getFilterMatrix() {
      return new ArrayList<List<Double>>(
          Arrays.asList(new ArrayList<Double>(Arrays.asList(1.0, 2.0)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0))));
    }

  }

  class DummyClass5 extends AbstractFilter {

    @Override
    protected List<List<Double>> getFilterMatrix() {
      return new ArrayList<List<Double>>(
          Arrays.asList(new ArrayList<Double>(Arrays.asList(1.0, 2.0, null)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
              new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0))));
    }

  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterConstructorExceptionWhenKernelIsNull() {
    new DummyClass1();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterConstructorExceptionWhenOneRowIsInvalid() {
    new DummyClass2();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterConstructorExceptionWhenSizeIsZero() {
    new DummyClass3();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterConstructorExceptionWhenNonSquareMatrix() {
    new DummyClass4();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterConstructorExceptionWhenNullDouble() {
    new DummyClass5();
  }
}
