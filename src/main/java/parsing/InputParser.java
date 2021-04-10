package parsing;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.ExplorationMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


@AllArgsConstructor
public class InputParser {

    private final MapParser mapParser;
    private final MountainParser mountainParser;

    public ExplorationMap readInput (String data) {
        ArrayList<String> lines =  new ArrayList<>(Arrays.asList(data.split("\n")));

        ExplorationMap explorationMap = new ExplorationMap(0 ,0);

        if (lines.size() < 1) {
            return explorationMap;
        }

        ParsingInfo parsingInfoMap = mapParser.parse(lines, explorationMap);
        lines = parsingInfoMap.getInput();
        explorationMap = parsingInfoMap.getExplorationMap();

        if (lines.size() < 1) {
            return explorationMap;
        }

        ParsingInfo parsingInfoMountain = mountainParser.parse(lines, explorationMap);
        explorationMap = parsingInfoMountain.getExplorationMap();
        lines = parsingInfoMap.getInput();

        return explorationMap;
    }


    @Data
    @AllArgsConstructor
    public static class ParsingInfo {
        private ArrayList<String> input;
        private ExplorationMap explorationMap;
    }

}