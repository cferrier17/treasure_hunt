package model.direction;

public enum Direction implements  Rotation, Abreviation{
    NORTH{
        @Override
        public char getAbreviation() {
            return 'N';
        }

        @Override
        public Direction turnLeft() {
            return WEST;
        }

        @Override
        public Direction turnRight() {
            return EAST;
        }
    },
    SOUTH {
        @Override
        public char getAbreviation() {
            return 'S';
        }

        @Override
        public Direction turnLeft() {
            return EAST;
        }

        @Override
        public Direction turnRight() {
            return WEST;
        }
    },
    EAST {
        @Override
        public char getAbreviation() {
            return 'E';
        }

        @Override
        public Direction turnLeft() {
            return NORTH;
        }

        @Override
        public Direction turnRight() {
            return SOUTH;
        }
    },
    WEST {
        @Override
        public char getAbreviation() {
            return 'W';
        }

        @Override
        public Direction turnLeft() {
            return SOUTH;
        }

        @Override
        public Direction turnRight() {
            return NORTH;
        }
    }

}
