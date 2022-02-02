package Exceptions;

public class FullInventoryException extends Exception{
    public FullInventoryException() {
        super("Your inventory is full!");
    }
}
