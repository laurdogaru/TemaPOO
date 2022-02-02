package Entities;

import SpellsAndPotions.*;

import java.util.*;

public abstract class Entity implements Element {
    public ArrayList<Spell> spells;
    public int currentHealth;
    public int maxHealth;
    public int currentMana;
    public int maxMana;
    public boolean fireProtection;
    public boolean iceProtection;
    public boolean earthProtection;

    public Entity() {
        Random random = new Random();
        spells = new ArrayList<>();
        int nrSpells = 2 + random.nextInt(3);
        for(int i = 0 ; i < nrSpells ; i++) {
            spells.add(spellType(random.nextInt(3)));
        }
    }

    public void regenerateHealth(int health) {
        if( currentHealth + health >= maxHealth ) {
            currentHealth = maxHealth;
            return;
        }
        currentHealth += health;
    }
    public void regenerateMana(int mana) {
        if( currentMana + mana > maxMana) {
            currentMana = maxMana;
            return;
        }
        currentMana += mana;
    }
    public void useSpell(Spell spell, Entity enemy) {
        if( currentMana >= spell.manaCost ) {
            currentMana -= spell.manaCost;
            enemy.accept(spell);
            spells.remove(spell);
        }
    }

    public abstract void receiveDamage(int damage);
    public abstract int getDamage();

    Spell spellType(int seed) {
        if(seed == 0) {
            return new Fire();
        } else if(seed == 1) {
            return new Ice();
        }
        return new Earth();
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public abstract void showParameters();

}
