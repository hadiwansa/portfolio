package week7;

import org.junit.jupiter.api.Test;

import week7.GTAVBackdoor;
import week7.adapter.XboxPSAdapter;
import week7.controllers.XBoxController;
import week7.controllers.PSController;
import week7.controllers.XBoxControllerFactory;


import static org.junit.jupiter.api.Assertions.*;

public class AdapterTest {

    @Test
    void pressXTest() {
        PSController psController = new PSController();
        XboxPSAdapter xboxpsAdapter = new XboxPSAdapter(psController);
        xboxpsAdapter.pressX();
        assertEquals("X", xboxpsAdapter.getState());
        assertEquals("Square", psController.getState());
    }


    @Test
    void FactoryTest() {
        XBoxController xboxController = XBoxControllerFactory.getController("FirstGen");
        assertEquals("FirstGen", xboxController.getName());
    }

    @Test
    void pressYTest() {
        PSController psController = new PSController();
        XboxPSAdapter xboxpsAdapter = new XboxPSAdapter(psController);
        xboxpsAdapter.pressY();
        assertEquals("Y", xboxpsAdapter.getState());
        assertEquals("Triangle", psController.getState());
    }

    @Test
    void pressATest() {
        PSController psController = new PSController();
        XboxPSAdapter xboxpsAdapter = new XboxPSAdapter(psController);
        xboxpsAdapter.pressA();
        assertEquals("A", xboxpsAdapter.getState());
        assertEquals("X", psController.getState());
    }

    @Test
    void pressBTest() {
        PSController psController = new PSController();
        XboxPSAdapter xboxpsAdapter = new XboxPSAdapter(psController);
        xboxpsAdapter.pressB();
        assertEquals("B", xboxpsAdapter.getState());
        assertEquals("Circle", psController.getState());
    }

}

