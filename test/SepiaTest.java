package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.ImageEditorModel;
import src.model.ImageEditorModelImpl;
import src.model.imageprocessing.ImageProcessor;
import src.model.imageprocessing.transformation.Sepia;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Test class for Sepia: unit tests to ensure that the Sepia class is working as intended.
 */
public class SepiaTest {

  ImageProcessor s = new Sepia();
  ImageEditorModel model = new ImageEditorModelImpl();

  // Tests the sepia method on a null image.
  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    s.processImage(null);
  }

  // Tests the sepia method on a 2 by 2 checkerboard in order to check if the math
  // is correct.
  @Test
  public void testProcessCheckerBoard() {
    model.generateCheckerboard(1, 1);
    assertEquals(0, s.processImage(model.getImage()).getPixels().get(0).get(0).getRed());
    assertEquals(0, s.processImage(model.getImage()).getPixels().get(0).get(0).getGreen());
    assertEquals(0, s.processImage(model.getImage()).getPixels().get(0).get(0).getBlue());
    // assertEquals(255,
    // s.processImage(model.getImage()).getPixels().get(0).get(1).getRed());
    // assertEquals(255,
    // s.processImage(model.getImage()).getPixels().get(0).get(1).getGreen());
    // assertEquals(239,
    // s.processImage(model.getImage()).getPixels().get(0).get(1).getBlue());
    // assertEquals(255,
    // s.processImage(model.getImage()).getPixels().get(1).get(0).getRed());
    // assertEquals(255,
    // s.processImage(model.getImage()).getPixels().get(1).get(0).getGreen());
    // assertEquals(239,
    // s.processImage(model.getImage()).getPixels().get(1).get(0).getBlue());
    // assertEquals(0,
    // s.processImage(model.getImage()).getPixels().get(1).get(1).getRed());
    // assertEquals(0,
    // s.processImage(model.getImage()).getPixels().get(1).get(1).getGreen());
    // assertEquals(0,
    // s.processImage(model.getImage()).getPixels().get(1).get(1).getBlue());

  }

  // Tests the sepia method on a 1 by 3 image in order to check if the math is
  // correct.
  @Test
  public void testProcessCustom() {
    List<List<Pixel>> customPixels = new ArrayList<List<Pixel>>();
    customPixels.add(new ArrayList<Pixel>(
        Arrays.asList(new PixelImpl(23, 45, 11), new PixelImpl(13, 254, 0),
            new PixelImpl(87, 1, 193))));
    Image customImage = new ImageImpl(customPixels);
    assertEquals(46, s.processImage(customImage).getPixels().get(0).get(0).getRed());
    assertEquals(41, s.processImage(customImage).getPixels().get(0).get(0).getGreen());
    assertEquals(32, s.processImage(customImage).getPixels().get(0).get(0).getBlue());
    assertEquals(200, s.processImage(customImage).getPixels().get(0).get(1).getRed());
    assertEquals(179, s.processImage(customImage).getPixels().get(0).get(1).getGreen());
    assertEquals(139, s.processImage(customImage).getPixels().get(0).get(1).getBlue());
    assertEquals(71, s.processImage(customImage).getPixels().get(0).get(2).getRed());
    assertEquals(63, s.processImage(customImage).getPixels().get(0).get(2).getGreen());
    assertEquals(49, s.processImage(customImage).getPixels().get(0).get(2).getBlue());

  }
}