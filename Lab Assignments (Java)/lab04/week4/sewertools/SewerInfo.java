package week4.sewertools;


import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SewerInfo class, to conduct queries on sets of sewers
 */
public class SewerInfo {

    /**
     * Group Sewers by Asset Type.
     * Use getColorByAssetType to get rendering colors for each Asset Type
     *
     * @param sewers is a list of Sewer objects
     * @return a map of assetId onto color, where color is determined by the assetType
     */
    public static Map<Long, Color> groupByAssetType(List<Sewer> sewers){
        Map<Long, Color> map = new HashMap<>();
        for (Sewer sewer : sewers) {
            map.put(sewer.getAssetId(), getColorByAssetType(sewer.getAssetType()));
        }
        return map;
    }

    /**
     * Group Sewers by Works Yard
     * Use getColorByWorksYard to get rendering colors for each Asset Type
     *
     * @param sewers is a list of Sewer objects
     * @return a map of assetId to color, where the color is determined by the worksYard
     */
    public static Map<Long,Color> groupByWorksYard(List<Sewer> sewers){
        Map<Long, Color> map = new HashMap<>();
        for (Sewer sewer : sewers) {
            map.put(sewer.getAssetId(), getColorByWorksYard(sewer.getWorksYard()));
        }
        return map;
    }

    /**
     * Group Sewers by Ward
     * Use getColorByWard to get rendering colors for each Asset Type
     *
     * @param sewers is a list of Sewer objects
     * @return a map of assetId to color, where the color is determined by the ward
     */
    public static Map<Long,Color> groupByWard(List<Sewer> sewers){
        Map<Long, Color> map = new HashMap<>();
        for (Sewer sewer : sewers) {
            map.put(sewer.getAssetId(), getColorByWard(sewer.getWard()));
        }
        return map;
    }

    /**
     * Group Sewers by Install Date
     * Use getColorByWard to get rendering colors for each Asset Type
     * 
     * @param sewers is a list of Sewer objects
     * @return a map of each assetId to color, where color is determined by installDate
     */
    public static Map<Long,Color> groupByInstallDateGroup(List<Sewer> sewers){
        Map<Long, Color> map = new HashMap<>();
        for (Sewer sewer : sewers) {
            map.put(sewer.getAssetId(), getColorByInstallDateGroup(sewer.getInstallDateGroup()));
        }
        return map;
    }


    /**
     * getColorByAssetType
     *
     * @param assetType The type of asset
     * @return the color of the asset
     */
    public static Color getColorByAssetType(String assetType){
        return switch (assetType) {
            case "Pipe Inlet" -> Color.BLACK;
            case "Catchbasin Manhole" -> Color.BLUE;
            case "Plug" -> Color.GREEN;
            case "Outlet" -> Color.YELLOW;
            case "Manhole" -> Color.ORANGE;
            case "Double Catchbasin" -> Color.CYAN;
            case "FDC Manhole" -> Color.MAGENTA;
            case "OGS Manhole" -> Color.PINK;
            case "Connection" -> Color.LIGHTGRAY;
            case "Ditch Inlet" -> Color.DARKGRAY;
            default -> Color.RED;
        };
    }

    /**
     * getColorByWorksYard
     *
     * @param worksYard The works yard
     * @return the color of the asset
     */
    public static Color getColorByWorksYard(String worksYard){
        return switch (worksYard) {
            case "MALTON" -> Color.BLUE;
            case "MAVIS" -> Color.GREEN;
            case "MEADOWVALE" -> Color.YELLOW;
            case "CLARKSON" -> Color.ORANGE;
            default -> Color.RED;
        };
    }

    /**
     * getColorByWard
     *
     * @param ward The ward
     * @return the color of the asset
     */
    public static Color getColorByWard(short ward){
        return switch (ward) {
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.YELLOW;
            case 4 -> Color.ORANGE;
            case 5 -> Color.CYAN;
            case 6 -> Color.MAGENTA;
            case 7 -> Color.PINK;
            case 8 -> Color.LIGHTGRAY;
            case 9 -> Color.DARKGRAY;
            case 10 -> Color.WHITE;
            case 11 -> Color.GRAY;
            default -> Color.RED;
        };
    }


    /**
     * getColorByInstallDateGroup
     * See the Sewer class for information about group IDs
     *
     * @param installDateGroup The install date group ID (0 to 6)
     * @return the color of the asset
     */
    public static Color getColorByInstallDateGroup(int installDateGroup){
        return switch (installDateGroup) {
            case 0 -> Color.BLACK;
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.YELLOW;
            case 4 -> Color.ORANGE;
            case 5 -> Color.CYAN;
            case 6 -> Color.MAGENTA;
            default -> Color.RED;
        };
    }

}
