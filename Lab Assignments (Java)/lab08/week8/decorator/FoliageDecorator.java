package week8.decorator;

import week8.trees.MunicipalTree;

/**
 * Class FoliageDecorator, incorporates foliage in calculation of a tree's carbon content
 */
public class FoliageDecorator extends TreeDecorator {


    public FoliageDecorator(MunicipalTree tree) {
        super(tree);
    }

    @Override
    public double calculateCarbonContent() {
        double baseCarbon = tree.calculateCarbonContent();
        double foliageCarbon = 0.47 * (tree.getFoliageParams()[0] * Math.pow(tree.getDiameter(), tree.getFoliageParams()[1]));
        return baseCarbon + foliageCarbon;
    }

}

