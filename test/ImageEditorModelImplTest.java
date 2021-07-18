// package test;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotEquals;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import org.junit.Before;
// import org.junit.Test;

// import src.model.ImageEditorModel;
// import src.model.ImageEditorModelImpl;
// import Sepia;
// import src.model.imagerep.Pixel;
// import src.model.imagerep.PixelImpl;

// /**
// * Test class for ImageEditorModelImpl. Tests that all functionality is
// * implemented correctly.
// */
// public class ImageEditorModelImplTest {

// private ImageEditorModel model;
// private List<List<Pixel>> checkerboard;
// private List<List<Pixel>> singlePixel;

// @Before
// public void initData() {
// this.model = new ImageEditorModelImpl();

// List<Pixel> row1 = new ArrayList<Pixel>(Arrays.asList(new PixelImpl(0, 0, 0),
// new PixelImpl(255, 255, 255)));
// List<Pixel> row2 = new ArrayList<Pixel>(Arrays.asList(new PixelImpl(255, 255,
// 255), new PixelImpl(0, 0, 0)));
// checkerboard = new ArrayList<List<Pixel>>();
// checkerboard.add(row1);
// checkerboard.add(row2);

// singlePixel = new ArrayList<>(Arrays.asList(new
// ArrayList<Pixel>(Arrays.asList(new PixelImpl(0, 0, 0)))));
// }

// @Test(expected = IllegalArgumentException.class)
// public void testApplyImageProcessorExceptionProcessorIsNull() {
// model.applyImageProcessor(null);
// }

// @Test
// public void testApplyImageProcessor() {
// List<List<Pixel>> pixelsBefore = model.getImage().getPixels();
// model.applyImageProcessor(new Sepia());
// List<List<Pixel>> pixelsAfter = model.getImage().getPixels();
// List<Pixel> row1 = new ArrayList<Pixel>(Arrays.asList(new PixelImpl(0, 0, 0),
// new PixelImpl(255, 255, 239)));
// List<Pixel> row2 = new ArrayList<Pixel>(Arrays.asList(new PixelImpl(255, 255,
// 239), new PixelImpl(0, 0, 0)));
// List<List<Pixel>> expectedPixelsAfter = new ArrayList<List<Pixel>>();
// expectedPixelsAfter.add(row1);
// expectedPixelsAfter.add(row2);

// assertNotEquals(pixelsBefore, pixelsAfter);
// assertEquals(expectedPixelsAfter, pixelsAfter);
// }

// @Test(expected = IllegalArgumentException.class)
// public void testImportFileExceptionFileNameIsNull() {
// model.importFile(null, new PpmImageConverter());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testImportFileExceptionConverterIsNull() {
// model.importFile("ImageWithComments.ppm", null);
// }

// @Test(expected = IllegalArgumentException.class)
// public void testImportFileExceptionInvalidFileType() {
// model.importFile("InvalidFileType.jpg", new PpmImageConverter());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testImportFileExceptionFileDoesNotExist() {
// model.importFile("Nonexistent.ppm", new PpmImageConverter());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testImportFileExceptionFileFormattedImproperly() {
// model.importFile("DoesNotStartWithP3.ppm", new PpmImageConverter());
// }

// @Test
// public void testImportFile() {
// assertEquals(checkerboard, model.getImage().getPixels());

// model.importFile("SinglePixel.ppm", new PpmImageConverter());

// assertEquals(singlePixel, model.getImage().getPixels());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testExportFileExceptionFileNameIsNull() {
// model.exportFile(null, new PpmImageConverter());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testExportFileExceptionConverterIsNull() {
// model.exportFile("doesntmatter.ppm", null);
// }

// @Test(expected = IllegalArgumentException.class)
// public void testExportFileExceptionInvalidFileType() {
// model.exportFile("doesntmatter.jpg", new PpmImageConverter());
// }

// @Test(expected = IllegalStateException.class)
// public void testExportFileErrorWritingToFile() {
// throw new IllegalStateException();
// }

// @Test
// public void testExportFile() {
// model.exportFile("Checkerboard.ppm", new PpmImageConverter());
// assertEquals(checkerboard, model.getImage().getPixels());

// model.generateCheckerboard(1, 1);
// assertEquals(singlePixel, model.getImage().getPixels());

// model.importFile("Checkerboard.ppm", new PpmImageConverter());
// assertEquals(checkerboard, model.getImage().getPixels());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testGenerateCheckerboardExceptionWidthNotPositive() {
// model.generateCheckerboard(0, 1);
// }

// @Test(expected = IllegalArgumentException.class)
// public void testGenerateCheckerboardExceptionHeightNotPositive() {
// model.generateCheckerboard(1, 0);
// }

// @Test
// public void testGenerateCheckerboard() {
// assertEquals(checkerboard, model.getImage().getPixels());

// model.generateCheckerboard(1, 1);

// assertEquals(singlePixel, model.getImage().getPixels());
// }

// @Test
// public void testGetImage() {
// assertEquals(checkerboard, model.getImage().getPixels());
// }
// }
