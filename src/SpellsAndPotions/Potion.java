package SpellsAndPotions;

import Entities.Character;

public interface Potion {
    public void usePotion(Character character);
    public int getPrice();
    public int getRegenerationValue();
    public int getWeight();
}
