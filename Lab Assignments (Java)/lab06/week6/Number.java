package week6;

/**
 * Class Number.  A Number is an ASTNode that represents a Number!  It has a value, and that's it.
 */
public class Number extends ASTNode {

    private double value;

    /**
     * Constructor
     *
     * @param v Value of number
     */
    public Number(double v) {
        this.value = v;
    }

    /**
     * Value getter
     *
     * @return  Value of number
     */
    public double getValue() {
        return value;
    }

    /**
     * All ASTNodes must accept visitors, for evaluation
     *
     * @param v Visitor (used to evaluate AST)
     */
    @Override
    void Accept(Visitor v) {
        v.VisitNumber(this);
    }

    /**
     * All ASTNodes may be leaves of an AST tree, or they may be joined to other AST nodes
     * All Numbers are leaves!
     *
     * @return  true if AST node is a leaf
     */
    @Override
    boolean isLeaf() {
        return true;
    }
}
