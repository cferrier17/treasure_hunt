package model;

import lombok.Data;

@Data
public class TreasureCell extends Cell {
    private int numberOfTreasures;


    public TreasureCell(int x, int y, int numberOfTreasures) {
        super(x, y, true);
        this.numberOfTreasures = numberOfTreasures;
    }
}
