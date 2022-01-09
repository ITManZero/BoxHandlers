package components;

import java.util.ArrayList;
import java.util.List;

public class BoardConfiguration {


    public static final BoardConfiguration NO_BOARD_CONFIG = new BoardConfiguration(0, 0, null, null, new ArrayList<>());
    private final int length;
    private final int width;
    private String[][] board;
    private final List<Box> boxes;
    private final int boxNumbers;
    private final Point start;

    public BoardConfiguration(int length, int width, Point start, String[][] board, List<Box> boxes) {
        this.length = length;
        this.width = width;
        this.board = board;
        this.boxes = boxes;
        this.boxNumbers = boxes.size();
        this.start = start;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public int getBoxNumbers() {
        return boxNumbers;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String[][] getBoard() {
        return board;
    }


    public Point getStart() {
        return start;
    }

    @Override
    public String toString() {

        int n = this.board.length;

        String str = "";
        for (int i = 0; i < n; i++) {
            str += "{";
            for (int j = 0; j < this.board[i].length; j++)
                str += (this.board[i][j] + " ");
            str += "}";
            str += "\n";
        }

        return "BoardConfiguration{" +
                "length=" + length +
                ", width=" + width +
                ", \nboard =\n" + str +
                ", boxes=" + boxes +
                ", boxNumbers=" + boxNumbers +
                '}';
    }
}
