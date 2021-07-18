package src.controller.imageio;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * Supports converting to and from standard image file formats: png, jpeg (NOT PPM).
 */
public class StandardImageFileConverter implements ImageConverter<Image> {

  @Override
  public void writeImage(Image image, String dest, String fileExtension)
      throws IllegalStateException, IllegalArgumentException {
    if (image == null || dest == null || fileExtension == null) {
      throw new IllegalArgumentException("one of arguments is null");
    } else if (!dest.endsWith("." + fileExtension)) {
      throw new IllegalArgumentException("supplied destination file does not match file extension");
    }

    List<List<Pixel>> pixelsToWrite = image.getPixels();
    BufferedImage imgToWrite = new BufferedImage(pixelsToWrite.get(0).size(), pixelsToWrite.size(),
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < pixelsToWrite.size(); i++) {
      for (int j = 0; j < pixelsToWrite.get(0).size(); j++) {
        Pixel current = pixelsToWrite.get(i).get(j);
        Color c = new Color(current.getRed(), current.getGreen(), current.getBlue());
        imgToWrite.setRGB(j, i, c.getRGB());
      }
    }

    try {
      ImageIO.write(imgToWrite, fileExtension, new File(dest));
    } catch (IOException e) {
      throw new IllegalStateException("error writing image");
    }

  }

  @Override
  public Image readImage(String src) throws IllegalArgumentException, IllegalStateException {
    if (src == null) {
      throw new IllegalArgumentException("src to read from is null");
    }

    try {
      List<List<Pixel>> pixels = new ArrayList<List<Pixel>>();
      BufferedImage bf = ImageIO.read(new File(src));

      for (int i = 0; i < bf.getHeight(); i++) {
        ArrayList<Pixel> row = new ArrayList<Pixel>();
        for (int j = 0; j < bf.getWidth(); j++) {
          Color c = new Color(bf.getRGB(j, i));
          row.add(new PixelImpl(c.getRed(), c.getGreen(), c.getBlue()));
        }
        pixels.add(row);
      }

      return new ImageImpl(pixels);
    } catch (IOException e) {
      throw new IllegalStateException("error reading image");
    }

  }

}
