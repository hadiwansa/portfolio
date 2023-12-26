package week9.weapons;

import week9.players.Player;

public class PoisonBehaviour implements IWeapon {
    public String describe() {
        return "A bottle of steaming, bubbly poison";
    }

    public void useOn(Player opponent) {
        opponent.reduceHitPoints(25);  // Deduct 25 hit points
    }
}