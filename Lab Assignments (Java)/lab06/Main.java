import week6.*;
import week6.Number;

import static org.junit.jupiter.api.Assertions.fail;

/*
 * Class Main.  Some code to demonstrate evaluation of an AST
 */
public class Main {

    public static void main(String[] args) {

        Evaluator total = new Evaluator();

        Number n1 = new Number(7);
        Number n2 = new Number(9);
        Number n3 = new Number(1);
        Number n4 = new Number(0.5);

        BinOp a1 = new BinOp(n2, "+", n3);  //9 + 1
        BinOp a2 = new BinOp(n1, "*", a1); //7 * (9 + 1)
        BinOp a3 = new BinOp(a2, "/", n4); //(7 * (9 + 1)) / 0.5
        BinOp a4 = new BinOp(a3, "-", n1); //((7 * (9 + 1)) / 0.5) - 7

        double val = 0;
        try {
            val = total.evaluate(a4);
        } catch (ValueError e) {
            System.out.println("Evaluator.evaluate() threw a ValueError.");
        }

        System.out.println("((7 * (9 + 1)) / 0.5) - 7: " + val);

        BinOp o1 = new BinOp(n4, "*", n2);//0.5 * 9
        BinOp o2 = new BinOp(n3, "+", o1);//1 + (0.5 * 9)
        BinOp o3 = new BinOp(o2, "-", n1);//1 + (0.5 * 9) - 7
        BinOp o4 = new BinOp(n2, "/", o3);//9 / (1 + (0.5 * 9) - 7)

        try {
            val = total.evaluate(o4);
        } catch (ValueError e) {
            System.out.println("Evaluator.evaluate() threw a ValueError.");
        }
        System.out.println("9 / (1 + (0.5 * 9) - 7): " + val);

    }

}
