package output;

import model.ExplorationMap;

import java.util.stream.Collectors;

public class AdventurersOutputter implements Outputter {
    @Override
    public String computeOutput(ExplorationMap explorationMap) {
        return explorationMap.getAdventurers()
                .stream()
                .map(adventurer -> String.format("A - %s - %d - %d - %c - %d", adventurer.getName(), adventurer.getCoordinates().getPosX(),
                        adventurer.getCoordinates().getPosY(), adventurer.getDirection().getAbreviation(), adventurer.getPickedTreasures()))
                .collect(Collectors.joining("\n"));    }
}
