package search.space;

import Heuristic.Heuristic;
import Heuristic.HeuristicCalculator;
import components.BoardConfiguration;
import components.Box;
import components.Point;
import Tools.Utils;

import java.util.ArrayList;
import java.util.List;


public class State {

    private String[][] board;
    private final Point currentPosition;
    private final List<Direction> directions; // الاتجاهات الممكنة

    private final State preState;
    private final Direction prevMove;
    private int steps;

    private final List<Integer> deliveredBoxes; //الصناديق التي تمت استلامها وتسليمها
    private final List<Integer> loadedBoxes; // الصناديق التي تم استلامها

    private final int cost; // كلفة الانتقال
    private final int payload; // الحمولة
    private final int heuristic;

    private final Point start; // نقطة العودة (البداية)
    private final int boxesNumber; // عدد الصناديق المطلوب تسليمها

    private final HeuristicCalculator heuristicCalculator;
    private final List<Box> boxes;


    public State(BoardConfiguration boardConfiguration) {
        this(boardConfiguration, Heuristic.NO_HEURISTIC);
    }

    public State(BoardConfiguration boardConfiguration, HeuristicCalculator heuristicCalculator) {
        this(boardConfiguration.getStart().getI(),
                boardConfiguration.getStart().getJ(),
                boardConfiguration.getBoard(),
                boardConfiguration.getBoxNumbers(),
                heuristicCalculator,
                boardConfiguration.getBoxes());
    }

    private State(int x, int y, String[][] board, int boxesNumber, HeuristicCalculator heuristicCalculator, List<Box> boxes) {
        this(x, y, board, null, new ArrayList<>(), new ArrayList<>(), null, 0, 0, 0, x, y, boxesNumber, heuristicCalculator, boxes);
    }


    private State(int x, int y,
                  String[][] board,
                  Direction prevMove,
                  List<Integer> loadedBoxes,
                  List<Integer> deliveredBoxes,
                  State preState,
                  int cost,
                  int steps,
                  int payload,
                  int startX, int startY,
                  int boxesNumber,
                  HeuristicCalculator heuristicCalculator,
                  List<Box> boxes) {
        this.currentPosition = new Point(x, y);
        this.board = board;
        this.loadedBoxes = loadedBoxes;
        this.deliveredBoxes = deliveredBoxes;
        this.boxesNumber = boxesNumber;
        this.boxes = boxes;
        this.cost = cost;
        this.steps = steps;
        this.payload = payload;
        this.preState = preState;
        this.prevMove = prevMove;
        this.start = new Point(startX, startY);
        this.directions = Utils.getValidDirections(this.board, this.currentPosition.getI(), this.currentPosition.getJ());
        this.heuristicCalculator = heuristicCalculator;
        this.heuristic = heuristicCalculator.calculate(this);
    }

    public State move(Direction direction) {
        int newX = this.currentPosition.getI() + direction.getX();
        int newY = this.currentPosition.getJ() + direction.getY();
        int newPayload = payload;
        List<Integer> newDeliveredBox = new ArrayList<>(deliveredBoxes);
        List<Integer> newLoadedBoxes = new ArrayList<>(loadedBoxes);
        String[][] newBoard = board.clone();

        String tile = board[newX][newY].trim();
        String numberStr = tile.substring(1);
        char startWith = tile.charAt(0);

        if (Utils.isLoadPlace(startWith, numberStr)) {
            int boxIndex = Integer.parseInt(numberStr);
            if (!loadedBoxes.contains(boxIndex) && !deliveredBoxes.contains(boxIndex)) {
                newLoadedBoxes.add(boxIndex); // loading box
                newPayload++;
            }
        } else if (Utils.isDeliverPlace(startWith, numberStr)) {
            int boxIndex = Integer.parseInt(numberStr);
            if (loadedBoxes.contains(boxIndex)) {
                newLoadedBoxes.remove((Integer) boxIndex); // unloading box
                newDeliveredBox.add(boxIndex); // delivering box
                newPayload--;
            }
        }
        return new State(
                newX,
                newY,
                newBoard,
                direction,
                newLoadedBoxes,
                newDeliveredBox,
                this,
                this.cost + 1,
                steps + 1,
                newPayload,
                this.start.getI(),
                this.start.getJ(),
                this.boxesNumber,
                heuristicCalculator,
                boxes);
    }

    public int getCurrentI() {
        return this.currentPosition.getI();
    }


    public int getCurrentJ() {
        return this.currentPosition.getJ();
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public int getCost() {
        return cost + payload;
    }

    public int getPayload() {
        return payload;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public State getPreState() {
        return preState;
    }

    public Direction getPrevMove() {
        return prevMove;
    }

    public int getBoxesNumber() {
        return boxesNumber;
    }

    public Point getStart() {
        return start;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Integer> getDeliveredBoxes() {
        return deliveredBoxes;
    }

    public List<Integer> getLoadedBoxes() {
        return loadedBoxes;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return currentPosition.equals(state.currentPosition)
                && getLoadedBoxes().equals(state.getLoadedBoxes())
                && getDeliveredBoxes().equals(state.getDeliveredBoxes())
                && (cost + heuristic) >= (state.cost + state.heuristic);
    }


    @Override
    public String toString() {
        int n = this.board.length;
        int m = this.board[0].length;
        String str = "";
        for (int i = 0; i < n; i++) {
            str += "{";
            for (int j = 0; j < m; j++) {
                if (i == currentPosition.getI() && j == currentPosition.getJ()) str += "T ";
                else
                    str += (this.board[i][j] + " ");
            }
            str += "}";
            str += "\n";
        }

        return str;
    }

    public static class Direction {
        private static final Direction UP = new Direction(-1, 0, "up");
        private static final Direction DOWN = new Direction(1, 0, "down");
        private static final Direction RIGHT = new Direction(0, 1, "right");
        private static final Direction LEFT = new Direction(0, -1, "left");


        public static List<Direction> ALL_DIRECTION;
        private int x;
        private int y;
        private String desc;

        static {
            ALL_DIRECTION = new ArrayList<>();
            ALL_DIRECTION.add(Direction.UP);
            ALL_DIRECTION.add(Direction.DOWN);
            ALL_DIRECTION.add(Direction.RIGHT);
            ALL_DIRECTION.add(Direction.LEFT);

        }


        private Direction(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return desc;
        }


    }

}
