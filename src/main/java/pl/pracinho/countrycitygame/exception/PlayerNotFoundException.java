package pl.pracinho.countrycitygame.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String name) {
        super("Player " + name + " not playing this game!");
    }
}