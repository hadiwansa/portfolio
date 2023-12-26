package week5;

import week5.observer.ObservableSewer;
import week5.observer.SewerObserver;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* MississaugaSewers class.  MississaugaSewers are Observable! */
public class MississaugaSewers extends ObservableSewer {

    private List<SewerObserver> maintainerList = new ArrayList<>(); //observers of sewers

    /**
     * MississaugaSewers Constructor
     *
     * Reads nLines from the file sewers-mississauga.csv (after skipping the header row).
     * Uses each row of data to instantiate a Sewer object, and places the new Sewer in
     * this.sewers.
     * Do not move the file sewers-mississauga.csv when you implement this method.
     *
     * @param nLines, the number of sewers to read from the input file (sewers-mississauga.csv).
     */

    public MississaugaSewers(int nLines) throws IOException {

        assert nLines > 0 && nLines <= 82323;

        // read csv file
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("sewers-mississauga.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] nextRecord = line.split(",");
                if (count > 0) {
                    try {
                        Sewer sewer = new Sewer(Long.parseLong(nextRecord[0]), nextRecord[1], nextRecord[2], nextRecord[3],
                                Short.parseShort(nextRecord[4]), Double.parseDouble(nextRecord[5]), Double.parseDouble(nextRecord[6]));
                        this.setObservableState(sewer);
                    } catch (Exception e) {
                        System.out.println("Error: " + nextRecord[0]);
                    }
                }
                count++;
            }
        }
    }

    /**
     * Register an individual as an observer of sewer statuses
     *
     * @param o the observer of the sewers
     */
    @Override
    public void register(SewerObserver o) {
        maintainerList.add(o);
    }

    /**
     * Unregister an individual as an observer of sewer statuses
     *
     * @param o the observer of the sewers
     */
    @Override
    public void unregister(SewerObserver o) {
        maintainerList.remove(o);
    }


    /**
     * Notify all observers that a given sewer is in need of attention
     *
     * @param sewer the sewer in need of attention
     */
    @Override
    public void notifyObservers(Sewer sewer) {
        for (SewerObserver o : maintainerList) {
            o.update(sewer);
        }
    }

    /**
     * Fetch sewer with a given asset ID
     *
     * @param assetId the assetId to fetch
     * @return the Sewer with the given assetId
     */
    public Sewer fetchSewer(long assetId){
        return this.getSewers().get(assetId);
    }
}
