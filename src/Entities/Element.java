package Entities;

import SpellsAndPotions.*;

public interface Element <T extends Entity>{
    public void accept(Visitor<T> visitor);
}
