package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import src.model.ImageEditorModel;
import src.model.ImageEditorModelImpl;
import src.model.imageprocessing.ImageProcessor;
import src.model.imageprocessing.Mosaic;


/**
 * Test class for Mosaic; unit tests to make sure the Mosaic functionality works as expected.
 */
public class MosaicTest {

  ImageProcessor m = new Mosaic(2000);
  ImageEditorModel model = new ImageEditorModelImpl();


  @Test(expected = IllegalArgumentException.class)
  public void testMosaicWhenTooManySeeds() {
    m.processImage(model.generateCheckerboard(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicSeedsIsNegative() {
    new Mosaic(-14).processImage(model.generateCheckerboard(2, 1));
  }

  @Test
  public void testMosaicFunctionality() {

    assertEquals(8,
        new Mosaic(2)
            .processImage(model.generateCheckerboard(4, 4)).getPixels().size());
    assertEquals(8,
        new Mosaic(2)
            .processImage(model.generateCheckerboard(4, 4)).getPixels().get(0).size());
  }

}
