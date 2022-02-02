package Cells;

import SpellsAndPotions.HealthPotion;
import SpellsAndPotions.ManaPotion;
import SpellsAndPotions.Potion;

import java.util.*;

public class Shop implements CellElement {
    private static Random r = new Random();
    public ArrayList<Potion> potions;

    public Shop() {
        int nrPotions = 2 + r.nextInt(3);
        potions = new ArrayList<>();
        for(int i = 0 ; i < nrPotions ; i++) {
            potions.add(potionType(r.nextBoolean()));
        }
    }
    public Shop(Potion potion1, Potion potion2) {
        potions = new ArrayList<>();
        potions.add(potion1);
        potions.add(potion2);
    }

    private Potion potionType(boolean seed) {
        if(seed) {
            return new HealthPotion();
        }
        return new ManaPotion();
    }
    @Override
    public char toCharacter() {
        return 'S';
    }
    public Potion selectPotion(int index) {
        Potion potion = potions.get(index);
        potions.remove(index);
        return potion;
    }
    @Override
    public String toString() {
        String string = "Shop:\n";
        for(int i = 0; i < potions.size(); i++) {
            string += i;
            string += " ";
            string += potions.get(i).getClass().getSimpleName();
            string += "\n\tPrice: ";
            string += potions.get(i).getPrice();
            string += "\n\tRegeneration Value: ";
            string += potions.get(i).getRegenerationValue();
            string += "\n\tWeight: ";
            string += potions.get(i).getWeight();
            string += "\n";
        }
        return string;
    }
}
