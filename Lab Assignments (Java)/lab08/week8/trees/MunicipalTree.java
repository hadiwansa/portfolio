package week8.trees;
/**
 * Tree Interface
 */
public abstract class MunicipalTree {

    double [] woodParams;
    double [] barkParams;
    double [] foliageParams;
    double diameter;
    String name;

    /**
     * Get description of tree.
     * @return String description
     */
    public String toString() {
        return "Name: " + this.getName() + "\nWood Parameters: (" +
        this.getWoodParams()[0] + ", " + this.getWoodParams()[1] +  ")\nFoliage Parameters: (" +
        this.getFoliageParams()[0] + ", " + this.getFoliageParams()[1] +  ")\nBark Parameters: (" +
        this.getBarkParams()[0] + ", " + this.getBarkParams()[1] +  ")\nDiameter: " + this.getDiameter(); 
    }

    /**
     * Calculate the carbon content of this tree in kg, based on biomass of wood
     * Carbon content of a White Ash can be calculated using this formula:
     * Carbon = a*B, where B is the tree's Biomass and a = 0.474.  We use
     * a = 0.474 as this was the mean wood carbon fraction for tree species 
     * surveyed by the USDA Forest Service Forest Inventory and Analysis (FIA) 
     * program (https://www.fs.usda.gov/nrs/highlights/2256).
     * 
     * The tree's Biomass can be estimated using this formula:
     * B = p*diameter^q, where diameter is the tree diameter.
     * p and q are wood parameters, by default.
     * 
     * The variables p and q are called allometric coefficeints.
     * Allometric coefficeints for the White Ash and other tree species can be found here:
     * https://www.researchgate.net/publication/249535320_Canadian_national_tree_aboveground_biomass_equations
     *
     * @return double  The kg of carbon sequestered in the wood of the tree
     */
    public double calculateCarbonContent() {
        return 0.47*(this.getWoodParams()[0]*Math.pow(this.getDiameter(), this.getWoodParams()[1]));
    }

    /**
    * Set Bark Parameters of tree.
    */  
    public void setBarkParams(double [] params) {
        this.barkParams = params;
    }

    /**
    * Set Wood Parameters of tree.
    */  
    public void setWoodParams(double [] params) {
        this.woodParams = params;
    }

    /**
    * Set Foliage Parameters of tree.
    */  
    public void setFoliageParams(double [] params) {
        this.foliageParams = params;
    }

    /**
    * Set Diameter of tree.
    */  
    public void setDiameter(double param) {
        this.diameter = param;
    }

    /**
    * Set name of tree.
    */  
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Get Bark Parameters of tree.
    * @return double [] params
    */  
    public double [] getBarkParams() {
        return this.barkParams;
    }

    /**
    * Get Wood Parameters of tree.
    * @return double [] params
    */  
    public double [] getWoodParams() {
        return this.woodParams;
    }

    /**
    * Get Foliage Parameters of tree.
    * @return double [] params
    */  
    public double [] getFoliageParams() {
        return this.foliageParams;
    }

    /**
    * Get Diameter of tree.
    * @return double diameter
    */  
    public double getDiameter() {
        return this.diameter;
    }

    /**
    * Get name of tree.
    * @return String name
    */  
    public String getName() {
        return this.name;
    }
}
