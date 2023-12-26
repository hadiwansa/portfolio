package week3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Sewer
 */
public class Sewer implements Comparable<Sewer> {

    public long assetId;//the asset ID
    public String assetType, worksYard;
    public Date installDate;
    public short ward;
    public double lon, lat;

    /**
     * Sewer Constructor
     *
     * @param assetId, the asset ID
     * @param assetType, the type of asset (e.g. manhole, catchbasin)
     * @param installDate, the date of installation
     * @param worksYard,  the water treatment yard that the sewer exits to
     * @param ward, the ward that maintains the sewer
     * @param lon, longitude coordinate
     * @param lat, latitude coordinate
     */
    public Sewer(long assetId, String assetType, String installDate, String worksYard, short ward, double lon, double lat) {
        this.assetId = assetId;
        this.assetType = assetType;
        this.worksYard = worksYard;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            this.installDate = sdformat.parse(installDate);
        } catch (ParseException e) {
            this.installDate = null;
        }
        this.ward = ward;
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * Compares two sewers based on installation dates
     *
     * @param o another Sewer object or dates are equal
     * @return 0 if either install dats is null, 1 if this.installDate is greater than o.installDate, else -1
     */
    @Override
    public int compareTo(Sewer o) {
        if(this.installDate == null || o.installDate == null){
            return 0;
        }
        else{
            return this.installDate.compareTo(o.installDate);
        }
    }

    /**
     * Calculates the Haversine distance between two sewers.
     *
     * In your calculations, note that the radius of the earth is 6371000 metres.
     *
     * More on the Haversine formula: https://en.wikipedia.org/wiki/Haversine_formula
     *
     * @param o another Sewer object
     * @return distance (in metres) over the earth's surface using the Haversine formula
     */
    public double distanceTo(Sewer o){
        // Radius of the earth in meters
        double R = 6371000.0;

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(this.lat);
        double lon1 = Math.toRadians(this.lon);
        double lat2 = Math.toRadians(o.lat);
        double lon2 = Math.toRadians(o.lon);

        // Haversine formula
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate distance
        double distance = R * c;

        return distance;
    }
}
