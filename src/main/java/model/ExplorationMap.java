package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class ExplorationMap {
    private int width;
    private int length;
    private Map<Coordinates, Cell> cells;
    private List<Adventurer> adventurers;

    public ExplorationMap(int width, int length) {
        this.width = width;
        this.length = length;
        this.cells = new HashMap<>();
        this.adventurers = new ArrayList<>();

        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= length; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                cells.put(coordinates, new EmptyCell(i,j));
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class Coordinates {
        private int posX;
        private int posY;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return posX == that.posX &&
                    posY == that.posY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(posX, posY);
        }
    }
}
