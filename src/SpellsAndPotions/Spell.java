package SpellsAndPotions;

import Entities.Entity;

public abstract class Spell implements Visitor {
    public int damage;
    public int manaCost;

    public Spell() {
        damage = 30;
        manaCost = 1;
    }

    public abstract void use(Entity entity);
}
