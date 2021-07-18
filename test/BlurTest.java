package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.ImageEditorModel;
import src.model.ImageEditorModelImpl;
import src.model.imageprocessing.ImageProcessor;
import src.model.imageprocessing.filter.Blur;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Test class for Blur: unit tests to ensure that the Blur class is working as intended.
 */
public class BlurTest {

  ImageProcessor b = new Blur();
  ImageEditorModel model = new ImageEditorModelImpl();

  // Tests the blur method on a null image.
  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    b.processImage(null);
  }

  // Tests the blur method on a 2 by 2 checkerboard in order to check if the math
  // is correct.
  @Test
  public void testProcessCheckerBoard() {
    model.generateCheckerboard(1, 1);
    assertEquals(0, b.processImage(model.getImage()).getPixels().get(0).get(0).getRed());
    // assertEquals(64,
    // b.processImage(model.getImage()).getPixels().get(0).get(0).getGreen());
    // assertEquals(64,
    // b.processImage(model.getImage()).getPixels().get(0).get(0).getBlue());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(0).get(1).getRed());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(0).get(1).getGreen());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(0).get(1).getBlue());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(1).get(0).getRed());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(1).get(0).getGreen());
    // assertEquals(80,
    // b.processImage(model.getImage()).getPixels().get(1).get(0).getBlue());
    // assertEquals(64,
    // b.processImage(model.getImage()).getPixels().get(1).get(1).getRed());
    // assertEquals(64,
    // b.processImage(model.getImage()).getPixels().get(1).get(1).getGreen());
    // assertEquals(64,
    // b.processImage(model.getImage()).getPixels().get(1).get(1).getBlue());

  }

  // Tests the blur method on a 1 by 3 image in order to check if the math is
  // correct.
  @Test
  public void testProcessCustom() {
    List<List<Pixel>> customPixels = new ArrayList<List<Pixel>>();
    customPixels.add(new ArrayList<Pixel>(
        Arrays.asList(new PixelImpl(23, 45, 11), new PixelImpl(13, 254, 0),
            new PixelImpl(87, 1, 193))));
    Image customImage = new ImageImpl(customPixels);
    assertEquals(7, b.processImage(customImage).getPixels().get(0).get(0).getRed());
    assertEquals(43, b.processImage(customImage).getPixels().get(0).get(0).getGreen());
    assertEquals(3, b.processImage(customImage).getPixels().get(0).get(0).getBlue());
    assertEquals(17, b.processImage(customImage).getPixels().get(0).get(1).getRed());
    assertEquals(69, b.processImage(customImage).getPixels().get(0).get(1).getGreen());
    assertEquals(26, b.processImage(customImage).getPixels().get(0).get(1).getBlue());
    assertEquals(23, b.processImage(customImage).getPixels().get(0).get(2).getRed());
    assertEquals(32, b.processImage(customImage).getPixels().get(0).get(2).getGreen());
    assertEquals(48, b.processImage(customImage).getPixels().get(0).get(2).getBlue());

  }
}