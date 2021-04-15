package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.ExplorationMap.Coordinates;

@Data
@NoArgsConstructor
public abstract class Cell {
    private Coordinates coordinates;
    private boolean isCrossable;
    private Adventurer adventurer;
    private int numberOfTreasures;

    public Cell(int x, int y, boolean isCrossable, int numberOfTreasures) {
        this.coordinates = new Coordinates(x,y);
        this.isCrossable = isCrossable;
        this.numberOfTreasures = numberOfTreasures;
    }


}
