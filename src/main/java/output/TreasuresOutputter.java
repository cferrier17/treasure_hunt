package output;

import model.Cell;
import model.ExplorationMap;
import model.TreasureCell;

import java.util.stream.Collectors;

public class TreasuresOutputter implements Outputter {
    @Override
    public String computeOutput(ExplorationMap explorationMap) {
        return explorationMap.getCells()
                .entrySet()
                .stream()
                .filter(coordinatesCellEntry -> coordinatesCellEntry.getValue().getClass() == TreasureCell.class)
                .filter(cell -> cell.getValue().getNumberOfTreasures() > 0)
                .map(cell -> {
                    Cell cellValue = cell.getValue();
                    return String.format("T - %d - %d - %d", cellValue.getCoordinates().getPosX(), cellValue.getCoordinates().getPosY(), cellValue.getNumberOfTreasures());
                })
                .collect(Collectors.joining("\n"));
    }
}
