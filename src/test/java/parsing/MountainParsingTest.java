package parsing;

import model.Cell;
import model.ExplorationMap;
import model.MountainCell;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static model.ExplorationMap.Coordinates;
import static org.assertj.core.api.Assertions.assertThat;
import static parsing.InputParser.ParsingInfo;

public class MountainParsingTest {
    private final MountainParser mountainParser = new MountainParser();
    private final ExplorationMap basicExplorationMap = new ExplorationMap(5,5);

    @ParameterizedTest
    @MethodSource("generateMountainsData")
    void are_mountains_well_parsed (List<String> input, List<Integer> moutainsXpositions, List<Integer> moutainsYpositions) {
        ParsingInfo parsingInfo = new ParsingInfo(new ArrayList<>(input), basicExplorationMap);

        ExplorationMap explorationMap = mountainParser.parse(parsingInfo).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        for (int i = 0; i < moutainsXpositions.size(); i++) {
            Coordinates coordinates = new Coordinates(moutainsXpositions.get(i), moutainsYpositions.get(i));
            assertThat(cells.get(coordinates)).isOfAnyClassIn(MountainCell.class);
        }
    }

    static Stream<Arguments> generateMountainsData () {
        return Stream.of(
                Arguments.of(Arrays.asList("M - 0 - 0"), Arrays.asList(0), Arrays.asList(0)),
                Arguments.of(Arrays.asList("M - 0 - 0", "M - 1 - 1"), Arrays.asList(0,1), Arrays.asList(0,1))
        );
    }

    @ParameterizedTest
    @MethodSource("generateMountainsWrongData")
    void is__mountain_parser_solid (List<String> input) {
        ParsingInfo parsingInfo = new ParsingInfo(new ArrayList<>(input), basicExplorationMap);

        ExplorationMap explorationMap = mountainParser.parse(parsingInfo).getExplorationMap();
        Map<Coordinates, Cell> cells = explorationMap.getCells();

        cells.values().forEach(cell -> assertThat(cell).isNotOfAnyClassIn(MountainCell.class));
    }

    static Stream<Arguments> generateMountainsWrongData () {
        return Stream.of(
                Arguments.of(Arrays.asList("C - 3 - 4")),
                Arguments.of(Arrays.asList("M - 3  - 4")),
                Arguments.of(Arrays.asList("M  3  - 4")),
                Arguments.of(Arrays.asList("M  34")),
                Arguments.of(Arrays.asList("M\n - 3 - 4")),
                Arguments.of(Arrays.asList("M - 6 - 6")),
                Arguments.of(Arrays.asList("M - A3 - 4"))
        );
    }
}
