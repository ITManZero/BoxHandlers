package Tools;

import components.Box;
import components.Point;
import search.space.State;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Utils {

    public static List<State.Direction> getValidDirections(String[][] board, int x, int y) {
        List<State.Direction> directions = new ArrayList<>();
        for (State.Direction direction : State.Direction.ALL_DIRECTION)
            if (direction.getX() + x < board.length &&
                    direction.getX() + x > -1 &&
                    direction.getY() + y < board[0].length &&
                    direction.getY() + y > -1 &&
                    !board[direction.getX() + x][direction.getY() + y].equals("#")) {
                directions.add(direction);
            }
        return directions;
    }

    public static boolean isLoadPlace(char startWith, String numberStr) {
        return (startWith == 'P' || startWith == 'p') &&
                isNumber(numberStr);
    }

    public static boolean isDeliverPlace(char startWith, String numberStr) {
        return (startWith == 'D' || startWith == 'd') &&
                isNumber(numberStr);
    }

    private static boolean isNumber(String string) {
        for (int i = 0; i < string.length(); i++)
            if (!(string.charAt(i) >= '0' && string.charAt(i) <= '9')) return false;
        return true;
    }

    public static boolean isGoal(State state) {
        int boxesNumber = state.getBoxesNumber();
        List<Integer> deliveredBoxes = state.getDeliveredBoxes();
        Point currentPosition = state.getCurrentPosition();
        Point start = state.getStart();
        return boxesNumber == deliveredBoxes.size()
                && currentPosition.equals(start);
    }

    public static int distance(Point p1, Point p2) {
        int x1 = p1.getI();
        int y1 = p1.getJ();
        int x2 = p2.getI();
        int y2 = p2.getJ();
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static List<String> getTiles(String line) {
        List<String> tiles = new ArrayList<>();
        StringBuilder tile = new StringBuilder();
        boolean emptyTile = true;
        for (int i = 0; i < line.length(); i++) {
            boolean isEndOfTile = line.charAt(i) == ' ' && !emptyTile;
            if (isEndOfTile) {
                tiles.add(tile.toString());
                tile = new StringBuilder();
                emptyTile = true;
            }
            if (line.charAt(i) == ' ') continue;
            tile.append(line.charAt(i));
            emptyTile = false;
        }
        if (tile.length() > 0) tiles.add(tile.toString());
        return tiles;
    }
}