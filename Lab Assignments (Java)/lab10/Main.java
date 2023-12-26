import week10.OrderPool;
import week10.threading.Courier;
import week10.threading.Warehouse;

/**
 * Main class.
 */
public class Main {

    static OrderPool orders;
    static Courier courrier;
    static Warehouse factory;

    /**
     * Driver to run the fulfilment of orders
     * @param args
     */
    public static void main(String[] args) {
        runWarehouse(); //run interacting threads to simulate a fulfilment centre!
    }

    /**
     * Run the fulfilment of orders!
     */
    public static void runWarehouse() {

        orders = new OrderPool();
        courrier = new Courier(orders);
        factory = new Warehouse(orders);

        Thread DeliveryThread = new Thread(courrier);
        Thread FactoryThread = new Thread(factory);

        DeliveryThread.start(); //start the concurrent threads!
        FactoryThread.start();
    }
}