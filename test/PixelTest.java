package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Test class for Pixel: unit tests to ensure that the Pixel class is working as intended.
 */
public class PixelTest {

  Pixel redPixel = new PixelImpl(255, 0, 0);
  Pixel greenPixel = new PixelImpl(0, 255, 0);
  Pixel bluePixel = new PixelImpl(0, 0, 255);
  Pixel brownPixel = new PixelImpl(165, 42, 42);

  // Tests the Pixel's constructor with a big red color value.
  @Test(expected = IllegalArgumentException.class)
  public void testBigRed() {
    Pixel illegalRed = new PixelImpl(25545, 3, 144);
  }

  // Tests the Pixel's constructor with a small red color value.
  @Test(expected = IllegalArgumentException.class)
  public void testSmallRed() {
    Pixel illegalRed = new PixelImpl(-33, 3, 144);
  }

  // Tests the Pixel's constructor with a big green color value.
  @Test(expected = IllegalArgumentException.class)
  public void testBigGreen() {
    Pixel illegalGreen = new PixelImpl(3, 33333, 144);
  }

  // Tests the Pixel's constructor with a small green color value.
  @Test(expected = IllegalArgumentException.class)
  public void testSmallGreen() {
    Pixel illegalGreen = new PixelImpl(14, -432, 144);
  }

  // Tests the Pixel's constructor with a big blue color value.
  @Test(expected = IllegalArgumentException.class)
  public void testBigBlue() {
    Pixel illegalBlue = new PixelImpl(144, 3, 33933);
  }

  // Tests the Pixel's constructor with a small blue color value.
  @Test(expected = IllegalArgumentException.class)
  public void testSmallBlue() {
    Pixel illegalBlue = new PixelImpl(14, 3, -65);
  }

  // Tests the Pixel's getRed method.
  @Test
  public void testGetRed() {
    assertEquals(255, this.redPixel.getRed());
    assertEquals(0, this.greenPixel.getRed());
    assertEquals(0, this.bluePixel.getRed());
    assertEquals(165, this.brownPixel.getRed());
  }

  // Tests the Pixel's getGreen method.
  @Test
  public void testGetGreen() {
    assertEquals(0, this.redPixel.getGreen());
    assertEquals(255, this.greenPixel.getGreen());
    assertEquals(0, this.bluePixel.getGreen());
    assertEquals(42, this.brownPixel.getGreen());
  }

  // Tests the Pixel's getBlue method.
  @Test
  public void testGetBlue() {
    assertEquals(0, this.redPixel.getBlue());
    assertEquals(0, this.greenPixel.getBlue());
    assertEquals(255, this.bluePixel.getBlue());
    assertEquals(42, this.brownPixel.getBlue());
  }
}