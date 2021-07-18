package src.controller.imageio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import src.model.imagerep.Image;
import src.model.imagerep.LayerState;
import src.model.imagerep.LayerStateImpl;

/**
 * Convertor to and from our uniquely designed layered image format. Helps import and export
 * layeredimages.
 */
public class LayeredImageFolderConverter implements ImageConverter<Map<String, LayerState>> {

  @Override
  public void writeImage(Map<String, LayerState> layers, String folderName, String folderEnding)
      throws IllegalStateException, IllegalArgumentException {
    if (layers == null || folderName == null || folderEnding == null) {
      throw new IllegalArgumentException("one of args is null");
    }

    if (!folderName.endsWith("." + folderEnding)) {
      throw new IllegalArgumentException("invalid file extension for a layered image");
    }

    for (String layerName : layers.keySet()) {
      if (layerName == null) {
        throw new IllegalArgumentException("layer name is null");
      } else if (layers.get(layerName) == null) {
        throw new IllegalArgumentException("layer state is null");
      }
    }

    StringBuilder layeredImageDetails = new StringBuilder();
    new File(folderName).mkdirs();
    for (String layerName : layers.keySet()) {
      new src.controller.imageio.StandardImageFileConverter()
          .writeImage(layers.get(layerName).getImage(),
              folderName + "/" + layerName + ".png", "png");

      layeredImageDetails.append(layers.get(layerName).getIndex()); // index
      layeredImageDetails.append("\n");
      layeredImageDetails.append(layers.get(layerName).getVisibility()); // visiibility
      layeredImageDetails.append("\n");
      layeredImageDetails.append(layerName); // layer name
      layeredImageDetails.append("\n");
    }

    try {
      FileWriter myWriter = new FileWriter(folderName + "/details.txt");
      myWriter.write(layeredImageDetails.toString());
      myWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("error writing layered image details to file");
    }

  }

  @Override
  public Map<String, LayerState> readImage(String folderName) throws IllegalArgumentException {
    Map<String, LayerState> layers = new HashMap<String, LayerState>();

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(folderName + "/" + "details.txt"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("folder does not exist");
    }

    try {
      while (sc.hasNext()) {
        int index = sc.nextInt();
        boolean visiibility = Boolean.parseBoolean(sc.next());
        String layerName = sc.next();

        Image i = new StandardImageFileConverter().readImage(folderName + "/" + layerName + ".png");
        layers.put(layerName, new LayerStateImpl(i, visiibility, index));

      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("details file in folder supplied is formatted improperly");
    }

    return layers;
  }
}
