package week3;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SewerTest {

    @Test
    void fetchSewerTest() throws IOException, ParseException {
        MississaugaSewers ms = new MississaugaSewers(100);
        Sewer sewer = ms.fetchSewer(28400);
        assertEquals(28400, sewer.assetId);
        assertEquals("Catchbasin Manhole", sewer.assetType);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date expectedDate = sdf.parse("1952/01/01");
        assertEquals(0, sewer.installDate.compareTo(expectedDate));

        assertEquals("CLARKSON", sewer.worksYard);
        assertEquals(1, sewer.ward);
    }

    @Test
    void distanceTest() throws IOException {

        MississaugaSewers ms = new MississaugaSewers(100);
        Sewer s1 = ms.fetchSewer(28400);
        Sewer s2 = ms.fetchSewer(2030138);
        assertEquals(9331.570354035523,s1.distanceTo(s2),1);
    }

    @Test
    void groupEarliestAssetTypesTest() throws IOException, ParseException {
        MississaugaSewers ms = new MississaugaSewers(82036);
        Map<String, Sewer> res = ms.groupEarliestAssetTypes();
        assertEquals(11,res.size());

        Sewer defaultSewer = new Sewer(-1,"notype","2023/07/07",null,(short)-1,0,0);
        assertEquals(0,res.getOrDefault("Pipe Inlet",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1955/01/01")));
        assertEquals(0,res.getOrDefault("Catchbasin Manhole",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1952/01/01")));
        assertEquals(0,res.getOrDefault("Plug",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1955/01/01")));
        assertEquals(0,res.getOrDefault("Outlet",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1946/01/01")));
        assertEquals(0,res.getOrDefault("Manhole",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1753/01/01")));
        assertEquals(0,res.getOrDefault("Double Catchbasin",defaultSewer).installDate.compareTo(new SimpleDateFormat("yyyy/MM/dd").parse("1753/01/01")));
    }

    //write your own JUnit Test(s) here!

    @Test
    void distanceToSameSewerTest() throws IOException {
        MississaugaSewers ms = new MississaugaSewers(100);
        Sewer s1 = ms.fetchSewer(28400);
        assertEquals(0, s1.distanceTo(s1), 1);
    }


    @Test
    void filterWardWorksYardEmptyTest() throws IOException {
        MississaugaSewers ms = new MississaugaSewers(100);
        ArrayList<Sewer> filteredSewers = ms.filterWardWorksYard("NON_EXISTENT_YARD", (short)999);
        assertTrue(filteredSewers.isEmpty());
    }

    @Test
    void fetchFirstSewerTest() throws IOException {
        MississaugaSewers ms = new MississaugaSewers(100);
        Sewer s1 = ms.fetchSewer(28400);
        Sewer s2 = new Sewer(28400, "DifferentType", "2023/01/01", "DifferentYard", (short)2, 0.0, 0.0);
        ms.sewers.add(s2);
        assertEquals(s1, ms.fetchSewer(28400));
    }
}