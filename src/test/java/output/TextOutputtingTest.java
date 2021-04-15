package output;

import compute.PlayerMovements;
import model.ExplorationMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import parsing.*;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TextOutputtingTest {
    private final MapParser mapParser = new MapParser();
    private final MountainParser mountainParser = new MountainParser();
    private final TreasureParser treasureParser = new TreasureParser();
    private final AdventurerParser adventurerParser = new AdventurerParser();

    private final InputParser inputParser = new InputParser(mapParser, mountainParser, treasureParser, adventurerParser);
    private final PlayerMovements playerMovements = new PlayerMovements();
    private final TextOutputting textOutputting = new TextOutputting();

    @ParameterizedTest
    @CsvSource( value = {
            "C - 3 - 4",
            "C - 5 - 5"
    })
    void is_map_output_ok (String input) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        String mapOutput = textOutputting.getMapOutput(explorationMap);

        assertThat(mapOutput).isEqualToIgnoringNewLines(input);
    }

    @ParameterizedTest
    @MethodSource("generateMountainsData")
    void is_mountains_output_ok (String input, String expectedOutput) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        String mountainsOutput = textOutputting.getMountainsOutput(explorationMap.getCells());

        assertThat(mountainsOutput).isEqualToIgnoringNewLines(expectedOutput);
    }

    static Stream<Arguments> generateMountainsData () {
        return Stream.of(
                Arguments.arguments("C - 3 - 4\nM - 1 - 0\nM - 2 - 1", "M - 1 - 0\nM - 2 - 1")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTreasuresData")
    void is_treasures_output_ok (String input, String expectedOutput) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        String treasuresOutput = textOutputting.getTreasuresOutput(explorationMap.getCells());

        assertThat(treasuresOutput).isEqualToIgnoringNewLines(expectedOutput);
    }

    static Stream<Arguments> generateTreasuresData () {
        return Stream.of(
                Arguments.arguments("C - 3 - 4\nT - 1 - 0 - 2\nT - 2 - 1 - 1", "T - 1 - 0 - 2\nT - 2 - 1 - 1")
        );
    }

    @ParameterizedTest
    @MethodSource("generateAdventurersData")
    void is_adventurers_output_ok (String input, String expectedOutput) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        ExplorationMap newMap = playerMovements.movePlayers(explorationMap);
        String adventurersOutput = textOutputting.getAdventurersOutput(newMap.getAdventurers());

        assertThat(adventurersOutput).isEqualToIgnoringNewLines(expectedOutput);
    }

    static Stream<Arguments> generateAdventurersData () {
        return Stream.of(
                Arguments.arguments("C - 3 - 4\nT - 0 - 3 - 2\nT - 1 - 3 - 3\nA - Lara - 1 - 1 - S - AA", "A - Lara - 1 - 3 - S - 1"),
                Arguments.arguments("C - 3 - 4\nT - 0 - 3 - 2\nT - 1 - 3 - 3\nA - Lara - 1 - 1 - S - AADA", "A - Lara - 0 - 3 - W - 2")
        );
    }


    @ParameterizedTest
    @MethodSource("generateSimpleOutputData")
    void is_simple_output_ok (String input, String output) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        explorationMap = playerMovements.movePlayers(explorationMap);
        String s = textOutputting.outputMapAfterActions(explorationMap);

        assertThat(s).isEqualToIgnoringNewLines(output);
    }


    static Stream<Arguments> generateSimpleOutputData () {
        return Stream.of(
                Arguments.of(
                    "C - 3 - 4\n" +
                    "M - 1 - 0\n" +
                    "M - 2 - 1\n" +
                    "T - 0 - 3 - 2\n" +
                    "T - 1 - 3 - 3\n" +
                    "A - Lara - 1 - 1 - S - AADADAGGA",
                    "C - 3 - 4\n" +
                    "M - 1 - 0\n" +
                    "M - 2 - 1\n" +
                    "T - 1 - 3 - 2\n" +
                    "A - Lara - 0 - 3 - S - 3"
        ));
    }

    @ParameterizedTest
    @MethodSource("generateComplexOutputData")
    void is_complex_output_ok (String input, String output) {
        ExplorationMap explorationMap = inputParser.readInput(input);
        explorationMap = playerMovements.movePlayers(explorationMap);
        String s = textOutputting.outputMapAfterActions(explorationMap);

        assertThat(s).isEqualToIgnoringNewLines(output);
    }


    static Stream<Arguments> generateComplexOutputData () {
        return Stream.of(
                Arguments.of(
                        "C - 5 - 5\n" +
                        "M - 0 - 0\n" +
                        "M - 2 - 0\n" +
                        "M - 1 - 1\n" +
                        "T - 3 - 0 - 3\n" +
                        "T - 2 - 1 - 2\n" +
                        "T - 0 - 2 - 1\n" +
                        "T - 1 - 2 - 1\n" +
                        "A - Lara - 1 - 0 - N - AGAGAGAG\n" +
                        "A - Chief - 3 - 2 - W - ADADAGA\n" +
                        "A - Ash - 1 - 4 - N - AAGAA",
                        "C - 5 - 5\n" +
                        "M - 0 - 0\n" +
                        "M - 1 - 1\n" +
                        "M - 2 - 0\n" +
                        "T - 2 - 1 - 1\n" +
                        "T - 3 - 0 - 2\n" +
                        "A - Lara - 1 - 0 - N - 0\n" +
                        "A - Chief - 3 - 0 - N - 2\n" +
                        "A - Ash - 0 - 2 - W - 2"
                ));
    }
}
