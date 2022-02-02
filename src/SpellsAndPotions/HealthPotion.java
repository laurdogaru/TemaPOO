package SpellsAndPotions;

import Entities.Character;

public class HealthPotion implements Potion {
    private int price;
    private int regenerationValue;
    private int weight;

    public HealthPotion() {
        price = 1;
        regenerationValue = 20;
        weight = 1;
    }

    @Override
    public void usePotion(Character character) {
        character.regenerateHealth(getRegenerationValue());
        character.inventory.removePotion(this);
    }
    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public int getRegenerationValue() {
        return regenerationValue;
    }
    @Override
    public int getWeight() {
        return weight;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
