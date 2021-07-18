package src.controller;

/**
 * An interface to represent the features of the controller.
 */
public interface Features {

  void exitProgram();

  /**
   * Apply blur to the model's photo.
   */
  void applyBlur();

  /**
   * Apply sharpen to the model's photo.
   */
  void applySharpen();

  /**
   * Apply sepia to the model's photo.
   */
  void applySepia();

  /**
   * Apply grayscale to the model's photo.
   */
  void applyGrayscale();

  /**
   * Apply mosaic to the model's photo.
   */
  void applyMosaic();

  /**
   * Apply downsize to the model's photo.
   */
  void applyDownsize();

  /**
   * Add a layer.
   */
  void addLayer();

  /**
   * Remove a layer with the given name.
   *
   * @param name the given name.
   */
  void removeLayer(String name);

  /**
   * Sets the current layer to given name.
   *
   * @param name the given name of the layer.
   */
  void setCurrentLayer(String name);

  /**
   * Sets the visibility of a layer.
   *
   * @param name    the name of the layer to change the visibility.
   * @param visible the visibility status.
   */
  void setLayerVisibility(String name, boolean visible);

  /**
   * Saves the image.
   */
  void saveImage();

  /**
   * Save the layers.
   *
   * @param filePath the filepath as a String.
   */
  void saveLayers(String filePath);

  /**
   * Loads images.
   */
  void loadImage();

  /**
   * Loads the layers from a filepath.
   *
   * @param filePath the given filepath as a String.
   */
  void loadLayers(String filePath);

  /**
   * Loads in and executes a script from a given filepath.
   *
   * @param filePath the given filepath as a String.
   */
  void loadAndExecuteScript(String filePath);
}
