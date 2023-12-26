package week4;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import week4.sewertools.Sewer;
import week4.sewertools.SewerInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Event Handler for View
 */
public class SewerGroupbyEventHandler implements EventHandler<MouseEvent> {

    private ComboBox groupSelect; //select box ref
    private SewerViewer sewerView; //the sewer view

    /**
     * Constructor
     *
     * @param view reference to SewerView
     */
    public SewerGroupbyEventHandler(SewerViewer view) {
        this.sewerView = view;
        this.groupSelect = view.getGroupSelect();
    }


    /**
     *  Handle a mouse event (i.e. a button click)!  This routine will need to:
     *  Update circle colors based on the combo-box selection by using each circle id which corresponds to the asset id
     *  Finally **save the image of the anchor pane to a file** using SewerViewer.saveAnchorPaneImage(groupName)
     *  Replace the groupName parameter with the name of the group that is selected in the combo box.
     *
     * @param mouseEvent the mouse event
    */
    @Override
    public void handle(MouseEvent mouseEvent) {
        // Get the selected group from the ComboBox
        String selectedGroup = (String) groupSelect.getValue();

        // Initialize a map to hold assetId and corresponding Color
        Map<Long, Color> assetColorMap = new HashMap<>();

        // Get the list of sewers
        List<Sewer> sewers = sewerView.getSewers();

        // Check which group is selected and populate the assetColorMap accordingly
        if ("Asset Type".equals(selectedGroup)) {
            assetColorMap = SewerInfo.groupByAssetType(sewers);
        } else if ("Works Yard".equals(selectedGroup)) {
            assetColorMap = SewerInfo.groupByWorksYard(sewers);
        } else if ("Ward".equals(selectedGroup)) {
            assetColorMap = SewerInfo.groupByWard(sewers);
        } else if ("Install Date".equals(selectedGroup)) {
            assetColorMap = SewerInfo.groupByInstallDateGroup(sewers);
        } else {
            // If no option is selected, set color to RED for all sewers
            for (Sewer sewer : sewers) {
                assetColorMap.put(sewer.getAssetId(), Color.RED);
            }
        }


        // Clear existing circles from the AnchorPane
        AnchorPane anchorRoot = sewerView.getAnchorRoot();
        Stack<Object> undoStack = sewerView.getUndoStack();
        if (undoStack != null) {
            for (Object obj : undoStack) {
                anchorRoot.getChildren().remove(obj);
            }
            undoStack.clear();
        }

        // Get the boundaries
        double[] boundaries = sewerView.getBoundaries();

        // Draw new circles on the AnchorPane based on the assetColorMap
        for (Sewer t : sewers) {
            double[] coords = t.getLoc().getCoords();
            double yval = (double) sewerView.getHeight() - sewerView.getHeight() * ((coords[1] - boundaries[2]) / (boundaries[0] - boundaries[2]));
            double xval = (double) sewerView.getWidth() - sewerView.getWidth() * ((coords[0] - boundaries[3]) / (boundaries[1] - boundaries[3]));

            if (yval < sewerView.getHeight() && yval > 0 && xval < sewerView.getWidth() && xval > 0) {
                Circle circle = new Circle();
                circle.setCenterX(xval);
                circle.setCenterY(yval);
                circle.setRadius(3);
                circle.setId(String.valueOf(t.getAssetId()));
                circle.setFill(assetColorMap.getOrDefault(t.getAssetId(), Color.RED));
                anchorRoot.getChildren().add(circle);
                undoStack.add(circle);
            }
        }

        // Save the image of the anchor pane to a file
        try {
            sewerView.saveAnchorPaneImage(selectedGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
