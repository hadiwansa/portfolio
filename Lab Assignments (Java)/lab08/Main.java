import week8.decorator.FoliageDecorator;
import week8.decorator.BarkDecorator;
import week8.decorator.TreeDecorator;
import week8.trees.WhiteAsh;

/**
 * Main Class. Create some trees and print them out.  Decorate some of the string descriptions.
 */
public class Main {

    /**
     * Main Method
     *
     * @param args arguments, if any
     */
    public static void main(String[] args) {

        WhiteAsh m = new WhiteAsh();
        System.out.println(m);
        System.out.println("Carbon in White Ash Wood (in kg):" + m.calculateCarbonContent());

        TreeDecorator b = new BarkDecorator(m);
        System.out.println("Carbon in White Ash Wood + Bark (in kg):" + b.calculateCarbonContent());

        TreeDecorator c = new FoliageDecorator(m);
        System.out.println("Carbon in White Ash Wood + Leaves (in kg):" + c.calculateCarbonContent());
    }
}