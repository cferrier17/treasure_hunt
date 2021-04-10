import model.ExplorationMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parsing.InputParser;
import parsing.MapParser;
import parsing.MountainParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;


public class MapParsingTest {
    private final MapParser mapParser = new MapParser();

    @ParameterizedTest
    @CsvSource(value = {
            "C - 3 - 4,3,4",
            "C - 10 - 20,10,20",
            "C - 100 - 200,100,200"
    })
    void is_map_well_created(String input, int expectedWidth, int expectedLength) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(input);
        ExplorationMap explorationMap = mapParser.parse(inputs, new ExplorationMap()).getExplorationMap();

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
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(input);
        ExplorationMap explorationMap = mapParser.parse(inputs, new ExplorationMap()).getExplorationMap();

        assertThat(explorationMap.getWidth()).isEqualTo(0);
        assertThat(explorationMap.getLength()).isEqualTo(0);
    }




}
