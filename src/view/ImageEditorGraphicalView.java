package src.view;

import java.awt.image.BufferedImage;
import java.util.List;
import src.controller.Features;

/**
 * This is the interface for an image editor graphical view. It holds methods that a view should be
 * able to support.
 */
public interface ImageEditorGraphicalView {

  /**
   * Adds features to this view.
   *
   * @param f the given features.
   */
  void addFeatures(Features f);

  /**
   * Sets the image of this view.
   *
   * @param i the image to set.
   */
  void setImage(BufferedImage i);

  /**
   * Resets the focus of this view.
   */
  void resetFocus();

  /**
   * Sets the status bar message of this view.
   *
   * @param message the status bar message to be shown.
   */
  void setStatusBarMessage(String message);

  /**
   * Gets the filepath.
   *
   * @param description the description as a String.
   * @param filters     the filters that were applied.
   * @return the filepath as a String.
   */
  String getFilePath(String description, String... filters);

  /**
   * Refreshes the menu, given the layer names and the features.
   *
   * @param layerNames the given layer names.
   * @param f          the features.
   */
  void refreshMenu(List<String> layerNames, Features f);

  /**
   * Gets the layer name.
   *
   * @return the layer name as a String.
   */
  String getLayerName();

  /**
   * Sets the topmost visible layer name.
   *
   * @param name the name of the layer.
   */
  void setTopmostVisibleLayerName(String name);

  /**
   * Sets the current layer name.
   *
   * @param name the name of the layer.
   */
  void setCurrentLayerName(String name);
}
