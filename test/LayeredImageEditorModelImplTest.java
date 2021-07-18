package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import src.model.LayeredImageEditorModel;
import src.model.LayeredImageEditorModelImpl;
import src.model.imageprocessing.filter.Blur;
import src.model.imagerep.LayerState;
import src.model.imagerep.LayerStateImpl;

/**
 * Test class for LayeredImageEditorModelImpl. Makes sure that functionality is implemented properly
 * including exceptions.
 */
public class LayeredImageEditorModelImplTest {

  LayeredImageEditorModel model = new LayeredImageEditorModelImpl();

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerWhenStringIsNull() {
    model.addLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerWhenNameAlreadyExists() {
    model.addLayer("bomb");
    model.addLayer("bomb");
  }

  @Test
  public void testAddLayer() {
    model.addLayer("bomb");
    model.addLayer("moss");
    assertEquals(2, this.model.getLayeredImage().size());
    assertTrue(this.model.getLayeredImage().containsKey("bomb"));
    assertTrue(this.model.getLayeredImage().containsKey("moss"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageWhenStringIsNull() {
    model.setLayerImage(null, this.model.generateCheckerboard(3, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageWhenImageIsNull() {
    this.model.addLayer("bomb");
    model.setLayerImage("bomb", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageWhenNameDoesNotExist() {
    model.addLayer("dolphin");
    model.setLayerImage("bomb", this.model.generateCheckerboard(3, 4));
  }

  @Test
  public void testSetLayerImage() {
    this.model.addLayer("grass");
    this.model.setLayerImage("grass", model.generateCheckerboard(3, 1));
    assertEquals(3, this.model.getTopmostVisibleImage().getPixels().size());
    assertEquals(3, this.model.getTopmostVisibleImage().getPixels().get(0).size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageWhenWrongDimensions() {
    this.model.addLayer("grass");
    this.model.addLayer("rain");
    this.model.setLayerImage("grass", model.generateCheckerboard(3, 1));
    this.model.setLayerImage("rain", model.generateCheckerboard(17, 3));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerWhenStringIsNull() {
    this.model.removeLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerWhenNameDoesNotExist() {
    this.model.removeLayer("chicago bulls");
  }

  @Test
  public void testRemoveLayer() {
    this.model.addLayer("grass");
    this.model.removeLayer("grass");
    assertEquals(0, this.model.getLayeredImage().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeVisibilityWhenStringIsNull() {
    this.model.changeVisibility(null, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeVisibilityWhenNameDoesNotExist() {
    this.model.changeVisibility("bomb", true);
  }

  @Test
  public void testChangeVisibility() {
    this.model.addLayer("grass");
    this.model.changeVisibility("grass", false);
    assertEquals(false, this.model.getLayeredImage().get("grass").getVisibility());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerWhenStringIsNull() {
    this.model.setCurrentLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerWhenNameDoesNotExist() {
    this.model.setCurrentLayer("bomb");
  }

  // TODO
  @Test
  public void testSetCurrentLayer() {
    this.model.addLayer("grainy");
    this.model.setCurrentLayer("grainy");
    this.model.applyImageProcessor(new Blur());
    assertEquals(0, this.model.getImage().getPixels().get(0).get(0).getBlue());
    assertEquals(0, this.model.getImage().getPixels().get(0).get(0).getRed());
    assertEquals(0, this.model.getImage().getPixels().get(0).get(0).getGreen());
  }

  @Test
  public void testGetLayeredImage() {
    model.addLayer("5");
    model.addLayer("4");
    model.addLayer("3");
    model.addLayer("2");
    model.addLayer("1");
    assertEquals(5, this.model.getLayeredImage().size());
    assertTrue(this.model.getLayeredImage().containsKey("1"));
    assertTrue(this.model.getLayeredImage().containsKey("2"));
    assertTrue(this.model.getLayeredImage().containsKey("3"));
    assertTrue(this.model.getLayeredImage().containsKey("4"));
    assertTrue(this.model.getLayeredImage().containsKey("5"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersWhenMapIsNull() {
    this.model.setLayers(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersWhenNameIsNull() {
    Map<String, LayerState> m = new HashMap<String, LayerState>();
    m.put("sepia", new LayerStateImpl(this.model.generateCheckerboard(4, 4), true, 0));
    m.put(null, new LayerStateImpl(this.model.generateCheckerboard(4, 4), false, 1));
    this.model.setLayers(m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersWhenLayerStateIsNull() {
    Map<String, LayerState> m = new HashMap<String, LayerState>();
    m.put("sepia", new LayerStateImpl(this.model.generateCheckerboard(4, 4), true, 0));
    m.put("garnish", null);
    this.model.setLayers(m);
  }

  // TODO
  @Test
  public void testSetLayers() {
    Map<String, LayerState> m = new HashMap<String, LayerState>();
    m.put("grass", new LayerStateImpl(model.generateCheckerboard(1, 1), true, 0));
    m.put("moss", new LayerStateImpl(model.generateCheckerboard(1, 1), false, 3));
    m.put("amit", new LayerStateImpl(model.generateCheckerboard(1, 1), true, 2));
    m.put("????", new LayerStateImpl(model.generateCheckerboard(1, 1), false, 1));
    model.setLayers(m);
    assertEquals(4, this.model.getLayeredImage().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostWhenNoImages() {
    this.model.getTopmostVisibleImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostWhenTopMostButNotVisible() {
    this.model.addLayer("six");
    this.model.changeVisibility("six", false);
    this.model.getTopmostVisibleImage();
  }

  @Test
  public void testGetTopMostVisibleImage() {
    this.model.addLayer("grass");
    this.model.setLayerImage("grass", this.model.generateCheckerboard(5, 4));
    this.model.addLayer("moss");
    this.model.changeVisibility("moss", false);
    assertEquals(10, this.model.getTopmostVisibleImage().getPixels().size());
    assertEquals(10, this.model.getTopmostVisibleImage().getPixels().get(0).size());

  }

}
