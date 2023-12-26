package week9;

import week9.players.*;
import week9.weapons.KnifeBehaviour;
import week9.weapons.PoisonBehaviour;

public class Main {

    /* Once you have implemented everyting, this should be the output of running week9.Main.java:

    Bippy is an elf from the forest of Elmdale.
    Bippy is pale and yellow.
    Bippy has 100 hit points.
    Bippy's Weapon is:
    A bottle of steaming, bubbly poison.
    Tarf is a gnome from the shores of the Humber River.
    Tarf is small and green.
    Tarf has 50 hit points.
    Tarf's Weapon is:
    An ornate, bejewelled dagger.
    Bippy is stabbed with a dagger!
    Tarf has been poisoned!
    After the fight, Bippy has 70 hit points.
    After the fight, Tarf has 25 hit points.

    */
    public static void main(String[] args) {
        Player bippy = new Elf("Bippy", new PoisonBehaviour());
        bippy.introduce();

        Player tarf = new Gnome("Tarf", new KnifeBehaviour());
        tarf.introduce();

        tarf.fight(bippy);
        bippy.fight(tarf);

        System.out.println("After the fight, " + bippy.getName() + " has " + bippy.getHitPoints() + " hit points.");
        System.out.println("After the fight, " + tarf.getName() + " has " + tarf.getHitPoints() + " hit points.");

    }

}