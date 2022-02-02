package Entities;

public final class CharacterFactory {
    public enum CharacterType {
        WARRIOR, ROGUE, MAGE
    }

    public static Character createCharacter(CharacterType characterType, String name, int level,
                                            int xp) {
        switch(characterType) {
            case WARRIOR:
                return new Warrior(name, level, xp);
            case ROGUE:
                return new Rogue(name, level, xp);
            case MAGE:
                return new Mage(name, level, xp);
            default:
                throw new IllegalArgumentException("The character type " +
                        characterType + " is not recognized.");
        }
    }
}
