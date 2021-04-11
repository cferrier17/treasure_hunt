package parsing;

import model.ExplorationMap;
import parsing.InputParser.ParsingInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapParser implements Parser{

    private final Pattern mapPattern = Pattern.compile("^C\\s-\\s(\\d+)\\s-\\s(\\d+)$");

    @Override
    public ParsingInfo parse(ParsingInfo parsingInfo) {

        Matcher mapMatcher = mapPattern.matcher(parsingInfo.getInput().remove(0));

        if (mapMatcher.find()) {
            int width = Integer.parseInt(mapMatcher.group(1));
            int length = Integer.parseInt(mapMatcher.group(2));

            if (width > 0 && length > 0) {
                parsingInfo.getExplorationMap().setWidth(width);
                parsingInfo.getExplorationMap().setLength((length));
                parsingInfo.setExplorationMap(new ExplorationMap(width, length));
            }
        }

        return parsingInfo;
    }
}
