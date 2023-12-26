package week7.controllers;

/**
 * Class PSController
 */
public class PSController {

    private String state = ""; //state of controller

    /**
     * press Square key
     */
    public void pressSquare() {
        state += "Square";
    }

    /**
     * press Triangle key
     */
    public void pressTriangle() {
        state += "Triangle";
    }

    /**
     * press Circle key
     */
    public void pressCircle() {
        state += "Circle";
    }

    /**
     * press X key
     */
    public void pressX() {
        state += "X";
    }

    /**
     * reset state
     */
    public void clear() {
        state = "";
    }

    /**
     * get state
     *
     * @return state
     */
    public String getState() {
        return state;
    }

}
