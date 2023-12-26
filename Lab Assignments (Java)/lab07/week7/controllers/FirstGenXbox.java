package week7.controllers;

/**
 * Class FirstGenXbox
 */
public class FirstGenXbox implements XBoxController {

    private String name = "FirstGen"; //name of controller

    private String state = ""; //state of controller

    /**
     * press X key
     */
    public void pressX() {
        state += "X";
    }

    /**
     * press Y key
     */
    public void pressY() {
        state += "Y";
    }

    /**
     * press A key
     */
    public void pressA() {
        state += "A";
    }

    /**
     * press B key
     */
    public void pressB() {
        state += "B";
    }

    /**
     * reset controller state
     */
    public void reset() {
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

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

}
