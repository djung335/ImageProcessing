package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Test class for ImageImpl. Makes sure that functionality is implemented properly including
 * exceptions.
 */
public class ImageImplTest {

  private final List<List<Pixel>> pixelGrid;
  private final Image img;

  /**
   * Constructs an ImageImplTest.
   */
  public ImageImplTest() {
    this.pixelGrid = new ArrayList<List<Pixel>>();
    this.pixelGrid.add(new ArrayList<Pixel>(
        Arrays.asList(new PixelImpl(100, 100, 100),
            new PixelImpl(100, 100, 100))));
    this.pixelGrid.add(new ArrayList<Pixel>(
        Arrays.asList(new PixelImpl(100, 100, 100),
            new PixelImpl(100, 100, 100))));
    this.img = new ImageImpl(pixelGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionPixelGridIsNull() {
    new ImageImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionPixelGridIsEmpty() {
    new ImageImpl(new ArrayList<List<Pixel>>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionPixelGridRowsNotEqualLength() {
    List<Pixel> row1 = new ArrayList<Pixel>(Arrays.asList(new PixelImpl(100, 100, 100)));
    List<Pixel> row2 = new ArrayList<Pixel>();

    new ImageImpl(new ArrayList<List<Pixel>>(Arrays.asList(row1, row2)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionPixelInPixelGridIsNull() {
    List<Pixel> row1 = new ArrayList<Pixel>();
    row1.add(null);

    new ImageImpl(new ArrayList<List<Pixel>>(Arrays.asList(row1)));
  }

  @Test
  public void testGetPixels() {
    assertEquals(pixelGrid, img.getPixels());
  }
}
