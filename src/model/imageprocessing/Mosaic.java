package src.model.imageprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * A class to represent the Mosaic functionality. It inherits methods from the image processor
 * interface.
 */
public class Mosaic implements ImageProcessor {

  private final int seeds;

  /**
   * Creates an instance of the Mosaic class.
   *
   * @param seeds the number of random seeds for this image processing.
   */
  public Mosaic(int seeds) {
    this.seeds = seeds;
  }

  @Override
  public Image processImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image is null.");
    }

    // get pixels makes a copy for us
    List<List<Pixel>> copy = image.getPixels();
    int width = copy.get(0).size();
    int height = copy.size();

    if (seeds > width * height || seeds < 0) {
      throw new IllegalArgumentException("wrong number of seeds");
    }

    List<List<Pixel>> mosaic = new ArrayList<List<Pixel>>();

    Set<Pixel> seeds = new HashSet<Pixel>();
    List<Pair> pairs = new ArrayList<Pair>();

    int counter = this.seeds;
    Random r = new Random();
    while (counter > 0) {

      int randomX = r.nextInt(height);
      int randomY = r.nextInt(width);
      if (!seeds.contains(copy.get(randomX).get(randomY))) {
        seeds.add(copy.get(randomX).get(randomY));
        pairs.add(new Pair(randomX, randomY));
        counter--;
      }
    }

    Map<Pixel, Pixel> pixelToCenter = new HashMap<Pixel, Pixel>();

    Map<Pixel, List<Pixel>> clusters = new HashMap<Pixel, List<Pixel>>();
    for (int i = 0; i < copy.size(); i++) {
      for (int j = 0; j < copy.get(i).size(); j++) {
        double minDistance = Integer.MAX_VALUE;
        Pair seedCoordinate = new Pair(0, 0);
        for (Pair p : pairs) {

          double distance = Math
              .sqrt((Math.pow((p.getX() - i), 2) + Math.pow((p.getY() - j), 2)));

          if (distance < minDistance) {
            minDistance = distance;
            seedCoordinate = new Pair(p.getX(), p.getY());
          }

        }
        Pixel pixelOriginal = copy.get(i).get(j);
        Pixel pixelSeed = copy.get(seedCoordinate.getX()).get(seedCoordinate.getY());

        pixelToCenter.put(pixelOriginal, pixelSeed);
        if (!clusters.containsKey(pixelSeed)) {
          clusters.put(pixelSeed, new ArrayList<Pixel>(Arrays.asList(pixelOriginal)));
        } else {
          clusters.get(pixelSeed).add(pixelOriginal);

        }
      }
    }

    Map<Pixel, Pixel> clusterAverage = new HashMap<Pixel, Pixel>();
    // now iterate through the map and map the centroid pixel to the correct Pixel after average.
    for (Pixel p : clusters.keySet()) {
      int redValue = 0;
      int greenValue = 0;
      int blueValue = 0;

      for (Pixel pixel : clusters.get(p)) {
        redValue = redValue + pixel.getRed();
        greenValue = greenValue + pixel.getGreen();
        blueValue = blueValue + pixel.getBlue();

      }
      redValue = redValue / clusters.get(p).size();
      greenValue = greenValue / clusters.get(p).size();
      blueValue = blueValue / clusters.get(p).size();
      clusterAverage.put(p, new PixelImpl(redValue, greenValue, blueValue));


    }

    // finally, create the new image
    for (int i = 0; i < copy.size(); i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < copy.get(i).size(); j++) {
        Pixel p = clusterAverage.get(pixelToCenter.get(copy.get(i).get(j)));
        row.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
      }
      mosaic.add(row);
    }

    return new ImageImpl(mosaic);
  }

  /**
   * A class to represent a pair of integers.
   */
  private class Pair {

    private final int x;
    private final int y;

    /**
     * Creates a pair of integers.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private Pair(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
      return this.x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
      return this.y;
    }

  }


}

