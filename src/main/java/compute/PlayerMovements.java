package compute;

import model.Adventurer;
import model.Cell;
import model.ExplorationMap;
import model.ExplorationMap.Coordinates;
import model.TreasureCell;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerMovements {

    public ExplorationMap movePlayers (ExplorationMap explorationMap) {
        //todo : factory aventurier ?
        List<Adventurer> adventurers = explorationMap.getAdventurers()
                .stream()
                .sorted(Comparator.comparingInt(Adventurer::getPriority))
                .collect(Collectors.toList());

        explorationMap.setAdventurers(adventurers);

        List<String> actions = adventurers
                .stream()
                .map(Adventurer::getActions)
                .collect(Collectors.toList());

        Optional<String> maxActions = actions
                .stream()
                .max(Comparator.comparingInt(String::length));

        if (!maxActions.isPresent()) {
            return explorationMap;
        }

        else {
            int maxActionsLength = maxActions.get().length();

            for (int actionsStringIndex = 0; actionsStringIndex < maxActionsLength; actionsStringIndex++) {
                for (int adventurersIndex = 0; adventurersIndex < adventurers.size(); adventurersIndex++) {
                    if  (actionsStringIndex < adventurers.get(adventurersIndex).getActions().length()) {
                        Adventurer newAdventurer = singleMovement(actions.get(adventurersIndex).charAt(actionsStringIndex),
                                adventurers.get(adventurersIndex),
                                explorationMap);

                        adventurers.get(adventurersIndex).setDirection(newAdventurer.getDirection());
                        adventurers.get(adventurersIndex).setCoordinates(newAdventurer.getCoordinates());

                        Coordinates adventurerOldCoordinates = findAdventurerCoordinates(adventurers.get(adventurersIndex).getName(), explorationMap);

                        //move adventurer in map
                        explorationMap.getCells().get(adventurerOldCoordinates).setAdventurer(null);
                        explorationMap.getCells().get(newAdventurer.getCoordinates()).setAdventurer(newAdventurer);

                        //treasure looting
                        if (explorationMap.getCells().get(newAdventurer.getCoordinates()).getNumberOfTreasures() > 0
                            && !newAdventurer.getCoordinates().equals(adventurerOldCoordinates) ) {
                            TreasureCell treasureCell = (TreasureCell) explorationMap.getCells().get(newAdventurer.getCoordinates());

                            if (treasureCell.getNumberOfTreasures() > 0 ) {
                                treasureCell.pickTreasure();
                                explorationMap.getCells().put(newAdventurer.getCoordinates(), treasureCell);
                                adventurers.get(adventurersIndex).pickTreasure();

                            }
                        }
                    }
                }
            }
        }

        return explorationMap;

    }

    public Adventurer singleMovement (char action, Adventurer adventurer, ExplorationMap explorationMap) {
        switch (action) {
            case 'A' : adventurer.setCoordinates(moveForward(adventurer, explorationMap)); break;
            case 'G' : adventurer.setDirection(adventurer.getDirection().turnLeft()); break;
            case 'D' : adventurer.setDirection(adventurer.getDirection().turnRight()); break;
        }

        return adventurer;
    }

    public Coordinates moveForward (Adventurer adventurer, ExplorationMap explorationMap) {

        int x = adventurer.getCoordinates().getPosX();
        int y = adventurer.getCoordinates().getPosY();

        Coordinates newCoordinates = new Coordinates(x,y);

        switch (adventurer.getDirection()) {
            case NORTH: newCoordinates = new Coordinates(x, y - 1); break;
            case SOUTH: newCoordinates = new Coordinates(x, y + 1); break;
            case EAST: newCoordinates = new Coordinates(x + 1, y); break;
            case WEST: newCoordinates = new Coordinates(x - 1, y); break;
        }

        if (isNewCellAvailable(newCoordinates, explorationMap)) {
            return newCoordinates;
        }
        else {
            return adventurer.getCoordinates();
        }

    }

    //handles mountains, map boundaries, other adventurers
    public boolean isNewCellAvailable (Coordinates coordinates, ExplorationMap explorationMap) {

        Cell cell = explorationMap.getCells().get(coordinates);

        return coordinates.getPosX() >= 0 && coordinates.getPosY() >= 0 &&
                coordinates.getPosX() <= explorationMap.getWidth() && coordinates.getPosY() <= explorationMap.getLength() &&
                cell.isCrossable() && cell.getAdventurer() == null;
    }


    public Coordinates findAdventurerCoordinates (String adventurerName, ExplorationMap explorationMap) {
        Optional<Cell> first = explorationMap.getCells()
                .entrySet()
                .stream()
                .filter(coordinatesCellEntry -> coordinatesCellEntry.getValue().getAdventurer() != null)
                .filter(coordinatesCellEntry -> coordinatesCellEntry.getValue().getAdventurer().getName().equals(adventurerName))
                .map(Map.Entry::getValue)
                .findFirst();


        return first.map(cell -> new Coordinates(cell.getCoordinates().getPosX(), cell.getCoordinates().getPosY()))
                .orElse(new Coordinates(-1,-1));

    }

}
