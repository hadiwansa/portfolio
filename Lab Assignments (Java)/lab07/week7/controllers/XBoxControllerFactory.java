package week7.controllers;

import week7.controllers.XBoxController;
import week7.controllers.FirstGenXbox;
import week7.controllers.SecondGenXbox;

/**
 * XBox Controller Factory
 */
public class XBoxControllerFactory {

    /**
     * Manufacture XBox Controller.
     * If type is "FirstGen", manufacture a FirstGenXbox controller.
     * If type is "SecondGen", manufacture a SecondGenXbox controller.
     * otherwise, return null.
     *
     * @return XBoxController, the manufactured controller
     */
    public static XBoxController getController(String type) {
        switch (type) {
            case "FirstGen":
                return new FirstGenXbox();
            case "SecondGen":
                return new SecondGenXbox();
            default:
                return null;
        }
    }
}
