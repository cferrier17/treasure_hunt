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

        String lastLine = "";
        Matcher mountainMatcher ;

        while( !parsingInfo.getInput().isEmpty() && (mountainMatcher = mountainPattern.matcher( lastLine = parsingInfo.getInput().remove(0))).matches()) {

            int posX = Integer.parseInt(mountainMatcher.group(1));
            int posY = Integer.parseInt(mountainMatcher.group(2));


            if (posX >= 0 && posX <= parsingInfo.getExplorationMap().getWidth() &&
                posY >= 0 && posY <= parsingInfo.getExplorationMap().getLength()) {
                parsingInfo.getExplorationMap().getCells().put(new ExplorationMap.Coordinates(posX, posY), new MountainCell(posX, posY));
            }

        }

        parsingInfo.getInput().add(0, lastLine);
        return parsingInfo;
    }
}
