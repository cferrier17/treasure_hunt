package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.ExplorationMap.Coordinates;
import model.direction.Direction;

@Data
@AllArgsConstructor
public class Adventurer {
    private String name;
    private Direction direction;
    private String actions;
    private Coordinates coordinates;
    private int priority;
    private int pickedTreasures;
}
