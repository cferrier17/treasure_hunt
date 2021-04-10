import model.ExplorationMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parsing.InputParser;

import static org.assertj.core.api.Assertions.assertThat;


public class MapParsingTest {
    public final InputParser inputParser = new InputParser();

    @ParameterizedTest
    @CsvSource(value = {
            "C - 3 - 4,3,4",
            "C - 10 - 20,10,20",
            "C - 100 - 200,100,200"
    })
    void is_map_well_created(String input, int expectedWidth, int expectedLength) {
        ExplorationMap explorationMap = inputParser.readInput(input);

        assertThat(explorationMap.getWidth()).isEqualTo(expectedWidth);
        assertThat(explorationMap.getLength()).isEqualTo(expectedLength);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CC - 3 - 4",
        "D - 3 - 4",
        "C - 3  - 4",
        "C  3  - 4",
        "C  34",
        "C\n - 3 - 4",
        "C - A3 - 4",
        "C - A - 4"
    })
    void is__map_parser_solid(String input) {
        ExplorationMap explorationMap = inputParser.readInput(input);

        assertThat(explorationMap.getWidth()).isEqualTo(0);
        assertThat(explorationMap.getLength()).isEqualTo(0);
    }




}
