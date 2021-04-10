package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExplorationMap {
    private int width;
    private int length;
    private Map<Coordinates, Cell> cells;

    public ExplorationMap(int width, int length) {
        this.width = width;
        this.length = length;
        this.cells = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    public static class Coordinates {
        private int posX;
        private int posY;

    }
}
