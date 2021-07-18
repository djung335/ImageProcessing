package test.controller;

import org.junit.Test;
import src.controller.NewestController;
import src.model.LayeredImageEditorModel;
import src.model.LayeredImageEditorModelImpl;
import src.view.ImageEditorGraphicalView;
import src.view.ImageEditorGraphicalViewImpl;

/**
 * Test class for NewestController; unit tests to make sure all functionality is working as
 * intended.
 */
public class NewestControllerTest {

  LayeredImageEditorModel model = new LayeredImageEditorModelImpl();
  ImageEditorGraphicalView view = new ImageEditorGraphicalViewImpl();
  NewestController controller = new NewestController(model, view);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenModelNull() {
    new NewestController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWhenViewNull() {
    new NewestController(model, null);
  }


}
