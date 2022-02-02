package Entities;

import java.util.Random;

public class Mage extends Character {
    private static Random random = new Random();

    public Mage(String name, int level, int xp) {
        super(name, level, xp);
        strength = 10 * level;
        dexterity = 10 * level;
        charisma = 20 * level;
        fireProtection = false;
        iceProtection = true;
        earthProtection = false;
        inventory = new Inventory(20);
    }

    @Override
    public void receiveDamage(int damage) {
        int chance = (strength + dexterity) / 4;
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
        int chance = charisma / 4;
        int randomNumber = 1 + random.nextInt(100);
        if(randomNumber <= chance) {
            return charisma * 2;
        }
        else {
            return charisma;
        }
    }
    @Override
    public void levelUp() {
        level++;
        xp = xp % 100;
        strength += 10;
        dexterity += 10;
        charisma += 20;
    }
}
