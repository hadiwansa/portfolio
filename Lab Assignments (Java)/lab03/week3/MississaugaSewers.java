package week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MississaugaSewers
 */
public class MississaugaSewers {

    public ArrayList<Sewer> sewers = new ArrayList<>();

    /**
     * MississaugaSewers Constructor
     *
     * Reads nLines from the file sewers-mississauga.csv (after skipping the header row).
     * Uses each row of data to instantiate a Sewer object, and places the new Sewer in
     * this.sewers.
     * Note that the file sewers-mississauga.csv should be in the week3 directory.
     * Do not move the file sewers-mississauga.csv when you implement this method.
     *
     * @param nLines, the number of sewers to read from the input file (sewers-mississauga.csv).
     */
    public MississaugaSewers(int nLines) throws IOException {

        assert  nLines > 0 && nLines <= 82036;

        // read csv file
        try (BufferedReader br = new BufferedReader(new FileReader("week3/sewers-mississauga.csv"))) {
            String line;
            br.readLine(); // Skip header row

            int count = 0;
            while ((line = br.readLine()) != null && count < nLines) {
                String[] values = line.split(",");
                // Instantiate a Sewer object and add it to the list
                Sewer sewer = new Sewer(Long.parseLong(values[0]), values[1], values[2], values[3], Short.parseShort(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));
                sewers.add(sewer);
                count++;
            }
        }
    }

    /**
     * Creates a mapping of assetTypes onto Sewer objects.
     * Each key in the map should be a given Sewer "assetType".
     * Each value in the map should be Sewer of that "assetType"
     * with the **earliest** install date for that assetType.
     *
     * @return a Map of assetType to the Sewer with the earliest installDate for that assetType.
     * Note that you need to filter out Sewers with null installDate
     */
    public Map<String, Sewer> groupEarliestAssetTypes(){
        Map<String, Sewer> earliestAssets = new HashMap<>();

        for (Sewer sewer : sewers) {
            if (sewer.installDate == null) {
                continue; // Skip sewers with null installDate
            }

            Sewer earliest = earliestAssets.getOrDefault(sewer.assetType, null);
            if (earliest == null || sewer.compareTo(earliest) < 0) {
                earliestAssets.put(sewer.assetType, sewer);
            }
        }

        return earliestAssets;
    }

    /**
     * Creates a list of Sewers in a given ward and worksYard.
     *
     * @param worksYard the worksYard to filter by
     * @param ward the ward to filter by
     * @return a list of Sewers in the given worksYard and ward
     */
    public ArrayList<Sewer> filterWardWorksYard(String worksYard, short ward){
        ArrayList<Sewer> filteredSewers = new ArrayList<>();

        for (Sewer sewer : sewers) {
            if (sewer.worksYard.equals(worksYard) && sewer.ward == ward) {
                filteredSewers.add(sewer);
            }
        }

        return filteredSewers;
    }

    /**
     * Method to fetch a Sewer with a given assetId from this.sewers
     *
     * @param assetId the assetId to fetch
     * @return the Sewer with the given assetId, or null if no such Sewer exists
     */
    public Sewer fetchSewer(long assetId){
        for (Sewer sewer : sewers) {
            if (sewer.assetId == assetId) {
                return sewer;
            }
        }
        return null;
    }

}
