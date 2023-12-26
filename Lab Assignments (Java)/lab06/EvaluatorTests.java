import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import week6.Evaluator;
import week6.ValueError;
import week6.BinOp;
import week6.Number;

public class EvaluatorTests {
    @Test
    void VisitNumber() {
        Evaluator evaluator = new Evaluator();
        try {
            assertEquals(10, evaluator.evaluate(new Number(10)), "VisitNumber doesn't evaluate number correctly");
        } catch (ValueError e) {
            fail("VisitNumber shouldn't raise Exception");
        }
    }


    @Test
    void VisitBinOp() {
        Evaluator evaluator = new Evaluator();

        BinOp bo = new BinOp(new Number(10), "+", new Number(11));

        try {
            assertEquals(21, evaluator.evaluate(bo), 0.1,
                    "Evaluator evaluated to a wrong answer.");
        } catch (ValueError e) {
            fail("Evaluator.evaluate() shouldn't raise Exception.");
        }

        bo = new BinOp(new Number(10), "/", new Number(0));

        try {
            evaluator.evaluate(bo);
        } catch (ValueError e) {
            assert (e instanceof ValueError);
        }

    }

}
