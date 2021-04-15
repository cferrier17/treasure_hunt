package output;

import model.ExplorationMap;

import java.util.stream.Collectors;

public class MountainsOutputter implements Outputter {
    @Override
    public String computeOutput(ExplorationMap explorationMap) {
        return explorationMap.getCells()
                .entrySet()
                .stream()
                .filter(coordinatesCellEntry -> !coordinatesCellEntry.getValue().isCrossable())
                .map(mountainCell -> String.format("M - %d - %d", mountainCell.getValue().getCoordinates().getPosX(), mountainCell.getValue().getCoordinates().getPosY()))
                .collect(Collectors.joining("\n"));
    }
}
