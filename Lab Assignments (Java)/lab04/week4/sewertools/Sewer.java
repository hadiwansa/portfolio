package week4.sewertools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Sewer class, to hold sewer details
 */
public class Sewer{

    private final long assetId;
    private final String assetType, worksYard;
    private Date installDate;
    private final short ward;
    private final Location loc;

    /**
     * Sewer Constructor
     * This is kind of a long constructor, eh?
     * We will soon learn some design patterns to make it a little less unweildy ...
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
        this.loc = new Location(lat,lon);
    }

     /**
     * getInstallDateGroup
     * Transforms installDate to a group ID that ranges from 0 to 6
     * Useful for visualization of groups by install date
     */
    public int getInstallDateGroup(){
        if(this.installDate == null){
            return 0;
        }
        int year = this.installDate.getYear()+1900;

        if(year >= 1750 && year < 1800){ //old!!
            return 1;
        }
        else if (year >= 1800 && year < 1850){
            return 2;
        }
        else if(year >= 1850 && year < 1900){
            return 3;
        }
        else if(year >= 1900 && year < 1950){
            return 4;
        }
        else if(year >= 1950 && year < 2000){
            return 5;
        }
        else if(year >= 2000 && year < 2050){
            return 6;
        }
        return -1;
    }

    /**
     * Useful Getters for attributes
     */
    public long getAssetId() {
        return assetId;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getWorksYard() {
        return worksYard;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public short getWard() {
        return ward;
    }

    public Location getLoc() {
        return loc;
    }

    /*
     * Pretty print the Sewer
     */
    @Override public String toString() {
        return "(" + this.getAssetType()
                + ", " + this.getAssetId()
                + ", " + this.getLoc()
                + ") ";
    }

    /*
     * @see Object.equals()
     */
    @Override public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Sewer)) {
            return false;
        }

        Sewer that = (Sewer) thatObject;
        return this.loc == that.loc;
    }

    /*
     * @see Object.hashCode()
     */
    @Override public int hashCode() {
        return (int) (7*this.loc.getLon() + 31*this.loc.getLat());
    }

}
