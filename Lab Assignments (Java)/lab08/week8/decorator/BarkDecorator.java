package week8.decorator;

import week8.trees.MunicipalTree;

/**
 * Class BarkDecorator, incorporates bark in calculation of a tree's carbon content
 */
public class BarkDecorator extends TreeDecorator {


    public BarkDecorator(MunicipalTree tree) {
        super(tree);
    }

    @Override
    public double calculateCarbonContent() {
        double baseCarbon = tree.calculateCarbonContent();
        double barkCarbon = 0.47 * (tree.getBarkParams()[0] * Math.pow(tree.getDiameter(), tree.getBarkParams()[1]));
        return baseCarbon + barkCarbon;
    }

}
