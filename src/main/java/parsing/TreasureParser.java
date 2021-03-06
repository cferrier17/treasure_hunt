package parsing;

import model.TreasureCell;
import parsing.InputParser.ParsingInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.ExplorationMap.Coordinates;

public class TreasureParser implements Parser {

    private final Pattern treasurePattern = Pattern.compile("^T\\s-\\s(\\d+)\\s-\\s(\\d+)\\s-\\s(\\d+)$");


    @Override
    public ParsingInfo parse(ParsingInfo parsingInfo) {

        Matcher treasureMatcher ;
        String lastLine = "";

        while( !parsingInfo.getInput().isEmpty() && (treasureMatcher = treasurePattern.matcher( lastLine = parsingInfo.getInput().remove(0))).matches()) {

            int posX = Integer.parseInt(treasureMatcher.group(1));
            int posY = Integer.parseInt(treasureMatcher.group(2));
            int numberOfTreasures = Integer.parseInt(treasureMatcher.group(3));

            Coordinates coordinates = new Coordinates(posX, posY);

            //check if x and y are in map range

            if (posX >= 0 && posX <= parsingInfo.getExplorationMap().getWidth() &&
                posY >= 0 && posY <= parsingInfo.getExplorationMap().getLength() &&
                parsingInfo.getExplorationMap().getCells().get(coordinates).isCrossable() &&
                numberOfTreasures > 0) {
                parsingInfo.getExplorationMap().getCells().put(new Coordinates(posX, posY), new TreasureCell(posX, posY, numberOfTreasures));
            }


        }

        parsingInfo.getInput().add(0, lastLine);
        return parsingInfo;
    }

}
