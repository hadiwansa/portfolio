package week8.trees;

/**
 * Class Beech Tree
 */
public class Beech extends MunicipalTree {

    /**
     * default diameter (in inches) of a beech tree
     */
    private final double DEFAULT_DIAMETER = 7;

    /**
     * Create a Beech Tree with the default diameter.
     * Allometric coefficeints for tree parts can be found here:
     * https://www.researchgate.net/publication/249535320_Canadian_national_tree_aboveground_biomass_equations
     */
    public Beech() {
        this.diameter = DEFAULT_DIAMETER;
        this.setName("Beech");
        this.setBarkParams(new double[]{0.03, 2.10});
        this.setFoliageParams(new double[]{0.03, 1.64});
        this.setWoodParams(new double[]{0.15, 2.29});
    }

    /**
     * Create a Beech Tree with the given diameter.
     *
     * @param diameter	The diameter of this tree
     */
    public Beech(double diameter) {
        this();
        this.diameter = diameter;
    }

}
