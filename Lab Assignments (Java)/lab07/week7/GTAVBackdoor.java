package week7;

import week7.adapter.XboxPSAdapter;

import java.util.*;

import static java.util.Map.entry;

/**
 * Class GTAVBackdoor, class to manage Cheat Codes for the XBox GTA
 */
public class GTAVBackdoor {

    Map<String, String> cheatCodes = Map.ofEntries(
            entry("XXBYYXA", "Wanted Level Up"),
            entry("YYBB", "Wanted Level Down"),
            entry("BXAAXA", "Fast Run"),
            entry("BBAXXA", "Fast Swim"),
            entry("YYYBBB", "Change Weather"),
            entry("YAYA", "Flaming Bullets"),
            entry("XBXB", "Give Parachute")
    );

    /**
     * checkControllerCompatibility
     * Checks if a given controller is compatible with the game
     *
     * @param xboxController the XBox controller to check
     * @return true if the controller is compatible, i.e  it can enter all the cheat codes
     */
    public boolean checkControllerCompatibility(XboxPSAdapter xboxController){
        boolean compatible = true;

        for(String cheatCode : cheatCodes.keySet()){ //for each cheat code
            xboxController.reset();
            for(char button : cheatCode.toCharArray()){ //enter the code into the controller
                switch(button){
                    case 'X':
                        xboxController.pressX();
                        break;
                    case 'Y':
                        xboxController.pressY();
                        break;
                    case 'A':
                        xboxController.pressA();
                        break;
                    case 'B':
                        xboxController.pressB();
                        break;
                }
            }

            if(!xboxController.getState().equals(cheatCode)){ //now query the state!
                compatible = false;
            }
        }

        return compatible; //did we correctly represent the cheat codes?
    }
}
