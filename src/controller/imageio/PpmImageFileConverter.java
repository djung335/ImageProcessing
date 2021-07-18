package src.controller.imageio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * A class to represent the PpmImageFileConverter. It inherits methods from the ImageConverter of
 * type Image.
 */
public class PpmImageFileConverter implements ImageConverter<Image> {

  @Override
  public void writeImage(Image image, String dest, String fileExtension)
      throws IllegalStateException, IllegalArgumentException {
    if (image == null || dest == null || fileExtension == null) {
      throw new IllegalArgumentException("one of arguments is null");
    } else if (!dest.endsWith("." + fileExtension)) {
      throw new IllegalArgumentException("supplied destination file does not match file extension");
    } else if (!fileExtension.equals("ppm")) {
      throw new IllegalArgumentException("this converter only support ppm");
    }

    List<List<Pixel>> pixels = image.getPixels();
    StringBuilder ppmText = new StringBuilder();
    ppmText.append(setUpPpmText(pixels));
    ppmText.append(formatPixelValues(pixels));

    try {
      FileWriter fileWriter = new FileWriter(dest);
      fileWriter.write(ppmText.toString());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("error in writing to file");
    }
  }

  @Override
  public Image readImage(String src) throws IllegalArgumentException, IllegalStateException {
    if (src == null) {
      throw new IllegalArgumentException("source is null");
    }

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(src));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("file not found");
    }

    sc = new Scanner(filterComments(sc).toString());
    try {
      String token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("ppm file is formatted improperly");
      }

      int width = sc.nextInt();
      int height = sc.nextInt();
      sc.nextInt(); // throwing out max value

      return new ImageImpl(getPixels(sc, width, height));
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("ppm file is formatted improperly");
    }
  }

  /**
   * Creates builder with any comments filtered out.
   *
   * @param sc scanner to read
   * @return builder without comments
   */
  private StringBuilder filterComments(Scanner sc) {
    StringBuilder builder = new StringBuilder();
    // read the file line by line, and populate a string. This will throw away any
    // comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    return builder;
  }

  /**
   * Extracts pixels from given scanner.
   *
   * @param width  width of resulting pixel grid
   * @param height height of resulting pixel grid
   * @param sc     scanner to read from
   * @return list of pixels
   */
  private List<List<Pixel>> getPixels(Scanner sc, int width, int height) {
    List<List<Pixel>> pixels = new ArrayList<List<Pixel>>();
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        row.add(new PixelImpl(clamp(r), clamp(g), clamp(b)));
      }
      pixels.add(row);
    }

    return pixels;
  }

  /**
   * Clamps a color value, represented as an integer from 0 to 255.
   *
   * @param value a double representing the color value.
   * @return the clamped color value.
   */
  private int clamp(int value) {
    if (value > 255) {
      return 255;
    } else if (value < 0) {
      return 0;
    } else {
      return value;
    }
  }

  private StringBuilder setUpPpmText(List<List<Pixel>> pixels) {
    StringBuilder setupText = new StringBuilder("P3");
    setupText.append("\n");
    setupText.append(pixels.get(0).size());
    setupText.append(" ");
    setupText.append(pixels.size());
    setupText.append("\n");
    setupText.append("255\n");

    return setupText;
  }

  private StringBuilder formatPixelValues(List<List<Pixel>> pixels) {
    StringBuilder formatted = new StringBuilder();
    for (List<Pixel> pixel : pixels) {
      for (int j = 0; j < pixels.get(0).size(); j++) {
        Pixel currentPixel = pixel.get(j);
        formatted.append(currentPixel.getRed());
        formatted.append("\n");
        formatted.append(currentPixel.getGreen());
        formatted.append("\n");
        formatted.append(currentPixel.getBlue());
        formatted.append("\n");
      }
    }

    return formatted;
  }

}
