package week7.controllers;

/**
 * XBox Controller interface
 */
public interface XBoxController {

    /**
     * press X key
     */
    public void pressX(); //press X

    /**
     * press Y key
     */
    public void pressY(); //press Y

    /**
     * press A key
     */
    public void pressA(); //press A

    /**
     * press B key
     */
    public void pressB(); //press B

    /**
     * reset state
     */
    public void reset(); //reset

    /**
     * get state
     *
     * @return state
     */
    public String getState();

    /**
     * get name
     *
     * @return state
     */
    public String getName();
}
