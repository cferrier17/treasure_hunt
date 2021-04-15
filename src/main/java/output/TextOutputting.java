package output;

import lombok.AllArgsConstructor;
import model.Cell;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;

import java.util.Map;

@AllArgsConstructor
public class TextOutputting {

    private final MapOutputter mapOutputter;
    private final MountainsOutputter mountainsOutputter;
    private final TreasuresOutputter treasuresOutputter;
    private final AdventurersOutputter adventurersOutputter;

    public String outputMapAfterActions (ExplorationMap explorationMap) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<Coordinates, Cell> cells = explorationMap.getCells();

        stringBuilder.append(mapOutputter.computeOutput(explorationMap));
        stringBuilder.append(mountainsOutputter.computeOutput(explorationMap));
        stringBuilder.append(treasuresOutputter.computeOutput(explorationMap));
        stringBuilder.append(adventurersOutputter.computeOutput(explorationMap));

        return stringBuilder.toString();
    }

}
