package week10.threading;

import week10.Order;
import week10.OrderPool;

/**
 * Courier Class.  Couriers deliver orders.
 */
public class Courier implements Runnable {
    private OrderPool orders;
    private int ordersCompleted;

    /**
     * Courier constructor
     * @param orders
     */
    public Courier(OrderPool orders) {
        this.orders = orders;
        this.ordersCompleted = 0;
    }

    /*
     * The run method for a Courier.
     * This method should:
     * 1. Check to see if the Courier's already delivered 100 orders.
     * 2. If yes, terminate the thread!
     * 3. If no, and if the Warehouse has orders that need to be fulfilled, the Courier should 'consume' an Order from the Order list.
     * 4. After each iteration of this thread, put it to sleep for 10 milliseconds!  You can do this using the command:
     *    Thread.sleep(10);
     */
    @Override
    public void run() {
        while (ordersCompleted < 100) {
            Order orderToDeliver = orders.pull();
            if (orderToDeliver != null) {
                // Deliver the order
                ordersCompleted++;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Getter for getOrdersDelivered
     * @return Orders Delivered
     */
    public int getOrdersDelivered () {
        return this.ordersCompleted;
    }
}