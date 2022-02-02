package SpellsAndPotions;

import Entities.Entity;

public class Fire extends Spell {
    @Override
    public void use(Entity entity) {
        if( !entity.fireProtection ) {
            entity.receiveDamage(damage);
        }
    }
    @Override
    public void visit(Entity entity) {
        use(entity);
    }
}
