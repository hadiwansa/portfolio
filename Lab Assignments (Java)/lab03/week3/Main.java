package week3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/*
 * Main class for lab 3
 */
public class Main {

    /*
     * Main method for lab 3, to use as a driver for code.
     */
    public static void main(String[] args) throws IOException {

        MississaugaSewers ms = new MississaugaSewers(100);
        System.out.println("ms.sewers.size(): " + ms.sewers.size());

        Map<String, Sewer> res1 =  ms.groupEarliestAssetTypes();
        System.out.println("res1: " + res1.size());

        ArrayList<Sewer> res2 = ms.filterWardWorksYard("CLARKSON", (short) 2);
        ArrayList<Sewer> res3 = ms.filterWardWorksYard("CLARKSON", (short) 1);
        System.out.println("res2: " + res2.size());
        System.out.println("res3: " + res3.size());

        Map<String, Sewer> res4 = ms.groupEarliestAssetTypes();
        System.out.println("res4: " + res4.size());

        Sewer sewer = ms.fetchSewer(28400);
        System.out.println("sewer.assetId: " + sewer.assetId);
        System.out.println("sewer.assetType: " + sewer.assetType);

    }

}
