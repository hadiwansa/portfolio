package week9.weapons;

import week9.players.Player;

public interface IWeapon {
    String describe();  // Method to describe the weapon
    void useOn(Player opponent);  // Method to use the weapon on an opponent
}