import week7.controllers.PSController;
import week7.adapter.XboxPSAdapter;
import week7.GTAVBackdoor;
/**
 * Main class, for demo purposes
 */
public class Main {
    public static void main(String[] args){
        PSController psController1 = new PSController();
        PSController psController2 = new PSController();
        psController2.pressSquare();

        XboxPSAdapter xboxpsAdapter1 = new XboxPSAdapter(psController1);
        XboxPSAdapter xboxpsAdapter2 = new XboxPSAdapter(psController2);

        GTAVBackdoor gtavBackdoor = new GTAVBackdoor();
        System.out.println("The xboxpsAdapter1's compatibility: " + gtavBackdoor.checkControllerCompatibility(xboxpsAdapter1));
        System.out.println("The xboxpsAdapter2's compatibility: " + gtavBackdoor.checkControllerCompatibility(xboxpsAdapter2));
    }
}
