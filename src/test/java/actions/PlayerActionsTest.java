package actions;

import compute.PlayerMovements;
import compute.PlayerMovements.CoordinatesWithDirection;
import model.Adventurer;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;
import model.MountainCell;
import model.direction.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


public class PlayerActionsTest {

    private final PlayerMovements playerMovements = new PlayerMovements();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @CsvSource( value = {
        "NORTH,1,1,1,0",
        "SOUTH,1,1,1,2",
        "EAST,1,1,2,1",
        "WEST,1,1,0,1",

    })
    void is_move_forward_ok (Direction direction, int xStart, int yStart, int xEnd, int yEnd) {
        Coordinates coordinates = playerMovements.moveForward(direction, new Coordinates(xStart, yStart), basicExplorationMap);

        assertThat(coordinates.getPosX()).isEqualTo(xEnd);
        assertThat(coordinates.getPosY()).isEqualTo(yEnd);
    }


    @ParameterizedTest
    @CsvSource( value = {
        "NORTH,1,0",
        "SOUTH,1,5",
        "EAST,5,1",
        "WEST,0,1"
    })
    void are_map_boundaries_ok (Direction direction, int xStart, int yStart) {
        Coordinates coordinates = playerMovements.moveForward(direction, new Coordinates(xStart, yStart), basicExplorationMap);

        assertThat(coordinates.getPosX()).isEqualTo(xStart);
        assertThat(coordinates.getPosY()).isEqualTo(yStart);
    }

    @ParameterizedTest
    @CsvSource( value = {
        "NORTH,1,1,1,0"
    })
    void are_mountains_ok (Direction direction, int xStart, int yStart, int xMountain, int yMountain) {

        basicExplorationMap.getCells().put(new Coordinates(xMountain, yMountain), new MountainCell(xStart, yStart));
        Coordinates coordinates = playerMovements.moveForward(direction, new Coordinates(xStart, yStart), basicExplorationMap);

        assertThat(coordinates.getPosX()).isEqualTo(xStart);
        assertThat(coordinates.getPosY()).isEqualTo(yStart);
    }

    @ParameterizedTest
    @CsvSource( value = {
            "NORTH,1,1,1,0"
    })
    void are_adventurers_ok (Direction direction, int xStart, int yStart, int xAdventurer, int yAdventurer) {
        basicExplorationMap.getCells().get(new Coordinates(xAdventurer, yAdventurer)).setAdventurer(new Adventurer());

        Coordinates coordinates = playerMovements.moveForward(direction, new Coordinates(xStart, yStart), basicExplorationMap);
        assertThat(coordinates.getPosX()).isEqualTo(xStart);
        assertThat(coordinates.getPosY()).isEqualTo(yStart);
    }


    @ParameterizedTest
    @CsvSource( value = {
            "NORTH,WEST,EAST",
            "SOUTH,EAST,WEST",
            "EAST,NORTH,SOUTH",
            "WEST,SOUTH,NORTH"
    })
    void is_rotation_ok (Direction directionStart, Direction directionAfterTurnLeft, Direction directionAfterTurnRight) {
        Direction directionLeft = directionStart.turnLeft();
        Direction directionRight = directionStart.turnRight();

        assertThat(directionLeft).isEqualTo(directionAfterTurnLeft);
        assertThat(directionRight).isEqualTo(directionAfterTurnRight);

    }


    @ParameterizedTest
    @CsvSource( value = {
            "A,NORTH,1,1,NORTH,1,0",
            "A,NORTH,1,0,NORTH,1,0",
            "G,NORTH,1,0,WEST,1,0",
            "D,NORTH,1,0,EAST,1,0",
    })
    void is_single_movement_ok (String action, Direction directionStart, int xStart, int yStart, Direction directionEnd, int xEnd, int yEnd) {
        CoordinatesWithDirection coordinatesWithDirection = playerMovements.singleMovement(action, directionStart, new Coordinates(xStart, yStart), basicExplorationMap);

        assertThat(coordinatesWithDirection.getCoordinates().getPosX()).isEqualTo(xEnd);
        assertThat(coordinatesWithDirection.getCoordinates().getPosY()).isEqualTo(yEnd);
        assertThat(coordinatesWithDirection.getDirection()).isEqualTo(directionEnd);
    }
}
