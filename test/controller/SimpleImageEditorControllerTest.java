package test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import src.controller.SimpleImageEditorController;
import src.model.ImageEditorModelImpl;

/**
 * Test class for SimpleImageEditorController.
 */
public class SimpleImageEditorControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageFromExceptionSourceIsNull() {
    new SimpleImageEditorController().readImageFrom(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageFromExceptionSourceIsNonExistent() {
    new SimpleImageEditorController().readImageFrom("nonexistent.jpeg");
  }

  @Test
  public void testReadImageFrom() {
    new SimpleImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "test.ppm");
    assertEquals(new ImageEditorModelImpl().generateCheckerboard(2, 4).getPixels(),
        new SimpleImageEditorController().readImageFrom("test.ppm").getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteImageToExceptionArgumentsAreNull() {
    new SimpleImageEditorController().writeImageTo(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteImageToExceptionDestinationIsInvalid() {
    new SimpleImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "invalid.invalid");
  }

  @Test
  public void testWriteImageTo() {
    new SimpleImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "test.ppm");
    assertEquals(new ImageEditorModelImpl().generateCheckerboard(2, 4).getPixels(),
        new SimpleImageEditorController().readImageFrom("test.ppm").getPixels());
  }
}
