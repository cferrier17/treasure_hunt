package parsing;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.ExplorationMap;

import java.util.ArrayList;
import java.util.Arrays;



@AllArgsConstructor
public class InputParser {

    private final MapParser mapParser;
    private final MountainParser mountainParser;
    private final TreasureParser treasureParser;
    private final AdventurerParser adventurerParser;

    public ExplorationMap readInput (String data) {
        ArrayList<String> lines =  new ArrayList<>(Arrays.asList(data.split("\n")));

        ExplorationMap explorationMap = new ExplorationMap(0 ,0);

        if (lines.size() < 1) {
            return explorationMap;
        }

        ParsingInfo parsingInfo = new ParsingInfo(lines, explorationMap);

        parsingInfo = mapParser.parse(parsingInfo);
        parsingInfo = mountainParser.parse(parsingInfo);
        parsingInfo = treasureParser.parse(parsingInfo);
        parsingInfo = adventurerParser.parse(parsingInfo);

        return parsingInfo.getExplorationMap();
    }


    @Data
    @AllArgsConstructor
    public static class ParsingInfo {
        private ArrayList<String> input;
        private ExplorationMap explorationMap;
    }

}