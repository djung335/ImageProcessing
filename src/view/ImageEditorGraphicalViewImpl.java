package src.view;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import src.controller.Features;

/**
 * Represents the image editor graphical view implementation. It inherits methods from image editor
 * graphical view.
 */
public class ImageEditorGraphicalViewImpl extends JFrame implements ImageEditorGraphicalView {

  private final JMenuItem sharpen;
  private final JMenuItem blur;
  private final JMenuItem sepia;
  private final JMenuItem grayscale;
  private final JMenu mosaic;
  private final JTextField addMosaicTextField;
  private final JMenu downsize;



  private final JTextField addLayerTextField;
  private final JButton addLayerButton;
  private final JMenu setLayer;
  private final JMenu removeLayer;
  private final JMenu setLayerVisible;
  private final JMenu setLayerInvisible;

  private final JMenuItem importMenuItem;
  private final JMenuItem exportMenuItem;
  private final JMenuItem loadScriptMenuItem;
  private final JMenuItem exportLayersMenuItem;
  private final JMenuItem importLayersMenuItem;

  private final JPanel mainPanel;
  private final JLabel statusBar;

  private final JLabel picLabel;

  private final JLabel topmostVisibleLayerNameLabel;
  private final JLabel currentLayerNameLabel;

  /**
   * Constructs an instance of an ImageEditorGraphicalViewImpl.
   */
  public ImageEditorGraphicalViewImpl() {
    this.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.setTitle("Image Editor");
    this.setSize(600, 600);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit on close

    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.PAGE_AXIS));
    this.add(this.mainPanel);

    JMenuBar menuBar = new JMenuBar();
    JMenu imageBar = new JMenu("Image");
    JMenu layerBar = new JMenu("Layer");
    JMenu readWriteBar = new JMenu("Import/Export");
    menuBar.add(imageBar);
    menuBar.add(layerBar);
    menuBar.add(readWriteBar);
    this.mainPanel.add(menuBar);

    this.blur = new JMenuItem("Blur");
    this.sharpen = new JMenuItem("Sharpen");
    this.sepia = new JMenuItem("Sepia");
    this.grayscale = new JMenuItem("Grayscale");
    this.mosaic = new JMenu("Mosaic");
    this.addMosaicTextField = new JTextField("number of seeds");
    this.mosaic.add(this.addMosaicTextField);
    this.downsize = new JMenu("Downsize");
    JTextField addDownsizeTextField = new JTextField("width and height scale");
    this.downsize.add(addDownsizeTextField);

    JMenu addLayer = new JMenu("Add Layer");
    this.addLayerTextField = new JTextField("layer name");
    addLayer.add(addLayerTextField);
    addLayerButton = new JButton("add layer");
    addLayer.add(addLayerButton);
    this.setLayer = new JMenu("Set Layer");
    this.removeLayer = new JMenu("Remove Layer");
    this.setLayerVisible = new JMenu("Set Layer Visible");
    this.setLayerInvisible = new JMenu("Set Layer Invisible");

    this.importMenuItem = new JMenuItem("Import Image");
    this.exportMenuItem = new JMenuItem("Export Image");
    this.exportLayersMenuItem = new JMenuItem("Export Layers");
    this.importLayersMenuItem = new JMenuItem("Import Layers");
    this.loadScriptMenuItem = new JMenuItem("Load Script");

    imageBar.add(this.blur);
    imageBar.add(this.sharpen);
    imageBar.add(this.sepia);
    imageBar.add(this.grayscale);
    imageBar.add(this.mosaic);
    imageBar.add(this.downsize);

    layerBar.add(addLayer);
    layerBar.add(this.setLayer);
    layerBar.add(this.removeLayer);
    layerBar.add(this.setLayerInvisible);
    layerBar.add(this.setLayerVisible);

    readWriteBar.add(this.importMenuItem);
    readWriteBar.add(this.exportMenuItem);
    readWriteBar.add(this.loadScriptMenuItem);
    readWriteBar.add(this.exportLayersMenuItem);
    readWriteBar.add(this.importLayersMenuItem);

    this.statusBar = new JLabel("<status bar>");
    this.mainPanel.add(this.statusBar);

    topmostVisibleLayerNameLabel = new JLabel("no topmost visible layer");
    currentLayerNameLabel = new JLabel("no current layer set");
    this.mainPanel.add(topmostVisibleLayerNameLabel);
    this.mainPanel.add(currentLayerNameLabel);

    picLabel = new JLabel();
    JPanel imagePanel = new JPanel();
    mainPanel.add(imagePanel);
    imagePanel.add(new JScrollPane(picLabel));

    this.setVisible(true);
  }

  @Override
  public void setTopmostVisibleLayerName(String name) {
    topmostVisibleLayerNameLabel.setText(name);
  }

  @Override
  public void setCurrentLayerName(String name) {
    currentLayerNameLabel.setText(name);
  }

  @Override
  public void addFeatures(Features features) {

    addLayerButton.addActionListener(evt -> features.addLayer());
    addMosaicTextField.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // nothing here
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          features.applyMosaic();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // nothing here
      }
    });

    // image editing stuff
    blur.addActionListener(evt -> features.applyBlur());
    sharpen.addActionListener(evt -> features.applySharpen());
    sepia.addActionListener(evt -> features.applySepia());
    grayscale.addActionListener(evt -> features.applyGrayscale());
    mosaic.addActionListener(evt -> features.applyMosaic());
    downsize.addActionListener(evt -> features.applyDownsize());

    // import/export stuff
    importMenuItem.addActionListener(evt -> features.loadImage());
    exportMenuItem.addActionListener(evt -> features.saveImage());
    importLayersMenuItem
        .addActionListener(evt -> features.loadLayers(getFilePath(null, null)));
    exportLayersMenuItem.addActionListener(evt -> features.saveLayers(getFilePath(null, null)));
    loadScriptMenuItem
        .addActionListener(evt -> features.loadAndExecuteScript(getFilePath("Text files", "txt")));
  }

  @Override
  public void refreshMenu(List<String> newLayerNames, Features f) {
    setLayer.removeAll();
    removeLayer.removeAll();
    setLayerVisible.removeAll();
    setLayerInvisible.removeAll();

    for (String name : newLayerNames) {
      JMenuItem layerSelector1 = new JMenuItem(name);
      setLayer.add(layerSelector1);
      layerSelector1.addActionListener(evt -> f.setCurrentLayer(name));

      JMenuItem layerSelector2 = new JMenuItem(name);
      removeLayer.add(layerSelector2);
      layerSelector2.addActionListener(evt -> f.removeLayer(name));

      JMenuItem layerSelector3 = new JMenuItem(name);
      setLayerVisible.add(layerSelector3);
      layerSelector3.addActionListener(evt -> f.setLayerVisibility(name, true));

      JMenuItem layerSelector4 = new JMenuItem(name);
      setLayerInvisible.add(layerSelector4);
      layerSelector4.addActionListener(evt -> f.setLayerVisibility(name, false));
    }

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  @Override
  public String getLayerName() {
    // should show text field pop up when done
    //    return "place holder";
    return addLayerTextField.getText();
  }

  @Override
  public void setImage(BufferedImage i) {
    if (i != null) {
      picLabel.setIcon(new ImageIcon(i));
    } else {
      picLabel.setIcon(null);
    }

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public String getFilePath(String filterDescription, String... extensions) {
    final JFileChooser fchooser = new JFileChooser(".");
    if (filterDescription == null || extensions == null) {
      fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    } else {
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          filterDescription, extensions);
      fchooser.setFileFilter(filter);
    }
    int retvalue = fchooser.showOpenDialog(ImageEditorGraphicalViewImpl.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }

    return null;
  }

  @Override
  public void setStatusBarMessage(String newMessage) {
    if (newMessage != null && statusBar != null) {
      statusBar.setText(newMessage);
    }
  }
}


