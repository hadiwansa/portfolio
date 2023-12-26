package week9.weapons;

import week9.players.Player;

public class KnifeBehaviour implements IWeapon {
    public String describe() {
        return "An ornate, bejewelled dagger";
    }

    public void useOn(Player opponent) {
        opponent.reduceHitPoints(30);  // Deduct 30 hit points
    }
}