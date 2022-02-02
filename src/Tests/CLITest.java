package Tests;

import Cells.*;
import Entities.*;
import Exceptions.*;
import java.util.*;
import Initial.*;

public class CLITest {
    public static void cliTest(Entities.Character currentCharacter){
        Scanner scanner = new Scanner(System.in);

        Game game = Game.getInstance();
        Grid grid = Grid.generateRandomMap();
        grid.currentCharacter = currentCharacter;

        String command;
        ArrayList<String> validCommands = new ArrayList<>();
        validCommands.add("w");
        validCommands.add("s");
        validCommands.add("e");
        validCommands.add("n");
        validCommands.add("quit");
        validCommands.add("enter");
        validCommands.add("inventory");
        validCommands.add("parameters");
        validCommands.add("stats");

        while(true) {
            System.out.println();
            grid.showGrid();
            game.showStory(grid.currentCell);
            if(grid.currentCell.type == CellEnum.FINISH) {
                System.out.println("Congratulations! You won!");
                System.exit(0);
            }
            if(grid.currentCell.type == CellEnum.ENEMY) {
                Enemy enemy = (Enemy) grid.currentCell.element;
                int i = 0;
                while(grid.currentCharacter.currentHealth > 0 && enemy.currentHealth > 0) {
                     if(i == 0) {
                         grid.currentCharacter.showParameters();
                         enemy.showParameters();
                     }
                     if( i % 2 == 0 ) {
                         if(grid.currentCharacter.spells.size() > 0) {
                             while(true) {
                                 System.out.println("0 Attack");
                                 System.out.println("1 Inventory");
                                 System.out.print("Choose action: ");
                                 int actionType = scanner.nextInt();
                                 if( actionType == 0 ) {
                                     break;
                                 }
                                 else if( actionType == 1 ) {
                                     checkInventory(grid);
                                 }
                             }
                             System.out.println("0 Normal");
                             System.out.println("1 Spell");
                             System.out.print("Choose attack type:");
                             int attackType;
                             try {
                                 attackType = scanner.nextInt();
                                 if( attackType != 0 && attackType != 1 ) {
                                     throw new InvalidCommandException();
                                 }
                             }
                             catch (Exception e) {
                                 System.out.println(new InvalidCommandException().getMessage());
                                 scanner.nextLine();
                                 continue;
                             }
                             if( attackType == 1 ) {
                                 for(int j = 0 ; j < grid.currentCharacter.spells.size(); j++) {
                                     System.out.println(j + " " + grid.currentCharacter.spells.get(j).getClass().getSimpleName());
                                 }
                                 System.out.print("Choose spell: ");
                                 int spellIndex = scanner.nextInt();
                                 String spellName = grid.currentCharacter.spells.get(spellIndex).getClass().getSimpleName();
                                 grid.currentCharacter.useSpell(grid.currentCharacter.spells.get(spellIndex), enemy);
                                 System.out.println("You've used " + spellName + ".");
                             } else {
                                 enemy.receiveDamage(grid.currentCharacter.getDamage());
                                 System.out.println("You've used a basic attack");
                             }
                         }
                         else {
                             while(true) {
                                 System.out.println("0 Attack");
                                 System.out.println("1 Inventory");
                                 System.out.print("Choose action: ");
                                 int actionType = scanner.nextInt();
                                 if( actionType == 0 ) {
                                     break;
                                 }
                                 else if( actionType == 1 ) {
                                     checkInventory(grid);
                                 }
                             }
                             enemy.receiveDamage(grid.currentCharacter.getDamage());
                             System.out.println("You've used a basic attack");
                         }
                         grid.currentCharacter.showParameters();
                         if(enemy.currentHealth > 0) {
                             enemy.showParameters();
                         }
                         try {
                             if( enemy.currentHealth > 0 ) {
                                 Thread.sleep(3000);
                             }
                         }
                         catch (Exception e) {
                             System.out.println(e.getMessage());
                         }
                     }
                     else {
                         enemy.attack(grid.currentCharacter);
                         grid.currentCharacter.showParameters();
                         if(enemy.currentHealth > 0) {
                             enemy.showParameters();
                         }
                     }
                     i++;
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
                }
                if( grid.currentCharacter.currentHealth <= 0 ) {
                    System.out.println("You were killed.");
                    System.exit(0);
                }
                if(i != 0) {
                    grid.showGrid();
                    scanner.nextLine();
                }
            }
            System.out.print("Next command: ");
            try {
                command = scanner.nextLine();
                if( !validCommands.contains(command) ) {
                    throw new InvalidCommandException();
                }
            }
            catch(InvalidCommandException e) {
                System.out.println(e.getMessage());
                continue;
            }
            if( command.compareTo("enter") == 0 && grid.currentCell.type == CellEnum.SHOP ) {
                Shop shop = (Shop) grid.currentCell.element;
                System.out.println(grid.currentCharacter.inventory);
                System.out.println(shop);
                System.out.println("exit -> EXIT");
                String shopCommand = null;
                while(true) {
                    System.out.print("Choose:");
                    try {
                        shopCommand = scanner.nextLine();
                        if(shopCommand.compareTo("exit") == 0) {
                            break;
                        }
                        if (Integer.parseInt(shopCommand) >= shop.potions.size()) {
                            throw new InvalidCommandException();
                        }
                    } catch (InvalidCommandException | NumberFormatException e) {
                        System.out.println(new InvalidCommandException().getMessage());
                        continue;
                    }
                    break;
                }
                if(shopCommand.compareTo("exit") == 0) {
                    continue;
                }
                else {
                    int potionIndex = Integer.parseInt(shopCommand);
                    try {
                        grid.currentCharacter.buyPotion(shop.selectPotion(potionIndex));
                    }
                    catch (NoMoneyException | FullInventoryException e) {
                        System.out.println(e.getMessage());
                    }
                }
                continue;
            }
            if( command.compareTo("inventory") == 0 ) {
                checkInventory(grid);
                continue;
            }
            switch (command) {
                case "quit" : System.exit(0);
                case "s" : grid.goSouth(); break;
                case "n" : grid.goNorth(); break;
                case "w" : grid.goWest(); break;
                case "e" : grid.goEast(); break;
                case "parameters" : grid.currentCharacter.showParameters(); break;
                case "stats" : grid.currentCharacter.showStats(); break;
            }
        }
    }
    private static void checkInventory(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(grid.currentCharacter.inventory);
        System.out.println("exit -> EXIT");
        String potionCommand = null;
        while(true) {
            System.out.print("Choose:");
            try {
                potionCommand = scanner.nextLine();
                if(potionCommand.compareTo("exit") == 0) {
                    break;
                }
                if( Integer.parseInt(potionCommand) >= grid.currentCharacter.inventory.potions.size()) {
                    throw new InvalidCommandException();
                }
            }
            catch (InvalidCommandException | NumberFormatException e) {
                System.out.println(new InvalidCommandException().getMessage());
                continue;
            }
            break;
        }
        if(potionCommand.compareTo("exit") == 0) {
            return;
        }
        else {
            int potionIndex = Integer.parseInt(potionCommand);
            grid.currentCharacter.inventory.potions.get(potionIndex).usePotion(grid.currentCharacter);
            grid.currentCharacter.showParameters();
        }
    }
}