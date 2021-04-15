package parsing;


import model.Adventurer;
import model.direction.Direction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.ExplorationMap.Coordinates;
import static model.direction.Direction.*;
import static parsing.InputParser.ParsingInfo;

public class AdventurerParser implements Parser {

    private final Pattern adventurerPattern = Pattern.compile("^A\\s-\\s(\\w+)\\s-\\s(\\d+)\\s-\\s(\\d+) -\\s([NSWE])\\s- ([ADG]+)$");
    private int adventurerPriority = 1;

    @Override
    public ParsingInfo parse(ParsingInfo parsingInfo) {

        Matcher adventurerMatcher;

        while( !parsingInfo.getInput().isEmpty() && (adventurerMatcher = adventurerPattern.matcher(parsingInfo.getInput().remove(0))).matches()) {

            String adventurerName = adventurerMatcher.group(1);
            int posX = Integer.parseInt(adventurerMatcher.group(2));
            int posY = Integer.parseInt(adventurerMatcher.group(3));
            Direction direction = getDirectionFromInput( adventurerMatcher.group(4));
            String actions = adventurerMatcher.group(5);

            Coordinates coordinates = new Coordinates(posX, posY);


            if (posX >= 0 && posX <= parsingInfo.getExplorationMap().getWidth() &&
                posY >= 0 && posY <= parsingInfo.getExplorationMap().getLength() &&
                parsingInfo.getExplorationMap().getCells().get(coordinates).getAdventurer() == null) {


                Adventurer adventurer = new Adventurer(adventurerName, direction, actions, coordinates, adventurerPriority, 0);

                parsingInfo.getExplorationMap().getAdventurers().add(adventurer);
                parsingInfo.getExplorationMap().getCells().get(coordinates).setAdventurer(adventurer);

                adventurerPriority++;
            }
        }

        return parsingInfo;
    }

    private Direction getDirectionFromInput (String input) {
        Direction direction = null;

        switch (input){
            case "S" : direction = SOUTH; break;
            case "N" : direction = NORTH; break;
            case "E" : direction = EAST; break;
            case "O" : direction = WEST; break;
            case "W" : direction = WEST; break;
        }

        return direction;
    }
}
