package parsing;

import model.ExplorationMap;
import model.MountainCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.ExplorationMap.*;

public class InputParser {
    private final Pattern mapPattern = Pattern.compile("^C\\s-\\s(\\d+)\\s-\\s(\\d+)$");
    private final Pattern mountainPattern = Pattern.compile("^M\\s-\\s(\\d+)\\s-\\s(\\d+)$");

    public ExplorationMap readInput (String data) {
        ArrayList<String> lines =  new ArrayList<>(Arrays.asList(data.split("\n")));

        ExplorationMap explorationMap = new ExplorationMap(0 ,0);

        if (lines.size() < 1) {
            return explorationMap;
        }

        Matcher mapMatcher = mapPattern.matcher(lines.remove(0));

        if (mapMatcher.find()) {
            int width = Integer.parseInt(mapMatcher.group(1));
            int length = Integer.parseInt(mapMatcher.group(2));

            if (width > 0 && length > 0) {
                explorationMap.setWidth(width);
                explorationMap.setLength(length);
            } else {
                return explorationMap;
            }
        } else {
            return explorationMap;
        }

        if (lines.isEmpty()) {
            return explorationMap;
        }

        String line = lines.remove(0);
        Matcher mountainMatcher = mountainPattern.matcher(line);

        while(mountainMatcher.matches()) {
            int posX = Integer.parseInt(mountainMatcher.group(1));
            int posY = Integer.parseInt(mountainMatcher.group(2));

            //check if x and y are in map range

            if (posX >= 0 && posX <= explorationMap.getWidth() &&
                posY >= 0 && posY <= explorationMap.getLength()) {
                explorationMap.getCells().put(new Coordinates(posX, posY), new MountainCell(posX, posY));
            }

            if (!lines.isEmpty()) {
                line = lines.remove(0);
                mountainMatcher = mountainPattern.matcher(line);
            }
            else {
                break;
            }

        }

        return explorationMap;
    }
}