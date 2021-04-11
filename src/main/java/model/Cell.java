package model;

import lombok.Data;

@Data
public abstract class Cell {
    private int x;
    private int y;
    private boolean isCrossable;
    private Adventurer adventurer;

    public Cell(int x, int y, boolean isCrossable) {
        this.x = x;
        this.y = y;
        this.isCrossable = isCrossable;
    }
}
