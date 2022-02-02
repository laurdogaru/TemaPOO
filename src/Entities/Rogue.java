package Entities;

import java.util.Random;

public class Rogue extends Character {
    private static Random random = new Random();

    public Rogue(String name, int level, int xp) {
        super(name, level, xp);
        strength = 10 * level;
        dexterity = 20 * level;
        charisma = 10 * level;
        fireProtection = false;
        iceProtection = false;
        earthProtection = true;
        inventory = new Inventory(50);
    }

    @Override
    public void receiveDamage(int damage) {
        int chance = (strength + charisma) / 4;
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
        int chance = dexterity / 4;
        int randomNumber = 1 + random.nextInt(100);
        if(randomNumber <= chance) {
            return dexterity * 2;
        }
        else {
            return dexterity;
        }
    }
    @Override
    public void levelUp() {
        level++;
        xp = xp % 100;
        strength += 10;
        dexterity += 20;
        charisma += 10;
    }
}
