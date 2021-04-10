import model.Cell;
import model.ExplorationMap;
import model.MountainCell;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parsing.InputParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MountainParsingTest {

    public final InputParser inputParser = new InputParser();

    @ParameterizedTest
    @MethodSource("generateMountainsData")
    void are_mountains_well_parsed (String input, List<Integer> moutainsXpositions, List<Integer> moutainsYpositions) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        Map<ExplorationMap.Coordinates, Cell> cells = explorationMap.getCells();

        for (int i = 0; i < moutainsXpositions.size(); i++) {
            ExplorationMap.Coordinates coordinates = new ExplorationMap.Coordinates(moutainsXpositions.get(i), moutainsYpositions.get(i));
            assertThat(cells.get(coordinates)).isOfAnyClassIn(MountainCell.class);
        }
    }

    static Stream<Arguments> generateMountainsData () {
        return Stream.of(
                Arguments.of("C - 3 - 4\nM - 0 - 0", Arrays.asList(0), Arrays.asList(0)),
                Arguments.of("C - 3 - 4\nM - 0 - 0\nM - 1 - 1", Arrays.asList(0,1), Arrays.asList(0,1))
        );
    }
}
