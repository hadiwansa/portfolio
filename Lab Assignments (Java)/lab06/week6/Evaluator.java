package week6;

/*
 * Class Evaluator.  An Evaluator will be used to evaluate an AST.
 * It implements the Visitor interface
 */
public class Evaluator implements Visitor {

    private double value;

    /*
     * Evaluation of an AST.  Note that each node in the tree will
     * accept a visitor.  This will impact the resulting value
     * of the Evaluator! Accept will call either VisitNumber or
     * VisitBinOp depending on the type of node encountered.
     *
     * @param root a reference to the AST to be evaluated
     */
    public double evaluate(ASTNode root) throws ValueError {
        root.Accept(this);
        return this.value;
    }

    /*
     * Visit an expression that is a NUMBER.
     * Accumulate the value of the number in this.value.
     *
     * @param v Evaluate the value of a number (results to be stored in value attribute)
     */
    @Override
    public void VisitNumber(Number n) {
        this.value = n.getValue();
    }

    /**
     * Visit an expression that is a BinOp.
     * This will ONLY be used to evaluate BinOps that represent additions or multiplications.
     * The only expected operation symbols will be "*" or "+".
     * Note that this will be a recursive function!
     * If both the right and left-hand sides of the BinOp are leaves,
     * you can evaluate the BinOp and accumulate the value in this.value.
     * If any side is NOT a leaf, you will have to ask the ASTNode
     * to recursively "Accept" a visitor until a leaf is encountered.
     * If you encounter any invalid operations (i.e. those other than + or *), throw a ValueError.
     * If you attempt to divide by zero, throw a ValueError.
     *
     * @param a Binary Operation to be evaluated (results to be stored in value attribute)
     */
    @Override
    public void VisitBinOp(BinOp a) throws ValueError {
        Evaluator leftEvaluator = new Evaluator();
        Evaluator rightEvaluator = new Evaluator();

        // Visit the left-hand side of the binary operation
        a.getLeft().Accept(leftEvaluator);

        // Visit the right-hand side of the binary operation
        a.getRight().Accept(rightEvaluator);

        double leftValue = leftEvaluator.value;
        double rightValue = rightEvaluator.value;

        // Perform the binary operation and accumulate the result
        switch (a.getOp()) {
            case "+" -> this.value = leftValue + rightValue;
            case "*" -> this.value = leftValue * rightValue;
            default -> throw new ValueError("Invalid operator");
        }
    }
}



