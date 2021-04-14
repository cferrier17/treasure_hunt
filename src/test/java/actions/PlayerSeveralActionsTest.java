package actions;

import compute.PlayerMovements;
import model.Adventurer;
import model.ExplorationMap;
import model.direction.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static model.ExplorationMap.*;
import static model.direction.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayerSeveralActionsTest {

    private final PlayerMovements playerMovements = new PlayerMovements();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @CsvSource( value = {
            "AA,SOUTH,0,0,SOUTH,0,2",
            "AA,NORTH,0,2,NORTH,0,0",
            "AA,WEST,2,0,WEST,0,0",
            "AA,EAST,0,0,EAST,2,0",
            "AAG,NORTH,0,2,WEST,0,0",
            "AAD,NORTH,0,2,EAST,0,0",
            "AAAAA,SOUTH,0,0,SOUTH,0,5",
            "AGADAGADAGADAGADAGAD,SOUTH,0,0,SOUTH,5,5"


    })
    void is_move_player_ok_for_one_adventurer (String actions, Direction directionStart, int xStart, int yStart, Direction directionEnd, int xEnd, int yEnd) {
        Coordinates adventurerCoordinates = new Coordinates(xStart, yStart);
        Adventurer chief = new Adventurer("Chief", directionStart, actions, adventurerCoordinates, 1, 0);
        basicExplorationMap.getCells().get(adventurerCoordinates).setAdventurer(chief);
        basicExplorationMap.getAdventurers().add(chief);

        ExplorationMap explorationMap = playerMovements.movePlayers(basicExplorationMap);

        Adventurer adventurer = explorationMap.getAdventurers().get(0);

        assertThat(adventurer.getDirection()).isEqualTo(directionEnd);
        assertThat(adventurer.getCoordinates()).isEqualTo(new Coordinates(xEnd, yEnd));
    }

    @ParameterizedTest
    @MethodSource("generateSeveralAdventurersData")
    void is_move_player_ok_for_several_adventurer (List<String> actions, List<String> adventurerNames, List<Direction> directionsStart, List<Integer> xStarts, List<Integer> yStarts, List<Direction> directionsEnd, List<Integer> xEnds, List<Integer> yEnds, List<Integer> priorities) {
        for (int i = 0; i < actions.size(); i++) {
            Coordinates coordinates = new Coordinates(xStarts.get(i), yStarts.get(i));
            Adventurer adventurer = new Adventurer(adventurerNames.get(i), directionsStart.get(i), actions.get(i), coordinates, priorities.get(i), 0);

            basicExplorationMap.getCells().get(coordinates).setAdventurer(adventurer);
            basicExplorationMap.getAdventurers().add(adventurer);
        }


        ExplorationMap explorationMap = playerMovements.movePlayers(basicExplorationMap);

        List<Adventurer> adventurers = explorationMap.getAdventurers();

        for (int i = 0; i < adventurers.size(); i++) {
            Adventurer adventurer = adventurers.get(i);
            assertThat(adventurer.getCoordinates()).isEqualTo(new Coordinates(xEnds.get(i), yEnds.get(i)));
            assertThat(adventurer.getDirection()).isEqualTo(directionsEnd.get(i));
        }
    }


    static Stream<Arguments> generateSeveralAdventurersData () {
        return Stream.of(
                Arguments.of(Arrays.asList("AA","AA"),
                        Arrays.asList("Lara","Chief"),
                        Arrays.asList(SOUTH, SOUTH),
                        Arrays.asList(0,1),
                        Arrays.asList(0,0),
                        Arrays.asList(SOUTH, SOUTH),
                        Arrays.asList(0,1),
                        Arrays.asList(2,2),
                        Arrays.asList(1,2)
                ),
                Arguments.of(Arrays.asList("A","A"),
                        Arrays.asList("Lara","Chief"),
                        Arrays.asList(SOUTH, WEST),
                        Arrays.asList(0,1),
                        Arrays.asList(0,1),
                        Arrays.asList(SOUTH, WEST),
                        Arrays.asList(0,1),
                        Arrays.asList(1,1),
                        Arrays.asList(1,2)
                )
        );
    }

}
