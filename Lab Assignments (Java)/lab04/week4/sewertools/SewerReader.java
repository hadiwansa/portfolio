package week4.sewertools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SewerReader class, to read sewers from file
 */
public class SewerReader {

    public static List<Sewer> readSewersFromCSV(String fName, int nLines) throws IOException {

        assert  nLines > 0 && nLines <= 82323;
        List<Sewer> sewers = new ArrayList<>();

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] nextRecord = line.split(",");
                if (count == nLines + 1) break;
                if (count > 0) {
                    try {
                        Sewer sewer = new Sewer(Long.parseLong(nextRecord[0]), nextRecord[1], nextRecord[2], nextRecord[3],
                                Short.parseShort(nextRecord[4]), Double.parseDouble(nextRecord[6]), Double.parseDouble(nextRecord[5]));
                        sewers.add(sewer);
                    } catch (Exception e) {
                        System.out.println("Error: " + nextRecord[0]);
                    }
                }
                count++;
            }
        }

        return sewers;
    }
}
