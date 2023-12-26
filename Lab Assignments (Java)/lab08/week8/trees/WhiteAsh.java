package week8.trees;
/**
 * Class WhiteAsh
 */
public class WhiteAsh extends MunicipalTree {

    /**
     * Get description of tree.
     */
    /**
     * default diameter (in inches) of a beech tree
     */
    private final double DEFAULT_DIAMETER = 10;

    /**
     * Create a White Ash Tree with the default diameter.
     * Allometric coefficeints for tree parts can be found here:
     * https://www.researchgate.net/publication/249535320_Canadian_national_tree_aboveground_biomass_equations
     */
    public WhiteAsh() {
        this.diameter = DEFAULT_DIAMETER;
        this.setName("White Ash");
        this.setBarkParams(new double[]{0.04, 1.99});
        this.setFoliageParams(new double[]{0.11, 1.23});
        this.setWoodParams(new double[]{0.19, 2.17});
    }

    /**
     * Create a White Ash Tree with the given diameter.
     *
     * @param diameter  The diameter of this tree
     */
    public WhiteAsh(double diameter) {
        this();
        this.diameter = diameter;
    }

}
