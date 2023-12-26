package week4;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import week4.sewertools.Sewer;
import week4.sewertools.SewerInfo;
import week4.sewertools.SewerReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SewerTest {

    @Test
    void groupByAssetTypeTest() throws IOException, ParseException {
        String fName = "./assets/sewers-mississauga.csv";
        List<Sewer> ms = SewerReader.readSewersFromCSV(fName,100);
        Map<Long, Color> res = SewerInfo.groupByAssetType(ms);
        assertEquals(100,res.size());
        assertEquals(Color.YELLOW,res.get((long)51118));
        assertEquals(Color.ORANGE,res.get((long)8937));
    }


    @Test
    void groupByWorksYardTest() throws IOException {
        String fName = "./assets/sewers-mississauga.csv";
        List<Sewer> ms = SewerReader.readSewersFromCSV(fName, 100);
        Map<Long, Color> res = SewerInfo.groupByWorksYard(ms);
        assertEquals(100,res.size());
        assertEquals(Color.ORANGE,res.get((long)51118));
        assertEquals(Color.ORANGE,res.get((long)8937));
    }

    @Test
    void groupByWardTest() throws IOException, ParseException {
        String fName = "./assets/sewers-mississauga.csv";

        List<Sewer> ms = SewerReader.readSewersFromCSV( fName, 100);
        Map<Long, Color> res = SewerInfo.groupByWard(ms);
        assertEquals(100,res.size());
        assertEquals(Color.BLUE,res.get((long)51118));
     }

    @Test
    void groupByInstallDateGroupTest() throws IOException {
        String fName = "./assets/sewers-mississauga.csv";
        List<Sewer> ms = SewerReader.readSewersFromCSV(fName,100);
        Map<Long, Color> res = SewerInfo.groupByInstallDateGroup(ms);
        assertEquals(100,res.size());
        assertEquals(Color.ORANGE,res.get((long)8937));
    }

    //write your own JUnit Test(s) here!

    @Test
    void groupByAssetTypeTest_EmptyList() throws IOException, ParseException {
        List<Sewer> emptyList = new ArrayList<>();
        Map<Long, Color> res = SewerInfo.groupByAssetType(emptyList);
        assertEquals(0, res.size());
    }

    @Test
    void groupByWorksYardTest_EmptyList() throws IOException {
        List<Sewer> emptyList = new ArrayList<>();
        Map<Long, Color> res = SewerInfo.groupByWorksYard(emptyList);
        assertEquals(0, res.size());
    }

    @Test
    void groupByWardTest_EmptyList() throws IOException, ParseException {
        List<Sewer> emptyList = new ArrayList<>();
        Map<Long, Color> res = SewerInfo.groupByWard(emptyList);
        assertEquals(0, res.size());
    }

    @Test
    void groupByInstallDateGroupTest_EmptyList() throws IOException {
        List<Sewer> emptyList = new ArrayList<>();
        Map<Long, Color> res = SewerInfo.groupByInstallDateGroup(emptyList);
        assertEquals(0, res.size());
    }


}
