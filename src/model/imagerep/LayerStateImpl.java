package src.model.imagerep;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a layer state implementation. It inherits the layer state interface, which
 * has methods pertaining to getting and setting layer state fields.
 */
public class LayerStateImpl implements LayerState {

  private src.model.imagerep.Image i;
  private boolean visible;
  private int index;


  /**
   * Creates an instance of a layer state implementation.
   *
   * @param i       the image for this layer state.
   * @param visible if this layer state is to visible or not.
   * @param index   the index of this layer state as an integer.
   * @throws IllegalArgumentException if the image is null.
   */
  public LayerStateImpl(src.model.imagerep.Image i, boolean visible, int index)
      throws IllegalArgumentException {
    if (i == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    this.i = i;
    this.visible = visible;
    this.index = index;

  }

  @Override
  public void setImage(Image i) throws IllegalArgumentException {
    if (i == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    List<List<Pixel>> copyOfPixels = new ArrayList<List<Pixel>>();
    List<List<Pixel>> original = i.getPixels();

    for (List<Pixel> l : original) {
      List rowOfPixels = new ArrayList();

      for (Pixel p : l) {
        rowOfPixels.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
      }
      copyOfPixels.add(rowOfPixels);

    }

    this.i = new src.model.imagerep.ImageImpl(copyOfPixels);
  }

  @Override
  public src.model.imagerep.Image getImage() {
    List<List<Pixel>> copyOfPixels = new ArrayList<List<Pixel>>();
    List<List<Pixel>> original = i.getPixels();

    for (List<Pixel> l : original) {
      List rowOfPixels = new ArrayList();

      for (Pixel p : l) {
        rowOfPixels.add(new PixelImpl(p.getRed(), p.getGreen(), p.getBlue()));
      }
      copyOfPixels.add(rowOfPixels);

    }
    return new ImageImpl(copyOfPixels);
  }

  @Override
  public void setVisibility(boolean visible) {
    this.visible = visible;
  }

  @Override
  public boolean getVisibility() {
    return visible;
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public int getIndex() {
    return index;
  }


}
