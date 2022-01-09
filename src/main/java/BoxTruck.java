import Tools.BoardReader;
import components.BoardConfiguration;
import search.algorithms.AStar;
import search.algorithms.UCS;
import Heuristic.Heuristic;
import search.space.State;
import java.io.IOException;


public class BoxTruck {


    public static void main(String[] args) throws IOException {

        BoardReader boardReader = new BoardReader(BoxTruck.class.getResource("boards/B1.txt").getFile());


        for (BoardConfiguration boardConfiguration : boardReader.readBoards()) {
            State initialState = new State(boardConfiguration);
            State initialState1 = new State(boardConfiguration, Heuristic.H1);
            State initialState2 = new State(boardConfiguration,Heuristic.H2);
            State initialState3 = new State(boardConfiguration,Heuristic.H3);
            System.out.println(initialState);
            System.out.println("-Uniform Cost Search-\n");
            UCS ucs = new UCS();
            State ucsSolution = ucs.search(initialState);
            ucs.showPath(ucsSolution);
            System.out.println("================================================================");
            AStar aStar = new AStar();
            System.out.println("-A Star Search-\n");
            State aStarSolution;
            aStarSolution = aStar.search(initialState1);
            aStar.showPath(aStarSolution);
            System.out.println("================================================================");
            aStarSolution = aStar.search(initialState2);
            aStar.showPath(aStarSolution);
            System.out.println("================================================================");
            aStarSolution = aStar.search(initialState3);
            aStar.showPath(aStarSolution);
        }
        boardReader.close();

//        while (newState.getDeliveredBoxes().size() != 1) {
//            Scanner s = new Scanner(System.in);
//            int i = 1;
//            for (State.Direction direction : newState.getDirections()) {
//                System.out.println(i++ + " " + direction);
//            }
//            newState = newState.move(newState.getDirections().get(s.nextInt() - 1));
//            System.out.println(newState);
//        }
    }
}
