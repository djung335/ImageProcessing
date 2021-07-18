package src;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import src.controller.InteractiveLayeredImageEditorController;
import src.controller.NewestController;
import src.model.LayeredImageEditorModelImpl;
import src.view.ImageEditorGraphicalViewImpl;

/**
 * This class represents the NewestRunner, which runs the image editor program.
 */
public class NewestRunner {

  /**
   * Runs the image editor program.
   *
   * @param args the arguments provided by the computer.
   */
  public static void main(String[] args) {
    if (args.length == 1) {
      switch (args[0]) {
        case "-text":
          new InteractiveLayeredImageEditorController(new LayeredImageEditorModelImpl(),
              new InputStreamReader(System.in),
              System.out).startProgram();
          break;
        case "-interactive":
          NewestController controller = new NewestController(new LayeredImageEditorModelImpl(),
              new ImageEditorGraphicalViewImpl());
          break;
        default:
          System.out.println("invalid arguments");
      }
    } else if (args.length == 2 && args[0].equals("-script")) {
      String pathToScriptFile = args[1];
      try {
        new InteractiveLayeredImageEditorController(new LayeredImageEditorModelImpl(),
            new StringReader(
                Files.readString(Path.of(pathToScriptFile), StandardCharsets.US_ASCII)),
            System.out).startProgram();
      } catch (IOException e) {
        System.out.println("invalid script file path given");
      }
    } else {
      System.out.println("invalid arguments");
    }
  }
}
