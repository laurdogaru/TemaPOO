package Entities;

import java.util.*;

public class Warrior extends Character {
    static Random random = new Random();
    public Warrior(String name, int level, int xp) {
        super(name, level, xp);
        strength = 20 * level;
        charisma = 10 * level;
        dexterity = 10 * level;
        fireProtection = true;
        iceProtection = false;
        earthProtection = false;
        inventory = new Inventory(100);
    }


    @Override
    public void receiveDamage(int damage) {
        int chance = (dexterity + charisma) / 4;
        int randomNumber = 1 + random.nextInt(100);
        if( randomNumber <= chance ) {
            currentHealth -= damage / 2;
        }
        else {
            currentHealth -= damage;
        }
    }
    @Override
    public int getDamage() {
        int damage;
        int chance = strength / 4;
        int randomNumber = 1 + random.nextInt(100);
        if(randomNumber <= chance) {
            return strength;
        }
        else {
            return strength / 2;
        }
    }
    @Override
    public void levelUp() {
        level++;
        xp = xp % 100;
        strength += 20;
        dexterity += 10;
        charisma += 10;
    }
}
