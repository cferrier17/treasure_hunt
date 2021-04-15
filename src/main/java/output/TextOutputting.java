package output;

import lombok.AllArgsConstructor;
import model.ExplorationMap;


@AllArgsConstructor
public class TextOutputting {

    private final MapOutputter mapOutputter;
    private final MountainsOutputter mountainsOutputter;
    private final TreasuresOutputter treasuresOutputter;
    private final AdventurersOutputter adventurersOutputter;

    public String outputMapAfterActions (ExplorationMap explorationMap) {

        return mapOutputter.computeOutput(explorationMap) +
                mountainsOutputter.computeOutput(explorationMap) +
                treasuresOutputter.computeOutput(explorationMap) +
                adventurersOutputter.computeOutput(explorationMap);

    }

}
