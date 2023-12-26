package week5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Sewer class.  */
public class Sewer {

    public enum Status {
        ALL_GOOD, NEEDS_REPAIR // Sewer Status Options
    }

    private final Long assetId; //the ID of the asset

    private final String assetType, worksYard;
    private Date installDate;
    private final short ward;
    private final double lon, lat;

    private Status status;  // Sewer Status

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
    public Sewer(Long assetId, String assetType, String installDate, String worksYard, short ward, double lon, double lat) {
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
        this.status = Status.ALL_GOOD;
    }

    /* Useful Getters */
    public Long getAssetId() {
        return assetId;
    }

    public String getAssetType() {
        return assetType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @param lat
     * @param lon
     * @return shortest distance over the earth's surface using the
     * haversine formula: https://en.wikipedia.org/wiki/Haversine_formula
     */
    public double distanceTo(Double lat, Double lon) {

        double lat1 = this.lat;
        double lon1 = this.lon;
        double lat2 = lat;
        double lon2 = lon;

        final double R = 6371e3;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // in metres
    }
}
