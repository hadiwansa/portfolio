package week7.adapter;

import week7.controllers.XBoxController;
import week7.controllers.PSController;
/**
 * Class XboxPSAdapter
 */
public class XboxPSAdapter implements XBoxController {

    private PSController psController;

    public XboxPSAdapter(PSController psController){
        this.psController = psController;
    }

//    //Add your code below!
//    //Your code should map sequences of XBox commands onto sequences of PS commands.
//
//    //Map an Xbox 'X' onto a PS 'Square'
//    //Map an Xbox 'Y' onto a PS 'Triangle'
//    //Map an Xbox 'A' onto a PS 'X'
//    //Map an Xbox 'B' onto a PS 'Circle'
//    //There is no mapping for XBox paddle keys.
//
//    //When queried for state, make sure to translate the state of your PS controller into a
//    //state that is compatible with an XBox!  The PS state "TriangleCircle"
//    //should therefore become equivalent to the XBox state "YB".

    public void pressX() {
        psController.pressSquare();
    }//press X

    public void pressY() {
        psController.pressTriangle();
    } //write this!

    public void pressA() {
        psController.pressX();
    } //write this!

    public void pressB() {
        psController.pressCircle();
    } //write this!

    public void reset() {
        psController.clear();
    } //write this!

    /**
     * get state of controller
     *
     * @return state
     */
    public String getState() {
        String psState = psController.getState();
        String xboxState = "";

        if (psState.equals("Square")) {
            xboxState = "X";
        } else if (psState.equals("Triangle")) {
            xboxState = "Y";
        } else if (psState.equals("X")) {
            xboxState = "A";
        } else if (psState.equals("Circle")) {
            xboxState = "B";
        }

        return xboxState;
    }

    /**
     * get name of controller. You can name your
     * adapted controller anything you want.
     *
     * @return name
     */
    public String getName() {
        return "XBoxPlayStationAdapter";
    }

}
