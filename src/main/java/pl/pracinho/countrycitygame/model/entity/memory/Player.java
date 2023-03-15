package pl.pracinho.countrycitygame.model.entity.memory;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private int index;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
    }


}