package output;

import model.ExplorationMap;

public class MapOutputter implements Outputter {
    @Override
    public String computeOutput(ExplorationMap explorationMap) {
        return String.format("C - %d - %d\n", explorationMap.getWidth(), explorationMap.getLength());
    }
}
