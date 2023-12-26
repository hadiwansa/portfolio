package week9;

import week9.players.*;
import org.junit.jupiter.api.Test;
import week9.weapons.KnifeBehaviour;
import week9.weapons.PoisonBehaviour;

public class StrategyTest {

    @Test
    public void WeaponTest() {
        Player bippy = new Elf("Bippy", new PoisonBehaviour());
        Player tarf = new Gnome("Tarf", new KnifeBehaviour());
        tarf.fight(bippy);
        assert (bippy.getHitPoints() == 70);
        bippy.fight(tarf);
        assert (tarf.getHitPoints() == 25);
    }


}
