import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import week10.OrderPool;
import week10.threading.Courier;
import week10.threading.Warehouse;

public class OrdersTest {

    @Test
    public void DeliveryTest() {
        OrderPool orders = new OrderPool();
        Courier courier = new Courier(orders);

        Thread CourrierThread = new Thread(courier);;
        Warehouse warehouse = new Warehouse(orders);
        Thread WarehouseThread = new Thread(warehouse);

        WarehouseThread.run();
        CourrierThread.run(); //start the  threads!

        assertEquals(courier.getOrdersDelivered(), 100);

    }


    @Test
    public void WarehouseTest() {
        OrderPool orders = new OrderPool();
        Warehouse warehouse = new Warehouse(orders);
        Thread WarehouseThread = new Thread(warehouse);
        WarehouseThread.run();
        assertEquals(warehouse.getOrdersCompleted(), 100);
    }


}
