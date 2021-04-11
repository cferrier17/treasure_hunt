package parsing;


import model.Adventurer;
import model.ExplorationMap;
import model.direction.Direction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.ExplorationMap.*;
import static model.direction.Direction.*;
import static parsing.InputParser.*;

public class AdventurerParser implements Parser {

    private final Pattern adventurerPattern = Pattern.compile("^A\\s-\\s(\\w+)\\s-\\s(\\d+)\\s-\\s(\\d+) -\\s([NSWE])\\s- ([ADG]+)$");
    private int adventurerPriority = 1;

    @Override
    public ParsingInfo parse(ParsingInfo parsingInfo) {

        String line = parsingInfo.getInput().get(0);
        Matcher adventurerMatcher = adventurerPattern.matcher(line);

        while( !parsingInfo.getInput().isEmpty() && adventurerMatcher.matches()) {
            line = parsingInfo.getInput().remove(0);
            adventurerMatcher = adventurerPattern.matcher(line);

            adventurerMatcher.find();

            String adventurerName = adventurerMatcher.group(1);
            int posX = Integer.parseInt(adventurerMatcher.group(2));
            int posY = Integer.parseInt(adventurerMatcher.group(3));
            Direction direction = getDirectionFromInput( adventurerMatcher.group(4));
            String actions = adventurerMatcher.group(5);

            if (posX >= 0 && posX <= parsingInfo.getExplorationMap().getWidth() &&
                posY >= 0 && posY <= parsingInfo.getExplorationMap().getLength()) {

                Adventurer adventurer = new Adventurer(adventurerName, direction, actions, new Coordinates(posX, posY), adventurerPriority);
                parsingInfo.getExplorationMap().getAdventurers().add(adventurer);
                parsingInfo.getExplorationMap().getCells().get(new Coordinates(posX, posY)).setAdventurer(adventurer);

                adventurerPriority++;
            }
        }

        return parsingInfo;
    }

    public Direction getDirectionFromInput (String input) {
        Direction direction = null;

        switch (input){
            case "S" : direction = SOUTH; break;
            case "N" : direction = NORTH; break;
            case "E" : direction = EAST; break;
            case "O" : direction = WEST; break;
        }

        return direction;
    }
}
