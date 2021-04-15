package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Cell {
    private int x;
    private int y;
    private boolean isCrossable;
    private Adventurer adventurer;
    private int numberOfTreasures;

    public Cell(int x, int y, boolean isCrossable, int numberOfTreasures) {
        this.x = x;
        this.y = y;
        this.isCrossable = isCrossable;
        this.numberOfTreasures = numberOfTreasures;
    }
}
