package SpellsAndPotions;

import Entities.Entity;

public class Ice extends Spell {
    @Override
    public void use(Entity entity) {
        if( !entity.iceProtection ) {
            entity.receiveDamage(damage);
        }
    }
    @Override
    public void visit(Entity entity) {
        use(entity);
    }

}
