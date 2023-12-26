package week8;

import week8.decorator.FoliageDecorator;
import week8.decorator.BarkDecorator;
import week8.trees.Beech;
import week8.trees.WhiteAsh;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class StudentTests, sanity tests for students
 */
public class StudentTests {

    @Test
    void BarkDecoratorStudentTest() {
        Beech tree = new Beech();
        BarkDecorator d = new BarkDecorator(tree);
        assertEquals(6.1,tree.calculateCarbonContent(),1);
        assertEquals(7.0, d.calculateCarbonContent(),1);
    }

    @Test
    void FoliageDecoratorStudentTest() {
        WhiteAsh tree = new WhiteAsh();
        FoliageDecorator d = new FoliageDecorator(tree);
        assertEquals(13.9,tree.calculateCarbonContent(),1);
        assertEquals(14.1, d.calculateCarbonContent(),1);
    }

}