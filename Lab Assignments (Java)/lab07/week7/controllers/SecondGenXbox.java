package week7.controllers;

/**
 * Class SecondGenXbox
 */
public class SecondGenXbox implements XBoxController {

    private String name = "SecondGen"; //name of controller

    private String state = ""; //state of controller

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
     * press L paddle key
     */
    public void pressLPaddle() {
        state += "L";
    }

    /**
     * press R paddle key
     */
    public void pressRPaddle() {
        state += "R";
    }

    /**
     * reset controller state
     */
    public void reset() {
        state = "";
    }

    /**
     * get controller state
     */
    public String getState() {
        return state;
    }

    /**
     * get controller name
     */
    public String getName() {
        return name;
    }

}
