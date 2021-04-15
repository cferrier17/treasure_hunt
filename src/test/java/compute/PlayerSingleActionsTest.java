package compute;

import model.Adventurer;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;
import model.MountainCell;
import model.direction.Direction;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


public class PlayerSingleActionsTest {

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
        Adventurer adventurer = new Adventurer();
        adventurer.setDirection(direction);
        adventurer.setCoordinates(new Coordinates(xStart, yStart));
        Coordinates coordinates = playerMovements.moveForward(adventurer, basicExplorationMap);

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
        Adventurer adventurer = new Adventurer();
        adventurer.setDirection(direction);
        adventurer.setCoordinates(new Coordinates(xStart, yStart));
        Coordinates coordinates = playerMovements.moveForward(adventurer, basicExplorationMap);

        assertThat(coordinates.getPosX()).isEqualTo(xStart);
        assertThat(coordinates.getPosY()).isEqualTo(yStart);
    }

    @ParameterizedTest
    @CsvSource( value = {
        "NORTH,1,1,1,0"
    })
    void are_mountains_ok (Direction direction, int xStart, int yStart, int xMountain, int yMountain) {
        Adventurer adventurer = new Adventurer();
        adventurer.setDirection(direction);
        adventurer.setCoordinates(new Coordinates(xStart, yStart));

        basicExplorationMap.getCells().put(new Coordinates(xMountain, yMountain), new MountainCell(xStart, yStart));
        Coordinates coordinates = playerMovements.moveForward(adventurer, basicExplorationMap);

        assertThat(coordinates.getPosX()).isEqualTo(xStart);
        assertThat(coordinates.getPosY()).isEqualTo(yStart);
    }

    @ParameterizedTest
    @CsvSource( value = {
            "NORTH,1,1,1,0"
    })
    void are_adventurers_ok (Direction direction, int xStart, int yStart, int xAdventurer, int yAdventurer) {
        Adventurer adventurer = new Adventurer();
        adventurer.setDirection(direction);
        adventurer.setCoordinates(new Coordinates(xStart, yStart));

        basicExplorationMap.getCells().get(new Coordinates(xAdventurer, yAdventurer)).setAdventurer(adventurer);

        Coordinates coordinates = playerMovements.moveForward(adventurer, basicExplorationMap);
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
    void is_single_movement_ok (char action, Direction directionStart, int xStart, int yStart, Direction directionEnd, int xEnd, int yEnd) {
        Adventurer adventurer = new Adventurer();
        adventurer.setDirection(directionStart);
        adventurer.setCoordinates(new Coordinates(xStart, yStart));
        Adventurer newAdventurer = playerMovements.singleMovement(action, adventurer, basicExplorationMap);

        assertThat(newAdventurer.getCoordinates().getPosX()).isEqualTo(xEnd);
        assertThat(newAdventurer.getCoordinates().getPosY()).isEqualTo(yEnd);
        assertThat(newAdventurer.getDirection()).isEqualTo(directionEnd);
    }



    @Test
    public void is_adventurer_well_found () {
        Coordinates adventurerCoordinates = new Coordinates(0, 0);
        String adventurerName = "Chief";
        Adventurer chief = new Adventurer(adventurerName, Direction.NORTH, "", adventurerCoordinates, 1, 0);
        basicExplorationMap.getCells().get(adventurerCoordinates).setAdventurer(chief);

        Coordinates chiefCoordinates = playerMovements.findAdventurerCoordinates(adventurerName, basicExplorationMap);

        assertThat(chiefCoordinates).isEqualTo(adventurerCoordinates);
    }
}
