package week9.players;

import week9.weapons.IWeapon;

/*
 * Player class
 */
public abstract class Player {
    protected IWeapon weapon; //weapon used by player
    protected String name; //name of player
    protected int hitPoints; //strength of player

    /*
     * Player constructor
     *
     * @param name, the name of character
     * @param inputWeapon, the weapon used by the character
     */
    public Player(String name, IWeapon inputWeapon)
    {
        this.weapon = inputWeapon;
        this.name = name;
    }

    /*
     * introduce
     *
     * print a string that introduces the character to console
     */
    public abstract void introduce();

    /*
     * Make this character fight another character
     * @param otherPlayer, the character to fight
     */
    public void fight(Player otherPlayer)
    {
        this.weapon.useOn(otherPlayer);
    }

    /*
     * Damage this Player
     * @param points, the amount of damage to take from player's hit points
     */
    public void damage(int points) {
        if (this.hitPoints >= 0) this.hitPoints -= points;
        if (this.hitPoints <= 0) {
            System.out.println("You have died!");
        }
    }

    /*
     * Getter for hit points
     * @return hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }


    /*
     * Getter for name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /*
     * Getter for weapon
     * @return weapon
     */
    public IWeapon getWeapon() {
        return this.weapon;
    }

    // Method to reduce hit points
    public void reduceHitPoints(int points) {
        this.hitPoints -= points;
    }

}