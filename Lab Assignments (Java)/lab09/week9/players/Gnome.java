package week9.players;

import week9.weapons.IWeapon;

/*
 * Gnome class
 */
public class Gnome extends Player {

    /*
     * Gnome constructor
     *
     * @param name, the name of character
     * @param inputWeapon, the weapon used by the character
     */
    public Gnome(String name, IWeapon inputWeapon) {
        super(name, inputWeapon);
        this.hitPoints = 50;
    }

    /*
     * introduce
     *
     * print a string that introduces the character to console
     */
    public void introduce() {
        System.out.println(this.name  + " is a gnome from the shores of the Humber River.");
        System.out.println(this.name  + " is small and green.");
        System.out.println(this.name  + " has " + this.hitPoints + " hit points.");
        System.out.println(this.name  + "'s Weapon is: ");
        //this.getWeapon().show(); //you need to create this method, then uncomment this line!
    }
}