package parsing;

import model.*;
import model.direction.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static model.ExplorationMap.*;
import static model.direction.Direction.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static model.direction.Direction.SOUTH;

public class InputParserTest {
    private final MapParser mapParser = new MapParser();
    private final MountainParser mountainParser = new MountainParser();
    private final TreasureParser treasureParser = new TreasureParser();
    private final AdventurerParser adventurerParser = new AdventurerParser();

    private final InputParser inputParser = new InputParser(mapParser, mountainParser, treasureParser, adventurerParser);

    @ParameterizedTest
    @MethodSource("generateInputData")
    void is_input_parsed_well (String input, int mapWidths, int mapLengths,
                               List<Integer> moutainsXpositions, List<Integer> moutainsYpositions,
                               List<Integer> treasuresXpositions, List<Integer> treasuresYpositions, List<Integer> numberOfTreasures,
                               List<String> adventurerNames, List<Integer> adventurerXpositions, List<Integer> adventurerYpositions,
                               List<Direction> directions, List<String> actions, List<Integer> priorities
                               ) {


        ExplorationMap explorationMap = inputParser.readInput(input);

        Map<Coordinates, Cell> cells = explorationMap.getCells();


            assertThat(explorationMap.getWidth()).isEqualTo(mapWidths);
            assertThat(explorationMap.getLength()).isEqualTo(mapLengths);


        for (int i = 0; i < moutainsXpositions.size(); i++) {
            assertThat(cells.get(new Coordinates(moutainsXpositions.get(i),
                    moutainsYpositions.get(i)))).isOfAnyClassIn(MountainCell.class);
        }

        for (int i = 0; i < treasuresXpositions.size(); i++) {
            Coordinates treasureCoordinates = new Coordinates(treasuresXpositions.get(i),
                    treasuresYpositions.get(i));
            assertThat(cells.get(treasureCoordinates)).isOfAnyClassIn(TreasureCell.class);
            TreasureCell treasureCell = (TreasureCell) cells.get(treasureCoordinates);
            assertThat(treasureCell.getNumberOfTreasures()).isEqualTo(numberOfTreasures.get(i));
        }

        List<Adventurer> adventurers = explorationMap.getAdventurers();
        for (int i = 0; i < adventurerNames.size(); i++) {
            Adventurer adventurer = new Adventurer(adventurerNames.get(i), directions.get(i), actions.get(i),
                    new Coordinates(adventurerXpositions.get(i), adventurerYpositions.get(i)), priorities.get(i), 0);
            assertThat(adventurers.get(i)).isEqualTo(adventurer);
        }


    }

    static Stream<Arguments> generateInputData () {
        return Stream.of(
                Arguments.of(
                        "C - 3 - 4\n" +
                        "M - 1 - 0\nM - 2 - 1\n" +
                        "T - 0 - 3 - 2\nT - 1 - 3 - 3\n" +
                        "A - Lara - 1 - 2 - S - AADADAGGA",
                        3, 4,
                        Arrays.asList(1, 2), Arrays.asList(0,1),
                        Arrays.asList(0, 1), Arrays.asList(3,3), Arrays.asList(2,3),
                        Arrays.asList("Lara"), Arrays.asList(1), Arrays.asList(2), Arrays.asList(SOUTH), Arrays.asList("AADADAGGA"), Arrays.asList(1)
                ),
                Arguments.of(
                        "C - 30 - 20\n" +
                        "M - 1 - 0\nM - 2 - 1\nM - 15 - 14\n" +
                        "T - 0 - 3 - 2\nT - 1 - 3 - 3\nT - 19 - 8 - 1\n" +
                        "A - Lara - 1 - 2 - S - AADADAGGA\nA - Chief - 22 - 12 - N - GA",
                        30, 20,
                        Arrays.asList(1,2,15), Arrays.asList(0,1,14),
                        Arrays.asList(0,1,19), Arrays.asList(3,3,8), Arrays.asList(2,3,1),
                        Arrays.asList("Lara","Chief"), Arrays.asList(1,22), Arrays.asList(2,12), Arrays.asList(SOUTH,NORTH), Arrays.asList("AADADAGGA","GA"), Arrays.asList(1,2)
                ),
                Arguments.of(
                        "C - 30 - 20\n" +
                                "M - 1 - 0\nM - 2 - 1\nM - 15 - 14\n" +
                                "T - 0 - 3 - 2\nT - 1 - 3 - 3\nT - 19 - 8 - 1\n" +
                                "A - Lara - 1 - 2 - S - AADADAGGA\nA - Chief - 22 - 12 - N - GA",
                        30, 20,
                        Arrays.asList(1,2,15), Arrays.asList(0,1,14),
                        Arrays.asList(0,1,19), Arrays.asList(3,3,8), Arrays.asList(2,3,1),
                        Arrays.asList("Lara","Chief"), Arrays.asList(1,22), Arrays.asList(2,12), Arrays.asList(SOUTH,NORTH), Arrays.asList("AADADAGGA","GA"), Arrays.asList(1,2)
                )

        );
    }
}
