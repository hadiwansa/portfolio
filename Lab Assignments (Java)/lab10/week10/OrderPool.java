package week10;

import java.util.Stack;

/**
 * OrderPool Class.  OrderPool stores orders that need delivery.
 */
public class OrderPool extends Stack<Order> {

    /*
     * Use this method to pull an Order from the OrderPool.
     */
    public Order pull() {
        if (!this.empty()) {
            this.lastElement().print();
            return this.pop();
        } else {
            System.out.println("Empty OrderPool! And so, no orders to be delivered.");
        }
        return null;
    }

}
