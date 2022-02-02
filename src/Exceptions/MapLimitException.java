package Exceptions;

public class MapLimitException extends Exception{
    public MapLimitException() {
        super("You have reached the limit of the map!");
    }
}
