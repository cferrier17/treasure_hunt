package compute;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Cell;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;
import model.direction.Direction;

public class PlayerMovements {

    public void movePlayers (ExplorationMap explorationMap) {
        //todo : mettre les listes de commande dans une variable pour les faire se déplacer tour par tour
    }


    public CoordinatesWithDirection singleMovement (String action, Direction direction, Coordinates coordinates, ExplorationMap explorationMap) {

        switch (action) {
            case "A" : coordinates = moveForward(direction, coordinates, explorationMap); break;
            case "G" : direction = direction.turnLeft(); break;
            case "D" : direction = direction.turnRight(); break;
        }

        return new CoordinatesWithDirection(coordinates, direction);
    }

    public Coordinates moveForward (Direction direction, Coordinates coordinates, ExplorationMap explorationMap) {

        int x = coordinates.getPosX();
        int y = coordinates.getPosY();

        Coordinates coordinates1 = new Coordinates(x,y);

        switch (direction) {
            case NORTH: coordinates1 = new Coordinates(x, y - 1); break;
            case SOUTH: coordinates1 = new Coordinates(x, y + 1); break;
            case EAST: coordinates1 = new Coordinates(x + 1, y); break;
            case WEST: coordinates1 = new Coordinates(x - 1, y); break;
        }

        if (isNewCellAvailable(coordinates1, explorationMap)) {
            return coordinates1;
        }
        else {
            return coordinates;
        }

    }

    //handles mountains, map boundaries, other adventurers
    public boolean isNewCellAvailable (Coordinates coordinates, ExplorationMap explorationMap) {

        Cell cell = explorationMap.getCells().get(coordinates);

        return coordinates.getPosX() >= 0 && coordinates.getPosY() >= 0 &&
                coordinates.getPosX() <= explorationMap.getWidth() && coordinates.getPosY() <= explorationMap.getLength() &&
                cell.isCrossable() && cell.getAdventurer() == null;
    }

    @Data
    @AllArgsConstructor
    public static class CoordinatesWithDirection {
        private Coordinates coordinates;
        private Direction direction;
    }


}
