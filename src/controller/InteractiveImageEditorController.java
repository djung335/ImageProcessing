package src.controller;

/**
 * Controller that supports reading/writing images in addition to an interactive UI.
 */
public interface InteractiveImageEditorController extends ImageEditorController {

  /**
   * Runs interactive image editor ui that user can write commands to.
   */
  void startProgram();
}
