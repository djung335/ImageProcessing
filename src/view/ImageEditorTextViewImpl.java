package src.view;

import java.io.IOException;

/**
 * View that renders text to a given destination.
 */
public class ImageEditorTextViewImpl implements ImageEditorTextView {

  private final Appendable out;

  /**
   * Constructs an ImageEditorTextViewImpl.
   *
   * @param out desitnation to output text to
   */
  public ImageEditorTextViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void renderMessage(String message) {
    try {
      out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("error displaying message");
    }
  }

}
