package src.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.model.imageprocessing.ImageProcessor;
import src.model.imagerep.Image;
import src.model.imagerep.ImageImpl;
import src.model.imagerep.LayerState;
import src.model.imagerep.LayerStateImpl;
import src.model.imagerep.Pixel;
import src.model.imagerep.PixelImpl;

/**
 * A layered image editor model that allows for adding, removing, and setting layers, as well as
 * changing visibility of layers.
 */
public class LayeredImageEditorModelImpl extends ImageEditorModelImpl implements
    LayeredImageEditorModel {

  // cannot be final for any.
  private Map<String, LayerState> layers;
  private String currentLayerName;
  private int nextTopMostLayerIndex;
  private int standardWidth;
  private int standardHeight;

  /**
   * Creates an instance of a LayeredImageEditorModelImpl object.
   */
  public LayeredImageEditorModelImpl() {
    this.image = null;
    this.layers = new HashMap<String, LayerState>();
    this.currentLayerName = null;
    this.nextTopMostLayerIndex = 0;
    this.standardWidth = 0;
    this.standardHeight = 0;
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("layer name is null");

    } else if (this.layers.containsKey(layerName)) {
      throw new IllegalArgumentException("layer name already exists");
    } else {
      layers.put(layerName,
          new LayerStateImpl(generateCheckerboard(1, 1), true, nextTopMostLayerIndex));
      nextTopMostLayerIndex++;
    }
  }

  // since pointers are used for the image, maybe be careful for that
  @Override
  public void setLayerImage(String layerName, Image i) throws IllegalArgumentException {
    if (layerName == null || i == null) {
      throw new IllegalArgumentException("some inputs are null");
    }

    if (!layers.containsKey(layerName)) {
      throw new IllegalArgumentException("layer with given name does not exist");
    } else {

      List<List<Pixel>> copyOfPixels = new ArrayList<List<Pixel>>();
      List<List<Pixel>> original = i.getPixels();

      if (this.standardHeight == 0 && this.standardWidth == 0) {
        this.standardWidth = original.get(0).size();
        this.standardHeight = original.size();
      } else if (this.standardWidth != original.get(0).size() || this.standardHeight != original
          .size()) {
        throw new IllegalArgumentException("this image does not meet the proper dimension sizes");
      }

      for (List<Pixel> l : original) {
        List rowOfPixels = new ArrayList();

        for (Pixel p : l) {
          rowOfPixels.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
        }
        copyOfPixels.add(rowOfPixels);

      }

      layers.get(layerName).setImage(new ImageImpl(copyOfPixels));

    }
  }

  @Override
  public void removeLayer(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("layer name given is null");
    } else {
      LayerState state = layers.remove(layerName);
      // in case of removing current layer
      if (layerName.equals(currentLayerName)) {
        currentLayerName = null;
      }
      if (state == null) {
        throw new IllegalArgumentException("trying to remove nonexistent layer");
      } else {

        updateIndices(state.getIndex());
      }
    }
    if (this.layers.isEmpty()) {
      this.standardHeight = 0;
      this.standardHeight = 0;
    }

  }

  private void updateIndices(int indexOfRemovedLayer) {
    for (String layerName : layers.keySet()) {
      LayerState s = layers.get(layerName);
      if (s.getIndex() > indexOfRemovedLayer) {
        s.setIndex(s.getIndex() - 1);
      }
    }
  }

  @Override
  public void changeVisibility(String layerName, boolean isVisible)
      throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("layer name given is null");
    }

    if (!layers.containsKey(layerName)) {
      throw new IllegalArgumentException("layer with given name does not exist");
    } else {
      layers.get(layerName).setVisibility(isVisible);
    }
  }

  @Override
  public Map<String, LayerState> getLayeredImage() {
    Map<String, LayerState> copy = new HashMap<String, LayerState>();

    for (String s : this.layers.keySet()) {
      LayerState oldLayerState = this.layers.get(s);
      LayerState newLayerState = new LayerStateImpl(oldLayerState.getImage(),
          oldLayerState.getVisibility(),
          oldLayerState.getIndex());

      copy.put(s, newLayerState);
    }

    return copy;
  }

  @Override
  public void setLayers(Map<String, LayerState> layers) throws IllegalArgumentException {
    if (layers == null) {
      throw new IllegalArgumentException("layers cannot be null");
    }

    for (String s : layers.keySet()) {
      if (s == null) {
        throw new IllegalArgumentException("names cannot be null");
      }
    }
    for (LayerState ls : layers.values()) {
      if (ls == null) {
        throw new IllegalArgumentException("layer state cannot be null");
      }
    }

    this.layers = layers; // copy input, then set
    this.currentLayerName = null;
  }

  @Override
  public void setCurrentLayer(String layerToSwitchTo) throws IllegalArgumentException {
    if (layerToSwitchTo == null) {
      throw new IllegalArgumentException("given name is null");
    }

    if (!layers.containsKey(layerToSwitchTo)) {
      throw new IllegalArgumentException("layer to set current to does not exist");
    } else {
      this.image = layers.get(layerToSwitchTo).getImage();
      this.currentLayerName = layerToSwitchTo;
    }
  }

  @Override
  public void setImage(Image i) throws IllegalArgumentException {
    if (i == null) {
      throw new IllegalArgumentException("given image is null");
    }

    if (currentLayerName == null) {
      throw new IllegalArgumentException(
          "layer not yet created; cannot set image to nonexistent layer");
    } else {
      List<List<Pixel>> original = i.getPixels();
      List<List<Pixel>> copy = new ArrayList<List<Pixel>>();
      for (List<Pixel> l : original) {
        List rowOfPixels = new ArrayList();

        for (Pixel p : l) {
          rowOfPixels.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
        }
        copy.add(rowOfPixels);

      }

      this.image = new ImageImpl(copy);
      // for the method call below, a copy is created in that method for the original
      // image.
      setLayerImage(currentLayerName, i);
    }
  }

  @Override
  public void applyImageProcessor(ImageProcessor ip) throws IllegalArgumentException {
    if (ip == null) {
      throw new IllegalArgumentException("processor is null");
    } else {
      image = ip.processImage(image);
      this.setImage(image);
    }
  }

  @Override
  public Image getTopmostVisibleImage() throws IllegalArgumentException {
    int greatestIndexSoFar = -1;
    Image topmostImageSoFar = null;
    for (String layerName : layers.keySet()) {
      LayerState s = layers.get(layerName);
      if (s.getVisibility() && s.getIndex() > greatestIndexSoFar) {
        greatestIndexSoFar = s.getIndex();
        topmostImageSoFar = s.getImage();
      }
    }

    if (topmostImageSoFar == null) {
      throw new IllegalArgumentException("image has no visible layers");
    } else {
      return topmostImageSoFar;
    }
  }

  @Override
  public String getCurrentLayerName() {
    return currentLayerName;
  }

  @Override
  public String getTopmostVisibleLayerName() {
    int greatestIndexSoFar = -1;
    String topmostImageSoFar = null;
    for (String layerName : layers.keySet()) {
      LayerState s = layers.get(layerName);
      if (s.getVisibility() && s.getIndex() > greatestIndexSoFar) {
        greatestIndexSoFar = s.getIndex();
        topmostImageSoFar = layerName;
      }
    }

    return topmostImageSoFar;
  }
}
