package week5.observer;

import week5.Sewer;

import java.util.HashMap;

/* Observable Collection of Sewers */
public abstract class ObservableSewer {

    private HashMap<Long, Sewer> sewers = new HashMap<>(); //Sewers to observe (assetId to Sewer map)

    public abstract void register(SewerObserver o); //register a sewer observer
    public abstract void unregister(SewerObserver o); //unregister a sewer observer

    /**
     * Notify all observers that a given sewer is in need of attention
     *
     * @param sewer, the sewer in need of attention
     */
    public abstract void notifyObservers(Sewer sewer);

    /**
     * setObservableState
     *
     * This method will accept a Sewer. When it is called, 1) add the
     * incoming sewer to the list of observed sewers, and 2) notify all
     * observers that a new sewer that may need or have received maintenance has
     * been added to the list.
     *
     * @param s the sewer to add to the list
     */
    public void setObservableState(Sewer s){
        this.sewers.put(s.getAssetId(), s);
        notifyObservers(s);
    }

    /**
     * getSewers
     */
    public HashMap<Long, Sewer> getSewers(){
        return this.sewers;
 }

}
