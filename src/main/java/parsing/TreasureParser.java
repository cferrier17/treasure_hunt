package parsing;

import model.ExplorationMap;
import model.MountainCell;
import model.TreasureCell;
import parsing.InputParser.ParsingInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.ExplorationMap.*;

public class TreasureParser implements Parser {

    private final Pattern treasurePattern = Pattern.compile("^T\\s-\\s(\\d+)\\s-\\s(\\d+)\\s-\\s(\\d+)$");


    @Override
    public ParsingInfo parse(ArrayList<String> lines, ExplorationMap explorationMap) {
        String line = lines.get(0);
        Matcher treasureMatcher = treasurePattern.matcher(line);

        ParsingInfo parsingInfo = new ParsingInfo(lines, explorationMap);

        while( !lines.isEmpty() && treasureMatcher.matches()) {
            line = lines.remove(0);
            treasureMatcher = treasurePattern.matcher(line);
            parsingInfo.setInput(lines);

            treasureMatcher.find();

            int posX = Integer.parseInt(treasureMatcher.group(1));
            int posY = Integer.parseInt(treasureMatcher.group(2));
            int numberOfTreasures = Integer.parseInt(treasureMatcher.group(3));

            //check if x and y are in map range

            if (posX >= 0 && posX <= explorationMap.getWidth() &&
                posY >= 0 && posY <= explorationMap.getLength() &&
                numberOfTreasures > 0) {
                explorationMap.getCells().put(new Coordinates(posX, posY), new TreasureCell(posX, posY, numberOfTreasures));
            }


        }

        parsingInfo.setExplorationMap(explorationMap);
        return parsingInfo;
    }

}
