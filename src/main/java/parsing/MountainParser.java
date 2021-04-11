package parsing;

import model.ExplorationMap;
import model.MountainCell;
import parsing.InputParser.ParsingInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MountainParser implements Parser {

    private final Pattern mountainPattern = Pattern.compile("^M\\s-\\s(\\d+)\\s-\\s(\\d+)$");

    @Override
    public ParsingInfo parse(ParsingInfo parsingInfo) {

        String line = parsingInfo.getInput().get(0);
        Matcher mountainMatcher = mountainPattern.matcher(line);

        while( !parsingInfo.getInput().isEmpty() && mountainMatcher.matches()) {
            line = parsingInfo.getInput().remove(0);
            mountainMatcher = mountainPattern.matcher(line);

            mountainMatcher.find();

            int posX = Integer.parseInt(mountainMatcher.group(1));
            int posY = Integer.parseInt(mountainMatcher.group(2));


            if (posX >= 0 && posX <= parsingInfo.getExplorationMap().getWidth() &&
                posY >= 0 && posY <= parsingInfo.getExplorationMap().getLength()) {
                parsingInfo.getExplorationMap().getCells().put(new ExplorationMap.Coordinates(posX, posY), new MountainCell(posX, posY));
            }

            if (!parsingInfo.getInput().isEmpty()) {
                mountainMatcher = mountainPattern.matcher(parsingInfo.getInput().get(0));
            }
        }

        return parsingInfo;
    }
}
