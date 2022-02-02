package Entities;

import java.util.*;
import SpellsAndPotions.*;

public class Inventory {
    public ArrayList<Potion> potions;
    int maxWeight;
    public int nrCoins;

    public Inventory(int maxWeight) {
        potions = new ArrayList<>();
        this.maxWeight = maxWeight;
        nrCoins = 3;
    }

    void addPotion(Potion potion) {
        potions.add(potion);
    }
    public void removePotion(Potion potion) {
        potions.remove(potion);
    }
    public int remainingWeight() {
        int currentWeight = 0;
        for(Potion potion : potions) {
            currentWeight += potion.getWeight();
        }
        return maxWeight - currentWeight;
    }
    public String toString() {
        String string = "\nInventory: \n";
        string += "Remaining weight: ";
        string += remainingWeight();
        string += "\nCoins: ";
        string += nrCoins;
        string += "\nPotions: \n";
        for(int i = 0 ; i < potions.size() ; i++) {
            string += i;
            string += " ";
            string += potions.get(i).getClass().getSimpleName();
            string += "\n\tRegeneration Value: ";
            string += potions.get(i).getRegenerationValue();
            string += "\n\tWeight: ";
            string += potions.get(i).getWeight();
            string += "\n";
        }
        return string;
    }
}
