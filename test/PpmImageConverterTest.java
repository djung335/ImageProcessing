// package test;

// import static org.junit.Assert.assertEquals;

// import org.junit.Test;
// import src.Image;
// import src.ImageConverter;
// import src.PpmImageConverter;
// import src.model.ImageEditorModel;
// import src.model.ImageEditorModelImpl;
// import src.model.imagerep.Image;

// /**
// * Tester class for PpmImageConverter. Makes sure that all functionality is
// * correctly implemented.
// */
// public class PpmImageConverterTest {

// private final ImageConverter converter;
// private final Image img;

// /**
// * Constructs a PpmImageConverterTest.
// */
// public PpmImageConverterTest() {
// this.converter = new PpmImageConverter();
// ImageEditorModel model = new ImageEditorModelImpl();
// model.generateCheckerboard(2, 2);
// this.img = model.getImage();
// }

// @Test(expected = IllegalArgumentException.class)
// public void testReadImageFromFileExceptionInvalidFileType() {
// converter.readImageFromFile("image.jpg");
// }

// @Test(expected = IllegalArgumentException.class)
// public void testReadImageFromFileExceptionFileDoesNotExist() {
// converter.readImageFromFile("nonexistent.ppm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void
// testReadImageFromFileExceptionFileFormattedImproperlyDoesNotStartWithP3() {
// converter.readImageFromFile("DoesNotStartWithP3.ppm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void
// testReadImageFromFileExceptionFileFormattedImproperlyHasStringAsRgbValue() {
// converter.readImageFromFile("HasStringAsRgbValue.ppm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void
// testReadImageFromFileExceptionFileFormattedImproperlyHasDoubleAsRgbValue() {
// converter.readImageFromFile("HasDoubleAsRgbValue.ppm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void
// testReadImageFromFileExceptionFileFormattedImproperlyRgbValueExceedsMaxValue()
// {
// converter.readImageFromFile("HasRgbValueExceedsMaxIntValue.ppm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void testReadImageFromFileNotEnoughRgbValues() {
// converter.readImageFromFile("NotEnoughRgbValues.ppm");
// }

// @Test
// public void testReadImageFromFile() {
// // with comments
// assertEquals(img.getPixels(),
// converter.readImageFromFile("ImageWithComments.ppm").getPixels());

// // without comments
// assertEquals(img.getPixels(),
// converter.readImageFromFile("ImageWithComments.ppm").getPixels());

// // capitalized file extension
// assertEquals(img.getPixels(),
// converter.readImageFromFile("ImageWithCapitalizedFileExt.PPM").getPixels());

// // excess RGB values
// assertEquals(img.getPixels(),
// converter.readImageFromFile("MoreThanEnoughRgbValues.ppm").getPixels());

// // clamping values than exceed 255 and are below 0
// assertEquals(img.getPixels(),
// converter.readImageFromFile("OutOfRangeRgbValues.ppm").getPixels());
// }

// @Test(expected = IllegalArgumentException.class)
// public void testWriteImageToFileExceptionInvalidFileTypeJpg() {
// converter.writeImageToFile(img, "InvalidFileType.jpg");
// }

// @Test(expected = IllegalArgumentException.class)
// public void testWriteImageToFileExceptionInvalidFileTypePpmm() {
// converter.writeImageToFile(img, "InvalidFileExtension.ppmm");
// }

// @Test(expected = IllegalArgumentException.class)
// public void testWriteImageToFileExceptionInvalidFileTypeNoExtension() {
// converter.writeImageToFile(img, "InvalidFileNoExtension");
// }

// @Test(expected = IllegalStateException.class)
// public void testWriteImageToFileExceptionIssueWritingToFile() {
// throw new IllegalStateException();
// }

// @Test
// public void testWriteImageToFile() {
// converter.writeImageToFile(img, "ImageWithoutComments.ppm");
// assertEquals(img.getPixels(),
// converter.readImageFromFile("ImageWithoutComments.ppm").getPixels());
// converter.writeImageToFile(img, "ImageWithCapitalizedFileExt.PPM");
// assertEquals(img.getPixels(),
// converter.readImageFromFile("ImageWithCapitalizedFileExt.PPM").getPixels());
// }
// }
