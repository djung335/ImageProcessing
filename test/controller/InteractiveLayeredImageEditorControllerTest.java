package test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import src.controller.InteractiveLayeredImageEditorController;
import src.controller.SimpleImageEditorController;
import src.controller.imageio.LayeredImageFolderConverter;
import src.controller.imageio.PpmImageFileConverter;
import src.controller.imageio.StandardImageFileConverter;
import src.model.ImageEditorModelImpl;
import src.model.LayeredImageEditorModel;
import src.model.LayeredImageEditorModelImpl;
import src.model.imageprocessing.filter.Blur;
import src.model.imagerep.LayerState;
import src.model.imagerep.LayerStateImpl;

/**
 * Test class for InteractiveLayeredImageEditorController.
 */
public class InteractiveLayeredImageEditorControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInputsAreNull() {
    new InteractiveLayeredImageEditorController(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageFromExceptionSourceIsNull() {
    new InteractiveLayeredImageEditorController().readImageFrom(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageFromExceptionSourceIsNonExistent() {
    new InteractiveLayeredImageEditorController().readImageFrom("nonexistent.jpeg");
  }

  @Test
  public void testReadImageFrom() {
    new InteractiveLayeredImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "test.ppm");
    assertEquals(new ImageEditorModelImpl()
            .generateCheckerboard(2, 4).getPixels(),
        new InteractiveLayeredImageEditorController().readImageFrom("test.ppm").getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteImageToExceptionArgumentsAreNull() {
    new InteractiveLayeredImageEditorController().writeImageTo(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteImageToExceptionDestinationIsInvalid() {
    new InteractiveLayeredImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "invalid.invalid");
  }

  @Test
  public void testWriteImageTo() {
    new InteractiveLayeredImageEditorController()
        .writeImageTo(new ImageEditorModelImpl().generateCheckerboard(2, 4),
            "test.ppm");
    assertEquals(new ImageEditorModelImpl()
            .generateCheckerboard(2, 4).getPixels(),
        new SimpleImageEditorController().readImageFrom("test.ppm").getPixels());
  }

  @Test
  public void testStartProgramAddLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nq\n");

    assertEquals(0, model.getLayeredImage().size());
    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(1, model.getLayeredImage().size());
  }

  @Test
  public void testStartProgramRemoveLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nadd second\nremove first\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(1, model.getLayeredImage().size());
    assertEquals(0, model.getLayeredImage().get("second").getIndex());
  }

  @Test
  public void testStartProgramRemoveLayerCaughtExceptionNonexistentLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("remove first\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(output.toString().contains(
        ".....try another command: java.lang.IllegalArgumentException:"
            + " trying to remove nonexistent layer"));
  }

  @Test
  public void testStartProgramCommandNotSupported() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("invalid command\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(output.toString().contains(".....command not supported; try again"));
  }

  @Test
  public void testStartProgramSetCurrentLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.getImage().getPixels(),
        model.getLayeredImage().get("first").getImage().getPixels());
  }

  @Test
  public void testStartProgramSetCurrentLayerCaughtExceptionNonexistentLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("current first\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();

    // System.out.println(output.toString());
    assertTrue(output.toString().contains(
        ".....try another command"
            + ": java.lang.IllegalArgumentException: layer to set current to does not exist"));
  }

  @Test
  public void testStartProgramSetLayerVisibility() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader(
        "add first\nvisible first\ninvisible "
            + "first\nadd second\ninvisible second\nvisible second\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertFalse(model.getLayeredImage().get("first").getVisibility());
    assertTrue(model.getLayeredImage().get("second").getVisibility());
  }

  @Test
  public void testStartProgramSetLayerVisibilityCaughtExceptionNonexistentLayer() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("invisible first\nvisible first\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();

    assertTrue(output.toString().contains(
        ".....try another command"
            + ": java.lang.IllegalArgumentException: layer with given name does not exist"));
  }

  @Test
  public void testStartProgramBlurCommand() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\nblur\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(new Blur()
            .processImage(model.generateCheckerboard(1, 1)).getPixels(),
        model.getImage().getPixels());
  }

  @Test
  public void testStartProgramBlurCommandCaughtExceptionNoImageToApplyTo() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nblur\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(output.toString()
        .contains(
            ".....try another command: java.lang.IllegalArgumentException: image cannot be null."));
  }

  @Test
  public void testStartProgramCheckerboardCommand() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\ncheckerboard\nq\n");

    // System.out.println(output.toString());
    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(5, 4)
        .getPixels(), model.getImage().getPixels());
  }

  @Test
  public void testStartProgramReadCommandPpm() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\nread temp.ppm\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1)
        .getPixels(), model.getImage().getPixels());
  }

  @Test
  public void testStartProgramReadCommandPng() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    //    new StandardImageFileConverter()
    //        .writeImage(model.generateCheckerboard(1, 1),
    //            "temp.png", "png");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\nread temp.png\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1)
        .getPixels(), model.getImage().getPixels());
  }

  @Test
  public void testStartProgramReadCommandJpeg() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new StandardImageFileConverter()
        .writeImage(model.generateCheckerboard(1, 1),
            "temp.jpeg", "jpeg");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\nread temp.jpeg\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1)
        .getPixels(), model.getImage().getPixels());
  }

  @Test
  public void testStartProgramReadCommandLayers() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    Map<String, LayerState> layers = new HashMap<String, LayerState>();
    layers.put("temp",
        new LayerStateImpl(model.generateCheckerboard(1, 1),
            true, 0));
    new LayeredImageFolderConverter()
        .writeImage(layers, "temp.layers", "layers");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("read temp.layers\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        model.getLayeredImage().get("temp").getImage().getPixels());
  }

  @Test
  public void testStartProgramReadCommandCaughtExceptionInvalidImage() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("read nonexistent.ppm\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(
        output.toString().contains(
            "....try another command: java.lang.IllegalArgumentException: file not found"));
  }

  @Test
  public void testStartProgramReadCommandCaughtExceptionNoCurrentLayerToWriteTo() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nread temp.ppm\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(
        output.toString().contains(".....try another command: java.lang.IllegalArgumentException:"
            + " layer not yet created; cannot set image to nonexistent layer"));
  }

  @Test
  public void testStartProgramWriteCommandPpm() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nread temp.ppm\nwrite temp2.ppm\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        new PpmImageFileConverter().readImage("temp2.ppm").getPixels());
  }

  @Test
  public void testStartProgramWriteCommandPng() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nread temp.ppm\nwrite temp2.png\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        new StandardImageFileConverter().readImage("temp2.png").getPixels());
  }

  @Test
  public void testStartProgramWriteCommandJpeg() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\nread temp.ppm\nwrite temp2.jpeg\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        new StandardImageFileConverter().readImage("temp2.jpeg").getPixels());
  }

  @Test
  public void testStartProgramWriteCommandLayers() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    Map<String, LayerState> layers = new HashMap<String, LayerState>();
    layers.put("temp",
        new LayerStateImpl(model.generateCheckerboard(1, 1),
            true, 0));
    new LayeredImageFolderConverter()
        .writeImage(layers, "temp.layers", "layers");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("read temp.layers\nwrite temp2.layers\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        new LayeredImageFolderConverter().readImage("temp2.layers").get("temp").getImage()
            .getPixels());
    assertEquals(0,
        new LayeredImageFolderConverter()
            .readImage("temp2.layers").get("temp").getIndex());
    assertTrue(
        new LayeredImageFolderConverter().readImage("temp2.layers")
            .get("temp").getVisibility());
  }

  @Test
  public void testStartProgramWriteCommandLayersImageWroteIsNotTopmost() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    new SimpleImageEditorController()
        .writeImageTo(model.generateCheckerboard(1, 1), "temp.ppm");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader("add first\ncurrent first\ncheckerboard\nadd"
        + " second\ncurrent second\nread temp.ppm\ninvisible second\nwrite dest.png\nq\n");

    assertEquals(model.generateCheckerboard(1, 1).getPixels(),
        new PpmImageFileConverter().readImage("temp.ppm").getPixels());
    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
  }

  @Test
  public void testStartProgramWriteLayersCaughtExceptionNoVisibleLayersToWriteImage() {
    LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
    Map<String, LayerState> layers = new HashMap<String, LayerState>();
    layers.put("temp",
        new LayerStateImpl(model.generateCheckerboard(1, 1),
            true, 0));
    new LayeredImageFolderConverter()
        .writeImage(layers, "temp.layers", "layers");
    StringBuilder output = new StringBuilder();
    StringReader commands = new StringReader(
        "read temp.layers\ninvisible temp\nwrite meh.png\nq\n");

    new InteractiveLayeredImageEditorController(model, commands, output).startProgram();
    assertTrue(output.toString()
        .contains(".....try another command:"
            + " java.lang.IllegalArgumentException: image has no visible layers"));
  }
}
