package week8.decorator;

import week8.trees.MunicipalTree;

/**
 * Abstract Class TreeDecorator
 */
public abstract class TreeDecorator extends MunicipalTree {

    protected MunicipalTree tree;

    public TreeDecorator(MunicipalTree tree) {
        this.tree = tree;
    }

    @Override
    public double calculateCarbonContent() {
        return tree.calculateCarbonContent();
    }

    @Override
    public String getName() {
        return tree.getName();
    }

}

