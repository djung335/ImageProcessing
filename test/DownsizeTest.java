package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import src.model.ImageEditorModel;
import src.model.ImageEditorModelImpl;
import src.model.imageprocessing.Downsize;
import src.model.imageprocessing.ImageProcessor;


/**
 * Test class for Downsize; unit tests to make sure the Downsize functionality works as expected.
 */
public class DownsizeTest {

  ImageProcessor half = new Downsize(0.5, 0.5);
  ImageProcessor uneven = new Downsize(0.25, 0.75);
  ImageEditorModel model = new ImageEditorModelImpl();

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenWidthIsNegative() {
    Downsize d = new Downsize(-3, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenWidthIsOverOne() {
    Downsize d = new Downsize(15, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenHeightIsNegative() {
    Downsize d = new Downsize(0.4, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenHeightIsOverOne() {
    Downsize d = new Downsize(-3, 1.111);
  }

  @Test
  public void testDownsizeHalf() {
    model.generateCheckerboard(4, 1);
    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(0).get(0).getRed());
    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(0).get(0).getGreen());
    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(0).get(0).getBlue());

    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(0).get(1).getRed());
    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(0).get(1).getGreen());
    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(0).get(1).getBlue());

    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(1).get(0).getRed());
    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(1).get(0).getGreen());
    assertEquals(255,
        half.processImage(model.getImage()).getPixels().get(1).get(0).getBlue());

    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(1).get(1).getRed());
    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(1).get(1).getGreen());
    assertEquals(0,
        half.processImage(model.getImage()).getPixels().get(1).get(1).getBlue());

    assertEquals(2, half.processImage(model.getImage()).getPixels().size());
    assertEquals(2, half.processImage(model.getImage()).getPixels().get(0).size());


  }

  @Test
  public void testDownsizeUneven() {
    model.generateCheckerboard(4, 1);
    assertEquals(0,
        uneven.processImage(model.getImage()).getPixels().get(0).get(0).getRed());
    assertEquals(0,
        uneven.processImage(model.getImage()).getPixels().get(0).get(0).getGreen());
    assertEquals(0,
        uneven.processImage(model.getImage()).getPixels().get(0).get(0).getBlue());
    assertEquals(128,
        uneven.processImage(model.getImage()).getPixels().get(1).get(0).getRed());
    assertEquals(128,
        uneven.processImage(model.getImage()).getPixels().get(1).get(0).getGreen());
    assertEquals(128,
        uneven.processImage(model.getImage()).getPixels().get(1).get(0).getBlue());
    assertEquals(255,
        uneven.processImage(model.getImage()).getPixels().get(2).get(0).getRed());
    assertEquals(255,
        uneven.processImage(model.getImage()).getPixels().get(2).get(0).getGreen());
    assertEquals(255,
        uneven.processImage(model.getImage()).getPixels().get(2).get(0).getBlue());

    assertEquals(3, uneven.processImage(model.getImage()).getPixels().size());
    assertEquals(1, uneven.processImage(model.getImage()).getPixels().get(0).size());


  }


}
