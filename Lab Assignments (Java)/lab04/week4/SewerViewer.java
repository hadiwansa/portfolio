package week4;

import week4.sewertools.Sewer;
import week4.sewertools.SewerReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This is the main JavaFX program.
 */
public class SewerViewer extends Application {

    private Stack<Object> undoStack; //undo stack, to facilitate drawing/erasing of graphics objects
    private List<Sewer> sewers; //list of sewers in the city
    private AnchorPane anchorRoot; //root of the scene graph

    //UI elements on a sewer view we will want other methods to access
    private final ComboBox<Object> groupSelect; //the list of sewer options

    private final int h = 700; //dimensions of the map for display
    private final int w = 1000;
    private final int ALL_LINES = 82323;


    //UTM COORDINATES
    private final double [] boundaries = {43.7398, -79.8033, 43.4783, -79.5431}; //map boundaries
    int nLines = ALL_LINES; // change this accordingly
    String mapName = "./assets/map-mississauga.png";
    String fName = "./assets/sewers-mississauga.csv";

    /**
     * Make a SewerViewer, to view sewer infrastructure in the city
     */
    public SewerViewer() {

        //get the sewer list from the file
        try {
            String filename =  fName; //change this accordingly
            sewers = SewerReader.readSewersFromCSV(fName, nLines);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        //get the sewer types
        final Set<String> groups = new HashSet<String>(Arrays.asList("Asset Type","Works Yard","Ward", "Install Date"));
        List<String> sortedList = new ArrayList<>(groups);
        Collections.sort(sortedList);
        sortedList.add(0, "No Group");

        //create the UI element to hold the sewer types
        this.groupSelect = new ComboBox<>(FXCollections
                .observableArrayList(sortedList));
        this.groupSelect.setId("groupSelect");

    }

    /** Main method */
    public static void main(String[] args) {
        launch(args);
    }

    /** Start the visualization */
    public void start(Stage primaryStage) {

        //load the map to visualize
        FileInputStream input = null;
        try {
            input = new FileInputStream(mapName); //change this if needed
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert input != null;
        Image image = new Image(input, w, h, false, false);
        ImageView iv = new ImageView(image); //place the map on the UI
        iv.setFitHeight(h);
        iv.setFitWidth(w);

        anchorRoot = new AnchorPane();
        anchorRoot.getChildren().add(iv); //attach the map to the scene graph
        anchorRoot.setId("anchorRoot");

        //create undo stack for visualized objects
        double[] coords;
        undoStack = new Stack<>();

        //add circles to visualization at locations of sewers
        for (Sewer t: sewers) {
            coords = t.getLoc().getCoords();

            double yval = (double) h - h*((coords[1] - boundaries[2])/(boundaries[0] - boundaries[2]));
            double xval = (double) w - w*((coords[0] - boundaries[3])/(boundaries[1] - boundaries[3]));

            if (yval < h & yval > 0 & xval < w & xval > 0) {
                //System.out.println(t.getName());
                Circle circle = new Circle();
                circle.setCenterX(xval);
                circle.setCenterY(yval);
                circle.setRadius(3);
                circle.setId(String.valueOf(t.getAssetId()));
                circle.setFill(Color.RED);
                anchorRoot.getChildren().add(circle); //attach each circle to the scene graph
                undoStack.add(circle); //add the circle to the undo stack
            }
            else{
                System.out.println("Sewer " + t.getAssetId() + " is out of bounds");
            }
        }

        //add button to UI
        Button filterSewers = new Button("Show Sewers");
        filterSewers.setId("filterSewers");

        //add event handler to UI
        SewerGroupbyEventHandler sewerHandler = new SewerGroupbyEventHandler(this);
        filterSewers.addEventHandler(MouseEvent.MOUSE_CLICKED, sewerHandler);

        //set UI elements within a horizontal box
        HBox hbox = new HBox(groupSelect, filterSewers);
        HBox.setHgrow(groupSelect, Priority.ALWAYS);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        //set horizontal box within a virtical box and attach to the scene graph
        VBox vbox = new VBox(hbox, anchorRoot);

        //attach all scene graph elements to the scene
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene); //set the scene ...
        primaryStage.show(); //... and ... go.

    }

    /** Save image to a file.  Don't change this!
     *
     * @param groupName dictates name of file to save
     */
    public void saveAnchorPaneImage(String groupName) throws IOException {
        WritableImage image = this.anchorRoot.snapshot(new SnapshotParameters(), null);

        File file = new File(groupName.replace(" ","_") + ".png");

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }

    /** Get latitude and longitude boundaries of display
     *
     * @return array of doubles holding map boundaries (urx, ury, llx, lly)
     */
    public double[] getBoundaries() {
        return boundaries;
    }

    /** Get height of display
     *
     * @return height of display in px
     */
    public int getHeight() {
        return h;
    }

    /** Get width of display
     *
     * @return width of display in px
     */
    public int getWidth() {
        return w;
    }

    /** Get Anchor Root, used for event handling
     *
     * @return anchorRoot
     */
    public AnchorPane getAnchorRoot() {
        return anchorRoot;
    }

    /**
     * Get getGroupSelect, useful for event handling
     *
     * @return ChoiceBox
     */
    public ComboBox getGroupSelect() {
        return this.groupSelect;
    }

    /** Get getUndoStack, useful for event handling
     *
     * @return undoStack
     */
    public Stack getUndoStack() { return this.undoStack; }

    /** Get Sewer List, useful for event handling
     *
     * @return sewers
     */
    public List<Sewer> getSewers() { return this.sewers; }
}