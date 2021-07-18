package src.model.imagerep;


/**
 * This interface represents a layer state, which is one layer.
 */
public interface LayerState {

  /**
   * Sets the image of this layer state.
   *
   * @param i the image to be set.
   * @throws IllegalArgumentException if the image is null.
   */
  void setImage(Image i) throws IllegalArgumentException;

  /**
   * Get the image of this layer state.
   *
   * @return the image of this layer state.
   */
  src.model.imagerep.Image getImage();

  /**
   * Set the visibility of this layer state.
   *
   * @param visible whether or not this layer is to be visible.
   */
  void setVisibility(boolean visible);

  /**
   * Get the visibility of this layer state.
   *
   * @return the visibility of this layer state.
   */
  boolean getVisibility();

  /**
   * Set the index of this layer state.
   *
   * @param index the index as an integer.
   */
  void setIndex(int index);

  /**
   * Get the index of this layer state.
   *
   * @return the index as an integer.
   */
  int getIndex();

}

