package output;

import model.Adventurer;
import model.Cell;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;
import model.TreasureCell;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextOutputting {

    public String outputMapAfterActions (ExplorationMap explorationMap) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<Coordinates, Cell> cells = explorationMap.getCells();

        stringBuilder.append(getMapOutput(explorationMap));
        stringBuilder.append(getMountainsOutput(cells));
        stringBuilder.append(getTreasuresOutput(cells));
        stringBuilder.append(getAdventurersOutput(explorationMap.getAdventurers()));

        return stringBuilder.toString();
    }

    public String getMapOutput (ExplorationMap explorationMap) {
        return String.format("C - %d - %d\n", explorationMap.getWidth(), explorationMap.getLength());
    }

    public String getMountainsOutput (Map<Coordinates, Cell> cells) {
        return cells.entrySet()
                .stream()
                .filter(coordinatesCellEntry -> !coordinatesCellEntry.getValue().isCrossable())
                .map(mountainCell -> String.format("M - %d - %d", mountainCell.getValue().getX(), mountainCell.getValue().getY()))
                .collect(Collectors.joining("\n"));
    }

    public String getTreasuresOutput (Map<Coordinates, Cell> cells) {
        return cells.entrySet()
                .stream()
                .filter(coordinatesCellEntry -> coordinatesCellEntry.getValue().getClass() == TreasureCell.class)
                .filter(cell -> cell.getValue().getNumberOfTreasures() > 0)
                .map(cell -> {
                    Cell cellValue = cell.getValue();
                    return String.format("T - %d - %d - %d", cellValue.getX(), cellValue.getY(), cellValue.getNumberOfTreasures());
                })
                .collect(Collectors.joining("\n"));
    }


    public String getAdventurersOutput (List<Adventurer> adventurers) {
        return adventurers.stream()
                .map(adventurer -> String.format("A - %s - %d - %d - %c - %d", adventurer.getName(), adventurer.getCoordinates().getPosX(),
                        adventurer.getCoordinates().getPosY(), adventurer.getDirection().getAbreviation(), adventurer.getPickedTreasures()))
                .collect(Collectors.joining("\n"));
    }
}
