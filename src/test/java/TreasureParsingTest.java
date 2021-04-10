import model.Cell;
import model.ExplorationMap;
import model.TreasureCell;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parsing.TreasureParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static model.ExplorationMap.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TreasureParsingTest {

    private final TreasureParser treasureParser = new TreasureParser();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @MethodSource("generateMountainsData")
    void are_treasures_well_parsed (List<String> input, List<Integer> moutainsXpositions, List<Integer> moutainsYpositions, List<Integer> numberOfTreasures) {
        ExplorationMap explorationMap = treasureParser.parse(new ArrayList<>(input) , basicExplorationMap).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        for (int i = 0; i < moutainsXpositions.size(); i++) {
            Coordinates coordinates = new Coordinates(moutainsXpositions.get(i), moutainsYpositions.get(i));
            assertThat(cells.get(coordinates)).isOfAnyClassIn(TreasureCell.class);
            TreasureCell treasureCell = (TreasureCell) cells.get(coordinates);
            assertThat(treasureCell.getNumberOfTreasures()).isEqualTo(numberOfTreasures.get(i));
        }
    }

    static Stream<Arguments> generateMountainsData () {
        return Stream.of(
                Arguments.of(Arrays.asList("T - 0 - 0 - 1"), Arrays.asList(0), Arrays.asList(0), Arrays.asList(1)),
                Arguments.of(Arrays.asList("T - 0 - 0 - 1", "T - 2 - 1 - 3"), Arrays.asList(0,2), Arrays.asList(0,1), Arrays.asList(1,3))
        );
    }

    @ParameterizedTest
    @MethodSource("generateMountainsWrongData")
    void is__mountain_parser_solid (List<String> input) {
        ExplorationMap explorationMap = treasureParser.parse(new ArrayList<>(input) , basicExplorationMap).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        cells.values().forEach(cell -> assertThat(cell).isNotOfAnyClassIn(TreasureCell.class));
    }

    static Stream<Arguments> generateMountainsWrongData () {
        return Stream.of(
                Arguments.of(Arrays.asList("T - 0 - 0 - 0")),
                Arguments.of(Arrays.asList("T - 6 - 6 - 6")),
                Arguments.of(Arrays.asList("T  0 - 0 - 3")),
                Arguments.of(Arrays.asList("5  0 - 0 - 3"))

        );
    }
}
