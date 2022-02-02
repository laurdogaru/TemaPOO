package Initial;

import Cells.*;
import Entities.*;
import SpellsAndPotions.*;


import java.util.*;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public int length;
    public int width;
    public Entities.Character currentCharacter;
    public Cell currentCell;
    private static Random random = new Random();

    private Grid(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public static Grid generatePredefinedMap() {
        Grid instance = new Grid(5, 5);
        for(int i = 0 ; i < 5 ; i++) {
            instance.add(new ArrayList<>());
        }
        for(int i = 0 ; i < 5 ; i++) {
            for(int j = 0 ; j < 5 ; j++) {
                instance.get(i).add(new Cell(j, i, CellEnum.EMPTY, new Empty(), false));
            }
        }
        instance.get(0).set(3, new Cell(3, 0,CellEnum.SHOP, new Shop(new ManaPotion(), new HealthPotion()), false));
        instance.get(1).set(3, new Cell(3, 1,CellEnum.SHOP, new Shop(), false));
        instance.get(2).set(0, new Cell(0, 2,CellEnum.SHOP, new Shop(), false));
        instance.get(3).set(4, new Cell(4, 3,CellEnum.ENEMY, new Enemy(), false));
        instance.get(4).set(4, new Cell(4, 4,CellEnum.FINISH, new Finish(), false));
        instance.currentCell = instance.get(0).get(0);
        return instance;
    }
    public static Grid generateRandomMap() {
        int length = 5 + random.nextInt(5);
        int width = 5 + random.nextInt(5);
        Grid instance = new Grid(length, width);
        for(int i = 0 ; i < length ; i++) {
            instance.add(new ArrayList<>());
        }
        for(int i = 0 ; i < length ; i++) {
            for(int j = 0 ; j < width ; j++) {
                instance.get(i).add(new Cell(j, i, CellEnum.EMPTY, new Empty(), false));
            }
        }
        int shopsNumber = (length * width) / 10;
        int enemiesNumber = (length * width) / 7;
        for(int i = 0 ; i < shopsNumber ; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(length);
            if( !(x == 0 && y == 0) ) {
                instance.get(y).set(x, new Cell(x, y,CellEnum.SHOP, new Shop(), false));
            }
        }
        for(int i = 0 ; i < enemiesNumber ; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(length);
            if( !(x == 0 && y == 0) ) {
                instance.get(y).set(x, new Cell(x, y,CellEnum.ENEMY, new Enemy(), false));
            }
        }
        int finishX = random.nextInt(width);
        int finishY = random.nextInt(length);
        instance.get(finishY).set(finishX, new Cell(finishX, finishY,CellEnum.FINISH, new Finish(), false));
        instance.currentCell = instance.get(0).get(0);
        return instance;
    }
    public void goNorth(){
        if( currentCell.y > 0 ) {
            int x = currentCell.x;
            int y = currentCell.y;
            currentCell = get(y-1).get(x);
            //colectarea de xp si coins
            if( !currentCell.visited ) {
                currentCharacter.xp += 2;
                if(currentCharacter.xp >= 100) {
                    currentCharacter.levelUp();
                }
                if (random.nextInt(5) == 0) {
                    currentCharacter.inventory.nrCoins += 2;
                }
            }
        }
        else {
            System.out.println("You have reached the limit of the map!");
        }
    }
    public void goSouth(){
        if( currentCell.y < length-1 ) {
            int x = currentCell.x;
            int y = currentCell.y;
            currentCell = get(y+1).get(x);
            //colectarea de xp si coins
            if( !currentCell.visited ) {
                currentCharacter.xp += 2;
                if(currentCharacter.xp >= 100) {
                    currentCharacter.levelUp();
                }
                if (random.nextInt(5) == 0) {
                    currentCharacter.inventory.nrCoins += 2;
                }
            }
        }
        else {
            System.out.println("You have reached the limit of the map!");
        }
    }
    public void goWest(){
        if( currentCell.x > 0 ) {
            int x = currentCell.x;
            int y = currentCell.y;
            currentCell = get(y).get(x-1);
            //colectarea de xp si coins
            if( !currentCell.visited ) {
                currentCharacter.xp += 2;
                if(currentCharacter.xp >= 100) {
                    currentCharacter.levelUp();
                }
                if (random.nextInt(5) == 0) {
                    currentCharacter.inventory.nrCoins += 2;
                }
            }
        }
        else {
            System.out.println("You have reached the limit of the map!");
        }
    }
    public void goEast(){
        if( currentCell.x < width-1 ) {
            int x = currentCell.x;
            int y = currentCell.y;
            currentCell = get(y).get(x+1);
            //colectarea de xp si coins
            if( !currentCell.visited ) {
                currentCharacter.xp += 2;
                if(currentCharacter.xp >= 100) {
                    currentCharacter.levelUp();
                }
                if (random.nextInt(5) == 0) {
                    currentCharacter.inventory.nrCoins += 2;
                }
            }
        }
        else {
            System.out.println("You have reached the limit of the map!");
        }
    }

    public void showGrid() {
        for(int i = 0 ; i < size() ; i++) {
            for(int j = 0 ; j < get(i).size(); j++) {
                if( get(i).get(j) == currentCell ) {
                    System.out.print('P');
                    System.out.print(get(i).get(j).element.toCharacter() + " ");
                    continue;
                }
                if( !get(i).get(j).visited ) {
                    System.out.print('?' + "  ");
                } else {
                    System.out.print(get(i).get(j).element.toCharacter() + "  ");
                }
            }
            System.out.println();
        }
    }

}
