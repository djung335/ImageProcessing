package src.controller;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import src.controller.imageeditorcommands.ReadImage;
import src.controller.imageeditorcommands.WriteImage;
import src.model.LayeredImageEditorModel;
import src.model.LayeredImageEditorModelImpl;
import src.model.imageprocessing.filter.Blur;
import src.model.imageprocessing.filter.Sharpen;
import src.model.imageprocessing.transformation.Grayscale;
import src.model.imageprocessing.transformation.Sepia;
import src.view.ImageEditorTextView;
import src.view.ImageEditorTextViewImpl;

/**
 * Controller that allows for read/write layered images and an interactive image editor UI.
 */
public class InteractiveLayeredImageEditorController extends SimpleImageEditorController
    implements InteractiveImageEditorController {

  private final LayeredImageEditorModel model;
  private final Readable commands;
  private final Appendable outputDest;
  private final ImageEditorTextView view;

  /**
   * Constructs an InteractiveLayeredImageEditorController.
   *
   * @param model      model that implements all functionality required for image editor
   *                   applications
   * @param commands   commands for controller to execute
   * @param outputDest destination that controller sends output to
   * @throws IllegalArgumentException if any arguments are null
   */
  public InteractiveLayeredImageEditorController(LayeredImageEditorModel model, Readable commands,
      Appendable outputDest) throws IllegalArgumentException {
    if (model == null || commands == null || outputDest == null) {
      throw new IllegalArgumentException("one of arguments is null");
    }

    this.model = model;
    this.commands = commands;
    this.outputDest = outputDest;
    this.view = new ImageEditorTextViewImpl(outputDest);
  }

  /**
   * Constructs an InteractiveLayeredImageEditorController without any arguments.
   */
  public InteractiveLayeredImageEditorController() {
    this.model = new LayeredImageEditorModelImpl();
    this.commands = new InputStreamReader(System.in);
    this.outputDest = System.out;
    this.view = new ImageEditorTextViewImpl(outputDest);
  }

  @Override
  public void startProgram() throws IllegalArgumentException {
    Scanner scan = new Scanner(commands);

    Map<String, Function<Scanner, Runnable>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("read", s -> new ReadImage(s.next(), model));
    knownCommands.put("write", s -> new WriteImage(s.next(), model));
    knownCommands.put("checkerboard", s -> () -> model.setImage(model.generateCheckerboard(5, 4)));

    knownCommands.put("blur", s -> () -> model.applyImageProcessor(new Blur()));
    knownCommands.put("sharpen", s -> () -> model.applyImageProcessor(new Sharpen()));
    knownCommands.put("sepia", s -> () -> model.applyImageProcessor(new Sepia()));
    knownCommands.put("bw", s -> () -> model.applyImageProcessor(new Grayscale()));

    knownCommands.put("add", s -> () -> model.addLayer(s.next()));
    knownCommands.put("remove", s -> () -> model.removeLayer(s.next()));
    knownCommands.put("current", s -> () -> model.setCurrentLayer(s.next()));
    knownCommands.put("invisible", s -> () -> model.changeVisibility(s.next(), false));
    knownCommands.put("visible", s -> () -> model.changeVisibility(s.next(), true));
    knownCommands.put("#", s -> () -> s.nextLine());

    while (scan.hasNext()) {
      Runnable c;
      String in = scan.next();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        view.renderMessage("quit application\n");
        return;
      }

      Function<Scanner, Runnable> cmd = knownCommands.getOrDefault(in, null);
      if (cmd == null) {
        view.renderMessage(".....command not supported; try again\n");
      } else {
        try {
          c = cmd.apply(scan);
          c.run();
          view.renderMessage(".....command successfully executed\n");
        } catch (IllegalArgumentException e) {
          view.renderMessage(".....try another command: " + e + " \n");
        } catch (IllegalStateException e) {
          view.renderMessage(".....error reading/writing to file: " + e + " \n");
        }
      }
    }
  }

}
