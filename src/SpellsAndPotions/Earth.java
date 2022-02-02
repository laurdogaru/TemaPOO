package SpellsAndPotions;

import Entities.*;

public class Earth extends Spell {
    @Override
    public void use(Entity entity) {
        if( !entity.earthProtection ) {
            entity.receiveDamage(damage);
        }
    }
    @Override
    public void visit(Entity entity) {
        use(entity);
    }
}
