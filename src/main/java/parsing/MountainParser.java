package parsing;

import model.ExplorationMap;
import model.MountainCell;
import parsing.InputParser.ParsingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MountainParser implements Parser {

    private final Pattern mountainPattern = Pattern.compile("^M\\s-\\s(\\d+)\\s-\\s(\\d+)$");

    @Override
    public ParsingInfo parse(ArrayList<String> lines, ExplorationMap explorationMap) {
        String line = lines.get(0);
        Matcher mountainMatcher = mountainPattern.matcher(line);

        ParsingInfo parsingInfo = new ParsingInfo(lines, explorationMap);

        while( !lines.isEmpty() && mountainMatcher.matches()) {
            line = lines.remove(0);
            mountainMatcher = mountainPattern.matcher(line);
            parsingInfo.setInput(lines);

            mountainMatcher.find();

            int posX = Integer.parseInt(mountainMatcher.group(1));
            int posY = Integer.parseInt(mountainMatcher.group(2));

            //check if x and y are in map range

            if (posX >= 0 && posX <= explorationMap.getWidth() &&
                    posY >= 0 && posY <= explorationMap.getLength()) {
                explorationMap.getCells().put(new ExplorationMap.Coordinates(posX, posY), new MountainCell(posX, posY));
            }


        }

        parsingInfo.setExplorationMap(explorationMap);
        return parsingInfo;
    }
}
