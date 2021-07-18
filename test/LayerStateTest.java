package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import src.model.LayeredImageEditorModel;
import src.model.LayeredImageEditorModelImpl;
import src.model.imagerep.LayerState;
import src.model.imagerep.LayerStateImpl;

/**
 * Test class for LayerState: unit tests to ensure that LayerState methods are working as intended.
 */
public class LayerStateTest {

  LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
  LayerState layerOne = new LayerStateImpl(model.generateCheckerboard(4, 4), true, 0);
  LayerState layerTwo = new LayerStateImpl(model.generateCheckerboard(4, 4), false, 1);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenImageIsNull() {
    LayerState badConstructor = new LayerStateImpl(null, false, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetImageWhenImageIsNull() {
    this.layerOne.setImage(null);

  }

  @Test
  public void testSetImage() {
    this.layerOne.setImage(this.model.generateCheckerboard(4, 4));
    assertEquals(8, this.layerOne.getImage().getPixels().get(0).size());
    assertEquals(8, this.layerOne.getImage().getPixels().size());

  }

  @Test
  public void testGetImage() {
    assertEquals(8, this.layerOne.getImage().getPixels().size());
    assertEquals(8, this.layerOne.getImage().getPixels().get(0).size());
    assertEquals(8, this.layerTwo.getImage().getPixels().size());
    assertEquals(8, this.layerTwo.getImage().getPixels().get(0).size());
  }

  @Test
  public void testSetVisibility() {
    this.layerOne.setVisibility(false);
    assertEquals(false, this.layerOne.getVisibility());
    this.layerTwo.setVisibility(true);
    assertEquals(true, this.layerTwo.getVisibility());
  }

  @Test
  public void getVisibility() {

    assertEquals(true, this.layerOne.getVisibility());
    assertEquals(false, this.layerTwo.getVisibility());
  }

  @Test
  public void setIndex() {
    this.layerOne.setIndex(16);
    assertEquals(16, this.layerOne.getIndex());
    this.layerTwo.setIndex(34);
    assertEquals(34, this.layerTwo.getIndex());
  }

  @Test
  public void getIndex() {
    assertEquals(0, this.layerOne.getIndex());
    assertEquals(1, this.layerTwo.getIndex());
  }
}