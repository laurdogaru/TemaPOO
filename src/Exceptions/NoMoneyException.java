package Exceptions;

public class NoMoneyException extends Exception{
    public NoMoneyException() {
        super("You don't have enough money!");
    }
}
