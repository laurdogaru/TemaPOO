package Entities;

import Cells.CellElement;

import java.util.*;
public class Enemy extends Entity implements CellElement {
    private static final Random random = new Random();

    public Enemy() {
        maxHealth = 1 + random.nextInt(100);
        currentHealth = maxHealth;
        maxMana = 1 + random.nextInt(10);
        currentMana = maxMana;
        fireProtection = random.nextBoolean();
        iceProtection = random.nextBoolean();
        earthProtection = random.nextBoolean();
    }

    @Override
    public void receiveDamage(int damage) {
        boolean aux = random.nextBoolean();
        if(aux) {
            currentHealth -= damage;
        }
    }
    public int getDamage() {
        boolean aux = random.nextBoolean();
        int damage = 1 + random.nextInt(10);
        if (aux) {
            return damage;
        }
        return damage * 2;
    }
    public void attack(Character character) {
        int decideSpell = random.nextInt(4);
        if( currentMana < 1 || spells.size() == 0 || decideSpell != 0) {
            character.receiveDamage(getDamage());
            System.out.println("The enemy used a basic attack.");
        }
        else {
            int index = random.nextInt(spells.size());
            String spellName = spells.get(index).getClass().getSimpleName();
            useSpell(spells.get(index), character);
            System.out.println("The enemy used " + spellName + "." );
        }
    }
    public char toCharacter() {
        return 'E';
    }
    public void showParameters() {
        System.out.println("Enemy's Health: " + currentHealth);
        System.out.println("Enemy's Mana: " + currentMana+ "\n");
    }
}
