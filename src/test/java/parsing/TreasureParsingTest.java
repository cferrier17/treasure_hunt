package parsing;

import model.Cell;
import model.ExplorationMap;
import model.TreasureCell;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import parsing.InputParser.ParsingInfo;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static model.ExplorationMap.Coordinates;
import static org.assertj.core.api.Assertions.assertThat;

public class TreasureParsingTest {

    private final TreasureParser treasureParser = new TreasureParser();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @MethodSource("generateTreasuresData")
    void are_treasures_well_parsed (List<String> input, List<Integer> treasuresXpositions, List<Integer> treasuresYpositions, List<Integer> numberOfTreasures) {
        ParsingInfo parsingInfo = new ParsingInfo(new ArrayList<>(input), basicExplorationMap);

        ExplorationMap explorationMap = treasureParser.parse(parsingInfo).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        for (int i = 0; i < treasuresXpositions.size(); i++) {
            Coordinates coordinates = new Coordinates(treasuresXpositions.get(i), treasuresYpositions.get(i));
            assertThat(cells.get(coordinates)).isOfAnyClassIn(TreasureCell.class);
            TreasureCell treasureCell = (TreasureCell) cells.get(coordinates);
            assertThat(treasureCell.getNumberOfTreasures()).isEqualTo(numberOfTreasures.get(i));
        }
    }

    static Stream<Arguments> generateTreasuresData() {
        return Stream.of(
                Arguments.of(Arrays.asList("T - 0 - 0 - 1"), Arrays.asList(0), Arrays.asList(0), Arrays.asList(1)),
                Arguments.of(Arrays.asList("T - 0 - 0 - 1", "T - 2 - 1 - 3"), Arrays.asList(0,2), Arrays.asList(0,1), Arrays.asList(1,3))
        );
    }

    @ParameterizedTest
    @CsvSource(value={
        "T - 0 - 0 - 0",
        "T - 6 - 6 - 6",
        "T  0 - 0 - 3",
        "5  0 - 0 - 3"
    })
    void is_treasure_parser_solid(String input) {
        ParsingInfo parsingInfo = new ParsingInfo(new ArrayList<>(singletonList(input)), basicExplorationMap);

        ExplorationMap explorationMap = treasureParser.parse(parsingInfo).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        cells.values().forEach(cell -> assertThat(cell).isNotOfAnyClassIn(TreasureCell.class));
    }


}
