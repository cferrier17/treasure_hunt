package parsing;


import model.ExplorationMap;
import parsing.InputParser.ParsingInfo;

import java.util.ArrayList;
import java.util.List;


public interface Parser {
    ParsingInfo parse (ArrayList<String> lines, ExplorationMap explorationMap);
}
