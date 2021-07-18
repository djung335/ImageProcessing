package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.ImageEditorModel;
import src.model.ImageEditorModelImpl;
import src.model.imageprocessing.ImageProcessor;
import src.model.imageprocessing.transformation.Grayscale;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Test class for Grayscale: unit tests to ensure that the Grayscale class is working as intended.
 */
public class GrayscaleTest {

  ImageProcessor g = new Grayscale();
  ImageEditorModel model = new ImageEditorModelImpl();

  // Tests the grayscale method on a null image.
  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    g.processImage(null);
  }

  // Tests the grayscale method on a 2 by 2 checkerboard in order to check if the
  // math is correct.
  @Test
  public void testProcessCheckerBoard() {
    model.generateCheckerboard(1, 1);
    assertEquals(0, g.processImage(model.getImage()).getPixels().get(0).get(0).getRed());
    // assertEquals(0,
    // g.processImage(model.getImage()).getPixels().get(0).get(0).getGreen());
    // assertEquals(0,
    // g.processImage(model.getImage()).getPixels().get(0).get(0).getBlue());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(0).get(1).getRed());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(0).get(1).getGreen());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(0).get(1).getBlue());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(1).get(0).getRed());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(1).get(0).getGreen());
    // assertEquals(255,
    // g.processImage(model.getImage()).getPixels().get(1).get(0).getBlue());
    // assertEquals(0,
    // g.processImage(model.getImage()).getPixels().get(1).get(1).getRed());
    // assertEquals(0,
    // g.processImage(model.getImage()).getPixels().get(1).get(1).getGreen());
    // assertEquals(0,
    // g.processImage(model.getImage()).getPixels().get(1).get(1).getBlue());

  }

  // Tests the grayscale method on a 1 by 3 image in order to check if the math is
  // correct.
  @Test
  public void testProcessCustom() {
    List<List<Pixel>> customPixels = new ArrayList<List<Pixel>>();
    customPixels.add(new ArrayList<Pixel>(
        Arrays.asList(new PixelImpl(23, 45, 11), new PixelImpl(13, 254, 0),
            new PixelImpl(87, 1, 193))));
    Image customImage = new ImageImpl(customPixels);
    assertEquals(38, g.processImage(customImage).getPixels().get(0).get(0).getRed());
    assertEquals(38, g.processImage(customImage).getPixels().get(0).get(0).getGreen());
    assertEquals(38, g.processImage(customImage).getPixels().get(0).get(0).getBlue());
    assertEquals(184, g.processImage(customImage).getPixels().get(0).get(1).getRed());
    assertEquals(184, g.processImage(customImage).getPixels().get(0).get(1).getGreen());
    assertEquals(184, g.processImage(customImage).getPixels().get(0).get(1).getBlue());
    assertEquals(33, g.processImage(customImage).getPixels().get(0).get(2).getRed());
    assertEquals(33, g.processImage(customImage).getPixels().get(0).get(2).getGreen());
    assertEquals(33, g.processImage(customImage).getPixels().get(0).get(2).getBlue());

  }
}