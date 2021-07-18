# Assignment 5 : Image Processing

Dylan Pham and Daniel Jung

Description of our design:

- The purpose of the ImageProcessor interface is to represent a tool used to apply various types of
  image transformations to a given image. We used an interface in order to allow for future image
  processing functionality. If we were to want another image processor like clarendon, we could
  easily just make a class which would implement image processor and keep us from modifying existing
  code.
- The purpose of the AbstractImageProcessor is to abstract away common code between different
  implementations of ImageProcessor. For example, both filters and color transformations require a
  clamp method.
- The purpose of the AbstractColorTransformation class is to abstract away similarities between
  different kinds of color transformations. Though our current implementations do not have any
  similarities we anticipate that it would be useful to avoid duplicated code in the future.
- The purpose of the AbstractLinearColorTransformation class is to abstract away common code between
  different kinds of linear color transformations. The constructor for Grayscale and Sepia are very
  similar apart from the transformation matrix they use, so we abstracted the similarities into the
  abstract class constructor and called helper methods that are implemented in the Grayscale and
  Sepia class to get the corresponding transformation matrix.
- The purpose of the AbstractFilter class is to abstract away similarities between the different
  kinds of filters. Methods shared by filters such as processImage are implemented similarly, so
  creating this class allows us to remove duplicate code. The constructors for Blur and Sharpen are
  similar (only the matrix is different), so we used helper methods implemented in the respective
  classes which are then called in the AbstractFilter class.
- The purpose of Sepia, Grayscale, Blur and Sharpen is to apply their respective image
  transformation to an image.
- To represent our images, we used an interface called Image and an implementation of that interface
  through an ImageImpl class. The interface holds methods that are useful to an image. We used an
  interface Image because we anticipate that images can be represented in more than one way, so the
  interface will be implemented for future representations of images as well.
- The purpose of the Pixel class us to represent the pixels of an image with 3 private fields of
  integers to represent the red, blue, and green values. We chose to throw exceptions when the
  integer values are over 255 or under 0 because it seems unintuitive to allow anyone to create a
  pixel with invalid rgb values. We clamped the rgb values outside of the Pixel class and in the
  respective image processing methods to ensure valid rgb values.
- The purpose of the ImageConverter is to convert from an implementation of our Image class to a
  certain file type and vice versa. We used an interface in anticipation of future extensions to our
  image editor that might require us to read/write to non-PPM file types.
- The purpose of the PpmImageConverter is to convert from a PPM file to an Image and vice versa. The
  ability to convert to and from and Image enables us to have a standardized format in which we can
  apply image transformations.
- Coming to the model, we have an interface called ImageEditorModel with a class
  SimpleImageEditorModel which implements it. This model ensures that all the essential
  functionality such as exports, imports, and image processing can be correctly done. We use the
  interface because differences in how images are to be exported, imported, or processed are
  entirely possible and we wanted to be able to anticipate that.
- With the new functionality, we created a new interface called LayeredImageEditorModel with a class
  called LayeredImageEditorModelImpl. We were thinking of new ways of how to incorporate layers into
  our design, but we decided to make a new interface and a new model because we believe that layers
  are designed differently enough to warrant their own functionality. Our layered image editor model
  can create, add, and set layers, as well as do all the things a normal image editor model can do
  like creating a checkerboard, which we were able to do through extension of the simple image
  editor model and its interface in respective areas.

Assignment 7:
Some new changes that we brought to our design were the creation of a new graphical view through
Swing. To support a new type of view, we created the ImageEditorGraphicalView interface which
contains methods that the view should have, and we had the ImageEditorGraphicalViewImpl class
implement that interface.

We also created a new controller that helps the model and the view work with each other. The
Features interface contains methods that tell the model what things to do, which our
NewestController class implements.


For the extra credit, we created two new classes: downsize and mosaic. Because our design for the
image processing was good, we could simply have these two classes implement the image processing
interface. Usually, we could have these image processors extend or implement respective sub
interfaces, but since these two classes are unique and aren't really classified as filters or
transformations in the way our other filters and transformations work, we thought it was appropriate
to have them directly implement ImageProcessor interface.

completed parts of the assignment:
- new support for graphical view
- EC: downscale as well as mosaic
- jar file that supports command line args

Image citations:

- Statue of Liberty image : “Statue of Liberty.” Wikipedia, Wikimedia Foundation, 9 June 2021,
  simple.wikipedia.org/wiki/Statue_of_Liberty.


- Amit Shesh image: “Professor Amit Shesh.” Khoury College of Computer Sciences – Khoury
  College, www.ccs.neu.edu/home/ashesh/.

- “Popular Movie Dog Breeds and How to Groom Them.” QC Pet Studies, 10 July
  2020, www.doggroomingcourse.com/2020/02/popular-movie-dog-breeds-and-how-to-groom-them/.
