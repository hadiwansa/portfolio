package week5;

import week5.observer.SewerObserver;

import java.util.ArrayList;
import java.util.List;


/* Maintainer class.  Maintainers are SewerObservers! */
public class Maintainer implements SewerObserver {
    private final List<String> specializations; //what the maintainers can work on
    private final Double lat, lon; //where they are
    private final float operationRadius; // in meters

    private List<String> todoList; //what they are to do

    /**
     * Maintainer Constructor
     *
     * @param specializations, a list of specializations
     * @param operationRadius, the radius within which they work
     * @param lon, longitude coordinate
     * @param lat, latitude coordinate
     */
    public Maintainer(List<String> specializations, Double lat, Double lon, float operationRadius) {
        this.specializations = specializations;
        this.lat = lat;
        this.lon = lon;
        this.operationRadius = operationRadius;
        this.todoList = new ArrayList<>();
    }

    /**
     * Receive a notification that a sewer may need attention.
     * If the sewer is in need of repair (i.e. it has a status of NEEDS_REPAIR), and
     * the given maintainer is specialized to work on its asset type, the
     * method should add the sewer's Asset ID (in string form) to the
     * Maintainer's list of "To Dos". If the sewer is fine and is in the Maintainer's
     * To Do list, it should be removed from the list.
     *
     * @param sewer the sewer needing attention
     */
    @Override
    public void update(Sewer sewer) {
        double distance = sewer.distanceTo(this.lat, this.lon);
        if (sewer.getStatus() == Sewer.Status.NEEDS_REPAIR &&
                this.specializations.contains(sewer.getAssetType()) &&
                distance <= this.operationRadius) {
            this.todoList.add(sewer.getAssetId().toString());
        } else {
            this.todoList.remove(sewer.getAssetId().toString());
        }
    }

    public List<String> getTodoList() {
        return this.todoList;
    }
}
