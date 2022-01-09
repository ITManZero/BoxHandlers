package Tools;

import components.BoardConfiguration;
import components.Box;
import components.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BoardReader {

    private final FileReader fileReader;
    private final BufferedReader bufferedReader;

    public BoardReader(String fileName) throws FileNotFoundException {
        this.fileReader = new FileReader(fileName);
        this.bufferedReader = new BufferedReader(fileReader);
    }

    public BoardConfiguration readBoard() throws IOException {
        String line;
        int l = 0;
        int w = 0;
        Point start = null;
        Map<Integer, Box> boxMap = new HashMap<>();
        List<List<String>> boardList = new ArrayList<>();
        while ((line = this.bufferedReader.readLine()) != null && !line.isEmpty()) {
            List<String> tiles = Utils.getTiles(line);
            if (l != tiles.size() && w > 0) throw new IOException("all rows must have same column numbers");
            l = tiles.size();
            List<String> row = new ArrayList<>();
            boardList.add(row);
            for (int j = 0; j < tiles.size(); j++) {
                String tile = tiles.get(j);
                row.add(tile);
                char startWith = tile.charAt(0);
                String numberStr = tile.substring(1);
                if (startWith == 'S' || startWith == 's') start = new Point(w, j);
                if (Utils.isLoadPlace(startWith, numberStr)) {
                    int boxNumber = Integer.parseInt(numberStr);
                    Box box = boxMap.get(boxNumber);
                    if (box == null) {
                        box = new Box();
                        boxMap.put(boxNumber, box);
                    }
                    box.setFrom(new Point(w, j));
                } else if (Utils.isDeliverPlace(startWith, numberStr)) {
                    int boxNumber = Integer.parseInt(numberStr);
                    Box box = boxMap.get(boxNumber);
                    if (box == null) {
                        box = new Box();
                        boxMap.put(boxNumber, box);
                    }
                    box.setTo(new Point(w, j));
                }
            }
            w++;
        }
        if (boxMap.isEmpty()) return line == null ? null : BoardConfiguration.NO_BOARD_CONFIG;

        String[][] board = new String[w][l];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < l; j++)
                board[i][j] = boardList.get(i).get(j);
        if (start == null) throw new IOException("missing start point");
        return new BoardConfiguration(l, w, start, board, new ArrayList<>(boxMap.values()));
    }

    public List<BoardConfiguration> readBoards() throws IOException {
        List<BoardConfiguration> boardConfigurations = new ArrayList<>();
        BoardConfiguration boardConfiguration;
        while ((boardConfiguration = readBoard()) != null) {
            if (boardConfiguration != BoardConfiguration.NO_BOARD_CONFIG)
                boardConfigurations.add(boardConfiguration);
        }
        return boardConfigurations;
    }

    public void close() throws IOException {
        this.bufferedReader.close();
        this.fileReader.close();
    }
}
