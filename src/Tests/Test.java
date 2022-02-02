package Tests;

import Cells.*;
import Entities.*;
import Exceptions.*;
import Initial.*;
import SpellsAndPotions.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws FontFormatException, IOException {
        Scanner scanner = new Scanner(System.in);
        Game game = Game.getInstance();
        Grid grid = Grid.generatePredefinedMap();
        game.run(grid);

        int i = 0;
        while(Game.startMode) {
            System.out.println();
            grid.showGrid();
            game.showStory(grid.currentCell);
            game.showOptions(grid.currentCell);
            scanner.nextLine();
            //mutarea cand personajul trebuie sa se lupte cu un inamic
            if(i == 9) {
                Enemy enemy = (Enemy) grid.currentCell.element;
                int j = 0;
                while(enemy.currentHealth > 0) {
                    grid.currentCharacter.showParameters();
                    enemy.showParameters();
                    //folosirea potiunii de viata
                    if( grid.currentCharacter.inventory.potions.size() > 0
                            && grid.currentCharacter.inventory.potions.get(0) instanceof HealthPotion
                            && grid.currentCharacter.currentHealth <= 70) {
                        grid.currentCharacter.inventory.potions.get(0).usePotion(grid.currentCharacter);
                    }
                    //folosirea potiunii de mana
                    if( grid.currentCharacter.currentMana <= 8 ) {
                        if(grid.currentCharacter.inventory.potions.size() == 2) {
                            grid.currentCharacter.inventory.potions.get(1).usePotion(grid.currentCharacter);
                        }
                        if( grid.currentCharacter.inventory.potions.size() == 1
                            && grid.currentCharacter.inventory.potions.get(0) instanceof ManaPotion ) {
                            grid.currentCharacter.inventory.potions.get(1).usePotion(grid.currentCharacter);
                        }
                    }
                    //tura personajului de a ataca
                    if(j % 2 == 0) {
                        System.out.print("Next command:");
                        scanner.nextLine();
                        if(grid.currentCharacter.spells.size() > 0) {
                            String spellName = grid.currentCharacter.spells.get(0).getClass().
                                    getSimpleName();
                            grid.currentCharacter.useSpell(grid.currentCharacter.spells.
                                    get(0), enemy);
                            System.out.println("You've used " + spellName + ".");
                        }
                        else {
                            enemy.receiveDamage(grid.currentCharacter.getDamage());
                            System.out.println("You've used a basic attack");
                        }
                    }
                    //tura inamicului de a ataca
                    else {
                        enemy.attack(grid.currentCharacter);
                    }
                    j++;
                }
                //colectarea de xp si coins
                if( enemy.currentHealth <= 0) {
                    grid.currentCharacter.xp += 50;
                    int chance = new Random().nextInt(5);
                    if(chance != 0) {
                        grid.currentCharacter.inventory.nrCoins += 2;
                    }
                    if(grid.currentCharacter.xp >= 100) {
                        grid.currentCharacter.levelUp();
                    }
                }
                //folosirea potiunii ramase, daca este cazul
                if( grid.currentCharacter.inventory.potions.size() > 0 ) {
                    grid.currentCharacter.inventory.potions.get(0).usePotion(grid.currentCharacter);;
                }
                grid.currentCharacter.showParameters();
                i++;
                continue;
            }
            switch(i) {
                case 0, 1, 2 : grid.goEast(); break;
                //mutarea cand este pe celula cu shop
                case 3:
                    Shop shop = (Shop) grid.currentCell.element;
                    System.out.println(shop);
                    try {
                        grid.currentCharacter.buyPotion(shop.selectPotion(1));
                        grid.currentCharacter.buyPotion(shop.selectPotion(0));
                    }
                    catch(NoMoneyException | FullInventoryException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                //afisarea inventarului
                case 4: System.out.print(grid.currentCharacter.inventory); break;
                case 5: grid.goEast(); break;
                case 6, 7, 8: grid.goSouth(); break;
                case 10: grid.goSouth();
            }
            i++;
        }
    }
}
