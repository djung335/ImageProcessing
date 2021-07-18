package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import src.controller.InteractiveLayeredImageEditorController;
import src.model.LayeredImageEditorModelImpl;

/**
 * Runs image editor program with text UI.
 */
public class Runner {

  /**
   * Starts the program to run the image editor program.
   *
   * @param args built in arguments
   * @throws IOException if program cannot run correctly
   */
  public static void main(String[] args) throws IOException {
    StringBuilder output1 = new StringBuilder();
    InteractiveLayeredImageEditorController c = new InteractiveLayeredImageEditorController(
        new LayeredImageEditorModelImpl(),
        new StringReader(Files.readString(Path.of("res/script1.txt"), StandardCharsets.US_ASCII)),
        output1);
    c.startProgram();
    try (PrintWriter out = new PrintWriter("script1output.txt")) {
      out.println(output1);
    }

    StringBuilder output2 = new StringBuilder();
    InteractiveLayeredImageEditorController c2 = new InteractiveLayeredImageEditorController(
        new LayeredImageEditorModelImpl(),
        new StringReader(Files.readString(Path.of("res/script2.txt"), StandardCharsets.US_ASCII)),
        output2);
    c2.startProgram();
    try (PrintWriter out = new PrintWriter("script2output.txt")) {
      out.println(output2);
    }
  }
}
