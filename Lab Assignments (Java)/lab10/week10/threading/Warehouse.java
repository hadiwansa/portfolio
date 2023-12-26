package week10.threading;

import week10.Order;
import week10.OrderPool;

/**
 * Warehouse Class.  Warehouses produce orders
 */
public class Warehouse implements Runnable {
    private OrderPool orderList;
    private int ordersCompleted;

    /**
     * Warehouse constructor
     * @param order
     */
    public Warehouse(OrderPool order) {
        this.orderList = order;
        this.ordersCompleted = 0;
    }

    /*
     * The run method for a Warehouse.
     * This method should:
     * 1. Check to see if <= 100 orders have been created.
     * 2. If yes, create another order! There are 4 types of orders:
     *    "Oranges", "Pineapples", "Strawberries", and "Cherries"
     * Every fourth order should have "Cherries" as a description.
     * Every third order should be described as "Strawberries".
     * Every second order should be "Pineapples", and the rest should be "Oranges".
     * Each time you create an order, increment the ordersFulfilled variable.
     * 3. Once 100 orders have been created, terminate the Thread
     * 4. After each iteration of the thread, put it to sleep for 10 milliseconds!  You can do this using the command:
     *    Thread.sleep(10);
     */
    @Override
    public void run() {
        while (ordersCompleted < 100) {
            String itemType;
            switch (ordersCompleted % 4) {
                case 0: itemType = "Oranges"; break;
                case 1: itemType = "Pineapples"; break;
                case 2: itemType = "Strawberries"; break;
                default: itemType = "Cherries";
            }

            // Create a new order with an order number and the specified item type
            Order newOrder = new Order(ordersCompleted, itemType);
            orderList.add(newOrder); // Assuming add is a method to add an order to the OrderPool
            ordersCompleted++;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Getter for order count
     * @return number of orders completed
     */
    public int getOrdersCompleted() {
        return this.ordersCompleted;
    }
}
