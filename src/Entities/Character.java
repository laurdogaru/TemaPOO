package Entities;

import SpellsAndPotions.*;
import Exceptions.*;

public abstract class Character extends Entity {
    public String name;
    int x;
    int y;
    public Inventory inventory;
    public int xp;
    int level;
    int strength;
    int charisma;
    int dexterity;

    public Character(String name, int level, int xp) {
        super();
        x = 0;
        y = 0;
        this.name = name;
        this.level = level;
        this.xp = 0;
        currentHealth = maxHealth = 100;
        currentMana = maxMana = 10;
    }


    public abstract void levelUp();
    public void buyPotion(Potion potion) throws NoMoneyException, FullInventoryException{
        if(inventory.nrCoins < potion.getPrice()) {
            throw new NoMoneyException();
        }
        if(inventory.remainingWeight() < potion.getWeight()) {
            throw new FullInventoryException();
        }
        inventory.nrCoins -= potion.getPrice();
        inventory.addPotion(potion);
    }
    public void showParameters() {
        System.out.println("\n" + name + "'s Health: " + currentHealth);
        System.out.println(name +"'s Mana: " + currentMana);
    }
    public void showStats() {
        System.out.println("Class: " + getClass().getSimpleName());
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("XP: " + xp);
        System.out.println("Strength: " + strength);
        System.out.println("Charisma: " + charisma);
        System.out.println("Dexterity: " + dexterity);
    }
    public String stats() {
        String s = "";
        s += "Class: " + getClass().getSimpleName();
        s += "\nLevel: " + level;
        s += "\nXP: " + xp;
        s += "\nStrenth: " + strength;
        s += "\nCharisma: " + charisma;
        s += "\nDexterity: " + dexterity;
        return s;
    }
    @Override
    public String toString() {
        return name;
    }
}
