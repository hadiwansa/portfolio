package week9.players;

import week9.weapons.IWeapon;

/*
 * Elf class
 */
public class Elf extends Player {

    /*
     * Elf constructor
     *
     * @param name, the name of character
     * @param inputWeapon, the weapon used by the character
     */
    public Elf(String name, IWeapon inputWeapon) {
        super(name, inputWeapon);
        this.hitPoints = 100;
    }

    /*
     * introduce
     *
     * print a string that introduces the character to console
     */
    public void introduce() {
        System.out.println(this.name  + " is an elf from the forest of Elmdale.");
        System.out.println(this.name  + " is pale and yellow.");
        System.out.println(this.name  + " has " + this.hitPoints + " hit points.");
        System.out.println(this.name  + "'s Weapon is: ");
        //this.getWeapon().show(); //you need to create this method, then uncomment this line!
    }


}
