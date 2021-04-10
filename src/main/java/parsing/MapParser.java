package parsing;

import model.ExplorationMap;
import parsing.InputParser.ParsingInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapParser implements Parser{

    private final Pattern mapPattern = Pattern.compile("^C\\s-\\s(\\d+)\\s-\\s(\\d+)$");

    @Override
    public ParsingInfo parse(ArrayList<String> lines, ExplorationMap explorationMap) {

        Matcher mapMatcher = mapPattern.matcher(lines.remove(0));
        ParsingInfo parsingInfo = new ParsingInfo(lines, explorationMap);

        if (mapMatcher.find()) {
            int width = Integer.parseInt(mapMatcher.group(1));
            int length = Integer.parseInt(mapMatcher.group(2));

            if (width > 0 && length > 0) {
                explorationMap.setWidth(width);
                explorationMap.setLength(length);
                parsingInfo.setExplorationMap(explorationMap);
            }
        }

        return parsingInfo;
    }
}
