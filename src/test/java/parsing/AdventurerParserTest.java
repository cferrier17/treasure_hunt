package parsing;


import model.Adventurer;
import model.ExplorationMap;
import model.direction.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static model.ExplorationMap.*;
import static model.direction.Direction.NORTH;
import static model.direction.Direction.SOUTH;
import static org.assertj.core.api.Assertions.assertThat;

class AdventurerParserTest {
    private final AdventurerParser adventurerParser = new AdventurerParser();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @MethodSource("generateAdventurersData")
    void are_adventurers_well_parsed (List<String> input, List<String> names,  List<Integer> adventurerXpositions, List<Integer> adventurerYpositions,
                                    List<Direction> directions, List<String> actions, List<Integer> priorities) {
        InputParser.ParsingInfo parsingInfo = new InputParser.ParsingInfo(new ArrayList<>(input), basicExplorationMap);

        ExplorationMap explorationMap = adventurerParser.parse(parsingInfo).getExplorationMap();

        for (int i = 0; i < adventurerXpositions.size(); i++) {
            Adventurer adventurer = explorationMap.getAdventurers().get(i);

            assertThat(adventurer.getName()).isEqualTo(names.get(i));
            assertThat(adventurer.getCoordinates()).isEqualTo(new Coordinates(adventurerXpositions.get(i), adventurerYpositions.get(i)));
            assertThat(adventurer.getDirection()).isEqualTo(directions.get(i));
            assertThat(adventurer.getActions()).isEqualTo(actions.get(i));
            assertThat(adventurer.getPriority()).isEqualTo(priorities.get(i));

        }
    }

    static Stream<Arguments> generateAdventurersData() {
        return Stream.of(
                Arguments.of(Arrays.asList("A - Lara - 1 - 2 - S - AADADAGGA"),
                        Arrays.asList("Lara"), Arrays.asList(1), Arrays.asList(2), Arrays.asList(SOUTH), Arrays.asList("AADADAGGA"), Arrays.asList(1)),
                Arguments.of(Arrays.asList("A - Lara - 1 - 2 - S - AADADAGGA","A - Chief - 2 - 3 - N - AA"),
                        Arrays.asList("Lara","Chief"), Arrays.asList(1,2), Arrays.asList(2,3), Arrays.asList(SOUTH, NORTH), Arrays.asList("AADADAGGA","AA"), Arrays.asList(1,2)),
                Arguments.of(Arrays.asList("A - Lara - 1 - 2 - S - AADADAGGA","A - Chief - 1 - 2 - N - AA"),
                        Arrays.asList("Lara"), Arrays.asList(1), Arrays.asList(2), Arrays.asList(SOUTH), Arrays.asList("AADADAGGA"), Arrays.asList(1))
        );
    }
}