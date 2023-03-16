package pl.pracinho.countrycitygame.model.entity.memory;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private int index;
    private int completedRounds;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
        this.completedRounds = 0;
    }

    public void incrementCompletedRounds() {
        this.completedRounds++;
    }


}