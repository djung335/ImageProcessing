package src.model;

import java.util.Map;
import src.model.imagerep.Image;
import src.model.imagerep.LayerState;

/**
 * Model for a layered image editor application. Implements all functionality required to add
 * layers, remove layers, set layers, and change visibility.
 */
public interface LayeredImageEditorModel extends ImageEditorModel {

  /**
   * Add a blank layer.
   *
   * @param layerName the name of the layer.
   * @throws IllegalArgumentException if the name is null, or the name already exists.
   */
  void addLayer(String layerName) throws IllegalArgumentException;

  /**
   * Set a layer to a given image.
   *
   * @param layerName the name of the layer.
   * @param i         the image to set the layer to.
   * @throws IllegalArgumentException if parameters are null, or if the name doesn't pertain to a
   *                                  layer, or if the image does not meet the required dimensions.
   */
  void setLayerImage(String layerName, Image i) throws IllegalArgumentException;

  /**
   * Remove a layer.
   *
   * @param layerName the name of the layer to be removed.
   * @throws IllegalArgumentException if argument is null, or the name does not pertain to any
   *                                  layer.
   */
  void removeLayer(String layerName) throws IllegalArgumentException;

  /**
   * Changes the visibility of a specific layer.
   *
   * @param layerName the name of the layer.
   * @param isVisible if the layer is to be visible or not.
   * @throws IllegalArgumentException if the layer name is null, or the layer name does not pertain
   *                                  to any layer.
   */
  void changeVisibility(String layerName, boolean isVisible) throws IllegalArgumentException;

  /**
   * Set the current layer.
   *
   * @param newCurrentLayerName the name of the layer you want to set the current to.
   * @throws IllegalArgumentException if the argument is null, or if the name doesn't pertain to any
   *                                  layer.
   */
  void setCurrentLayer(String newCurrentLayerName) throws IllegalArgumentException;

  /**
   * Gets a copy of the layer names and the corresponding layer states.
   *
   * @return the copy of the layer names and the corresponding layer states.
   */
  Map<String, LayerState> getLayeredImage();

  /**
   * Set the layers to a given map of names to layers.
   *
   * @param layers the map of names to layers.
   * @throws IllegalArgumentException if the argument is null, or if any names or layers are null.
   */
  void setLayers(Map<String, LayerState> layers) throws IllegalArgumentException;

  /**
   * Gets the topmost visible image.
   *
   * @return the topmost visible image.
   * @throws IllegalArgumentException if there is no topmost visible image.
   */
  Image getTopmostVisibleImage() throws IllegalArgumentException;

  /**
   * Returns current layer name.
   *
   * @return current layer name
   */
  String getCurrentLayerName();

  String getTopmostVisibleLayerName();
}
